package de.nedelosk.modularmachines.common.modules.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import akka.japi.Pair;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularTileEntity;
import de.nedelosk.modularmachines.api.modular.ModularHelper;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerGroup;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerSlot;
import de.nedelosk.modularmachines.api.modular.renderer.IRenderState;
import de.nedelosk.modularmachines.api.modular.renderer.ISimpleRenderer;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IRecipeManager;
import de.nedelosk.modularmachines.api.modules.casing.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.PropertyBool;
import de.nedelosk.modularmachines.api.modules.state.PropertyFloat;
import de.nedelosk.modularmachines.api.modules.state.PropertyInteger;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;
import de.nedelosk.modularmachines.client.render.modules.EngineRenderer;
import de.nedelosk.modularmachines.common.modules.Module;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleEngine extends Module implements IModuleEngine {

	private final int burnTimeModifier;

	public static final PropertyFloat PROGRESS = new PropertyFloat("progress");
	public static final PropertyBool WORKING = new PropertyBool("isWorking");
	public static final PropertyInteger MACHINEINDEX = new PropertyInteger("machineIndex");

	public ModuleEngine(int burnTimeModifier) {
		this.burnTimeModifier = burnTimeModifier;
	}

	@Override
	public boolean onAssembleModule(IAssemblerGroup group, IModuleState moduleState, IModuleState<IModuleCasing> casing, Map<IAssemblerGroup, List<Pair<IAssemblerSlot, IModuleState>>> modules, boolean beforeAdd) {
		if(beforeAdd){
			IModuleState machine = modules.get(group).get(0).second();
			moduleState.add(MACHINEINDEX, machine.getIndex());
		}
		return true;
	}


	@Override
	public int getBurnTimeModifier(IModuleState state) {
		return burnTimeModifier;
	}

	@Override
	public void updateServer(IModuleState state) {
		IModular modular = state.getModular();
		boolean isWorking = (boolean) state.get(WORKING);
		IModuleState<IModuleTool> machineState = modular.getModule(state.get(MACHINEINDEX));
		if (machineState == null) {
			return;
		}

		if (machineState.getModule().getRecipeManager(machineState) != null && canWork(state) && machineState.getModule().getWorkTime(machineState) <= machineState.getModule().getWorkTimeTotal(machineState)) {
			if (removeMaterial(state, machineState)) {
				if(!isWorking){
					state.add(WORKING, true);
					PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularTileEntity) modular.getTile(), state));
				}
				machineState.getModule().addWorkTime(machineState, burnTimeModifier);
				PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularTileEntity) modular.getTile(), machineState));
			}
		}else if(isWorking){
			state.add(WORKING, false);
			PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularTileEntity) modular.getTile(), state));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModuleState state) {
		if (isWorking(state)) {
			addProgress(state, 0.025F);
			if (getProgress(state) > 1) {
				setProgress(state, 0);
			}
		}else{
			if(getProgress(state) > 0){
				if(getProgress(state) < 1){
					addProgress(state, 0.025F);
				}else{
					setProgress(state, 0);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(IRenderState state) {
		return new EngineRenderer(state.getModuleState().getContainer(), ModularHelper.getCasing(state.getModular()).getContainer());
	}

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).add(PROGRESS, 0).add(WORKING, false).add(MACHINEINDEX, -1);
	}

	@Override
	public boolean removeMaterial(IModuleState state, IModuleState<IModuleTool> machineState) {
		IRecipeManager handler = machineState.getModule().getRecipeManager(machineState);
		if(state.getModular().getEnergyHandler() == null){
			return false;
		}
		if (state.getModular().getEnergyHandler().extractEnergy(null, handler.getMaterialToRemove(), false) == handler.getMaterialToRemove()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public float getProgress(IModuleState state) {
		return (float) state.get(PROGRESS);
	}

	@Override
	public void setProgress(IModuleState state, float progress) {
		state.add(PROGRESS, progress);
	}

	@Override
	public void addProgress(IModuleState state, float progress) {
		state.add(PROGRESS, (float) state.get(PROGRESS) + progress);
	}

	@Override
	public boolean isWorking(IModuleState state) {
		return (boolean) state.get(WORKING);
	}

	@Override
	public void setIsWorking(IModuleState state, boolean isWorking) {
		state.add(WORKING, isWorking);
	}

	@Override
	public IModulePage[] createPages(IModuleState state) {
		return null;
	}

	@Override
	public List<Integer> getMachineIndexes(IModuleState state) {
		List<Integer> indexes = new ArrayList();
		indexes.add((int)state.get(MACHINEINDEX));
		return indexes;
	}

	@Override
	public boolean canWork(IModuleState state) {
		IModular modular = state.getModular();
		if(modular.getEnergyHandler() == null){
			return false;
		}
		if (modular.getEnergyHandler().getEnergyStored(null) > 0) {
			return true;
		} else {
			return false;
		}
	}

}