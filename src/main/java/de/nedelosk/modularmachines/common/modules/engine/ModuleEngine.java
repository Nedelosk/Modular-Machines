package de.nedelosk.modularmachines.common.modules.engine;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IRecipeManager;
import de.nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;
import de.nedelosk.modularmachines.api.property.PropertyBool;
import de.nedelosk.modularmachines.api.property.PropertyFloat;
import de.nedelosk.modularmachines.api.property.PropertyInteger;
import de.nedelosk.modularmachines.common.modules.Module;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleEngine extends Module implements IModuleEngine {

	private final int burnTimeModifier;

	public static final PropertyFloat PROGRESS = new PropertyFloat("progress", 0);
	public static final PropertyBool WORKING = new PropertyBool("isWorking", false);
	public static final PropertyInteger MACHINEINDEX = new PropertyInteger("machineIndex", -1);

	public ModuleEngine(int burnTimeModifier) {
		this.burnTimeModifier = burnTimeModifier;
	}

	/*@Override
	public boolean assembleModule(IItemHandler itemHandler, IModular modular, IModuleState state) {
		IModuleState machine = modules.get(group).get(0).second();
		moduleState.add(MACHINEINDEX, machine.getIndex());
		return super.assembleModule(itemHandler, modular, state);
	}*/


	@Override
	public int getBurnTimeModifier(IModuleState state) {
		return burnTimeModifier;
	}

	@Override
	public void updateServer(IModuleState state, int tickCount) {
		IModular modular = state.getModular();
		boolean isWorking = state.get(WORKING);
		IModuleState<IModuleTool> machineState = modular.getModule(state.get(MACHINEINDEX));
		if (machineState == null) {
			return;
		}

		if (machineState.getModule().getRecipeManager(machineState) != null && canWork(state) && machineState.getModule().getWorkTime(machineState) <= machineState.getModule().getWorkTimeTotal(machineState)) {
			if (removeMaterial(state, machineState)) {
				if(!isWorking){
					state.add(WORKING, true);
					PacketHandler.INSTANCE.sendToAll(new PacketModule(modular.getHandler(), state));
				}
				machineState.getModule().addWorkTime(machineState, burnTimeModifier);
				PacketHandler.INSTANCE.sendToAll(new PacketModule(modular.getHandler(), machineState));
			}
		}else if(isWorking){
			state.add(WORKING, false);
			PacketHandler.INSTANCE.sendToAll(new PacketModule(modular.getHandler(), state));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient(IModuleState state, int tickCount) {
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

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		return super.createState(modular, container).register(PROGRESS).register(WORKING).register(MACHINEINDEX);
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
		return state.get(PROGRESS);
	}

	@Override
	public void setProgress(IModuleState state, float progress) {
		state.add(PROGRESS, progress);
	}

	@Override
	public void addProgress(IModuleState state, float progress) {
		state.add(PROGRESS, state.get(PROGRESS) + progress);
	}

	@Override
	public boolean isWorking(IModuleState state) {
		return state.get(WORKING);
	}

	@Override
	public void setIsWorking(IModuleState state, boolean isWorking) {
		state.add(WORKING, isWorking);
	}

	@Override
	public List<Integer> getMachineIndexes(IModuleState state) {
		List<Integer> indexes = new ArrayList();
		indexes.add((int)state.get(MACHINEINDEX));
		return indexes;
	}

	@Override
	public int getSize() {
		return 1;
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