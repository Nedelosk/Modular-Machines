package de.nedelosk.forestmods.common.modules.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import akka.japi.Pair;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.client.render.modules.EngineRenderer;
import de.nedelosk.forestmods.common.modules.Module;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketModule;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modular.ModularHelper;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerGroup;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerSlot;
import de.nedelosk.forestmods.library.modular.renderer.IRenderState;
import de.nedelosk.forestmods.library.modular.renderer.ISimpleRenderer;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.IModuleMachine;
import de.nedelosk.forestmods.library.modules.IRecipeManager;
import de.nedelosk.forestmods.library.modules.ModuleUID;
import de.nedelosk.forestmods.library.modules.casing.IModuleCasing;
import de.nedelosk.forestmods.library.modules.engine.IModuleEngine;
import de.nedelosk.forestmods.library.modules.handlers.IModulePage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class ModuleEngine extends Module implements IModuleEngine {

	protected boolean isWorking;
	protected float progress;
	private final int burnTimeModifier;
	private int machineIndex;

	public ModuleEngine(IModular modular, IModuleContainer container, int burnTimeModifier) {
		super(modular, container);
		this.burnTimeModifier = burnTimeModifier;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		super.readFromNBT(nbt, modular);
		isWorking = nbt.getBoolean("isWorking");
		if(nbt.hasKey("MachineIndex")){
			machineIndex = nbt.getInteger("MachineIndex");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		super.writeToNBT(nbt, modular);
		nbt.setBoolean("isWorking", isWorking);
		nbt.setInteger("MachineIndex", machineIndex);
	}

	@Override
	public boolean onAddToModular(IAssemblerGroup group, IModuleCasing casing, Map<IAssemblerGroup, List<Pair<IAssemblerSlot, IModule>>> modules, boolean beforeAdd) {
		if(beforeAdd){
			IModule controller = modules.get(group).get(0).second();
			machineIndex = controller.getIndex();
		}
		return true;
	}

	@Override
	protected void onCreateModel(IModular modular, IModuleContainer moduleContainer) {
		super.onCreateModel(modular, moduleContainer);
	}

	@Override
	public int getBurnTimeModifier() {
		return burnTimeModifier;
	}

	@Override
	public void updateServer() {
		IModuleMachine machine = modular.getModule(machineIndex);
		if (machine == null) {
			return;
		}

		if (machine.getRecipeManager() != null && canWork() && machine.getBurnTime() <= machine.getBurnTimeTotal()) {
			if (removeMaterial(modular, machine)) {
				if(!isWorking){
					isWorking = true;
					PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularTileEntity) modular.getTile(), this));
				}
				machine.addBurnTime(burnTimeModifier);
				PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularTileEntity) modular.getTile(), machine));
			}
		}else if(isWorking){
			isWorking = false;
			PacketHandler.INSTANCE.sendToAll(new PacketModule((TileEntity & IModularTileEntity) modular.getTile(), this));
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateClient() {
		if (isWorking()) {
			addProgress(0.025F);
			if (getProgress() > 1) {
				setProgress(0);
			}
		}else{
			if(getProgress() > 0){
				if(getProgress() < 1){
					addProgress(0.025F);
				}else{
					setProgress(0);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ISimpleRenderer getRenderer(IRenderState state) {
		return new EngineRenderer(container, ModularHelper.getCasing(state.getModular()).getModuleContainer());
	}

	@Override
	public boolean removeMaterial(IModular modular, IModuleMachine machine) {
		IRecipeManager handler = machine.getRecipeManager();
		if(modular.getEnergyHandler() == null){
			return false;
		}
		if (modular.getEnergyHandler().extractEnergy(ForgeDirection.UNKNOWN, handler.getMaterialToRemove(), false) == handler.getMaterialToRemove()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public float getProgress() {
		return progress;
	}

	@Override
	public void setProgress(float progress) {
		this.progress = progress;
	}

	@Override
	public void addProgress(float progress) {
		this.progress += progress;
	}

	@Override
	public boolean isWorking() {
		return isWorking;
	}

	@Override
	public void setIsWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}

	@Override
	protected IModulePage[] createPages() {
		return null;
	}

	@Override
	public List<Integer> getMachineIndexes() {
		List<Integer> indexes = new ArrayList();
		indexes.add(machineIndex);
		return indexes;
	}

	@Override
	public boolean canWork() {
		if(modular.getEnergyHandler() == null){
			return false;
		}
		if (modular.getEnergyHandler().getEnergyStored(ForgeDirection.UNKNOWN) > 0) {
			return true;
		} else {
			return false;
		}
	}
}