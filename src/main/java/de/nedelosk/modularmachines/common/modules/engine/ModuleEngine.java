package de.nedelosk.modularmachines.common.modules.engine;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModuleIndexStorage;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modules.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.EnumWallType;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleModelHandler;
import de.nedelosk.modularmachines.api.modules.IModuleState;
import de.nedelosk.modularmachines.api.modules.engine.IModuleEngine;
import de.nedelosk.modularmachines.api.modules.tool.IModuleMachine;
import de.nedelosk.modularmachines.api.modules.tool.IModuleTool;
import de.nedelosk.modularmachines.api.property.PropertyBool;
import de.nedelosk.modularmachines.api.property.PropertyFloat;
import de.nedelosk.modularmachines.api.property.PropertyInteger;
import de.nedelosk.modularmachines.client.modules.ModelHandlerEngine;
import de.nedelosk.modularmachines.common.inventory.ContainerModularAssembler;
import de.nedelosk.modularmachines.common.modules.ModuleStoraged;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public abstract class ModuleEngine extends ModuleStoraged implements IModuleEngine {

	protected final int burnTimeModifier;
	protected final int materialPerTick;

	public static final PropertyFloat PROGRESS = new PropertyFloat("progress", 0);
	public static final PropertyBool WORKING = new PropertyBool("isWorking", false);
	public static final PropertyInteger MACHINEINDEX = new PropertyInteger("machineIndex", -1);

	public ModuleEngine(String name, int complexity, int burnTimeModifier, int materialPerTick) {
		super(name, complexity);
		this.burnTimeModifier = burnTimeModifier;
		this.materialPerTick = materialPerTick;
	}

	private int getIndexForSlot(IItemHandler itemHandler, int index, IModuleIndexStorage storage){
		int matchingToolSlot = 0;
		for(matchingToolSlot = 0;matchingToolSlot < 3;matchingToolSlot++){
			int driveIndex = ContainerModularAssembler.driveSlots[matchingToolSlot];
			if(index == driveIndex){
				break;
			}
		}
		if(itemHandler.getStackInSlot(ContainerModularAssembler.toolSlots[matchingToolSlot]) != null){
			return storage.getSlotIndexToState().get(Integer.valueOf(ContainerModularAssembler.toolSlots[matchingToolSlot])).getIndex();
		}else{
			for(int i = 0;i < 3;i++){
				if(i != matchingToolSlot){
					int toolSlotIndex= ContainerModularAssembler.toolSlots[i];
					ItemStack stack = itemHandler.getStackInSlot(toolSlotIndex);
					if(stack != null){
						IModuleContainer container = ModularManager.getContainerFromItem(stack);
						EnumModuleSize size = ((IModuleTool)container.getModule()).getSize();
						if(size == EnumModuleSize.LARGE || size == EnumModuleSize.MIDDLE){
							return storage.getSlotIndexToState().get(Integer.valueOf(toolSlotIndex)).getIndex();
						}
					}
				}

			}
		}
		return -1;
	}

	@Override
	public boolean assembleModule(IItemHandler itemHandler, IModular modular, IModuleState state, IModuleIndexStorage storage) {
		int index = getIndexForSlot(itemHandler, storage.getStateToSlotIndex().get(state), storage);
		if(index != -1){
			state.set(MACHINEINDEX, index);
		}else{
			return false;
		}
		return super.assembleModule(itemHandler, modular, state, storage);
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
		IModuleState<IModuleMachine> machineState = modular.getModule(state.get(MACHINEINDEX));
		if (machineState == null) {
			return;
		}

		if (machineState.getModule().getCurrentRecipe(machineState) != null && canWork(state) && machineState.getModule().getWorkTime(machineState) <= machineState.getModule().getWorkTimeTotal(machineState)) {
			if (removeMaterial(state, machineState)) {
				if(!isWorking){
					state.set(WORKING, true);
					PacketHandler.INSTANCE.sendToAll(new PacketModule(modular.getHandler(), state));
				}
				machineState.getModule().addWorkTime(machineState, burnTimeModifier);
				PacketHandler.INSTANCE.sendToAll(new PacketModule(modular.getHandler(), machineState));
			}
		}else if(isWorking){
			state.set(WORKING, false);
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
	public float getProgress(IModuleState state) {
		return state.get(PROGRESS);
	}

	@Override
	public void setProgress(IModuleState state, float progress) {
		state.set(PROGRESS, progress);
	}

	@Override
	public void addProgress(IModuleState state, float progress) {
		state.set(PROGRESS, state.get(PROGRESS) + progress);
	}

	@Override
	public boolean isWorking(IModuleState state) {
		return state.get(WORKING);
	}

	@Override
	public void setIsWorking(IModuleState state, boolean isWorking) {
		state.set(WORKING, isWorking);
	}

	@Override
	public List<Integer> getMachineIndexes(IModuleState state) {
		List<Integer> indexes = new ArrayList();
		indexes.add((int)state.get(MACHINEINDEX));
		return indexes;
	}

	@Override
	public EnumModuleSize getSize() {
		return EnumModuleSize.SMALL;
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

	@Override
	public IModuleModelHandler getInitModelHandler(IModuleContainer container) {
		return new ModelHandlerEngine(new ResourceLocation("modularmachines:module/engines/" + container.getMaterial().getName()));
	}

	@Override
	public EnumWallType getWallType(IModuleState state) {
		return EnumWallType.WINDOW;
	}

}