package de.nedelosk.modularmachines.common.modular.assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import akka.japi.Pair;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.assembler.IAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerGroup;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerSlot;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.IModuleController;
import de.nedelosk.modularmachines.api.modules.ModuleEvents;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.casing.IModuleCasing;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.client.gui.GuiModularAssembler;
import de.nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import de.nedelosk.modularmachines.common.core.BlockManager;
import de.nedelosk.modularmachines.common.inventory.ContainerModularAssembler;
import de.nedelosk.modularmachines.common.modular.Modular;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

public class Assembler implements IAssembler {

	public final TileModularAssembler tile;
	public int maxControllers;
	public Map<Integer, IAssemblerGroup> groups = Maps.newHashMap();
	public IAssemblerGroup currentGroup;

	public Assembler(TileModularAssembler inventory) {
		this.tile = inventory;
	}

	@Override
	public Map<Integer, IAssemblerGroup> getGroups() {
		return groups;
	}

	@Override
	public IAssemblerGroup addGroup(IAssemblerGroup group) {
		groups.put(group.getGroupID(), group);
		return group;
	}

	@Override
	public ItemStack getCasingStack() {
		return tile.getStackInSlot(0);
	}

	@Override
	public GuiContainer getGUIContainer(InventoryPlayer inventory) {
		return new GuiModularAssembler(this, inventory);
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return new ContainerModularAssembler(this, inventory);
	}

	@Override
	public IAssemblerGroup getCurrentGroup() {
		return currentGroup;
	}

	@Override
	public void setCurrentGroup(IAssemblerGroup currentGroup, EntityPlayer player) {
		this.currentGroup = currentGroup;
		if(player.worldObj.isRemote){
			if(Minecraft.getMinecraft().currentScreen instanceof GuiModularAssembler){
				GuiModularAssembler assemblerGui = (GuiModularAssembler) Minecraft.getMinecraft().currentScreen;
				//Reload all buttons, to remove the slot buttons.
				assemblerGui.initButtons();
			}
		}
	}

	@Override
	public int getMaxControllers() {
		return maxControllers;
	}

	@Override
	public ItemStack getStack(int index) {
		return tile.getStackInSlot(index);
	}

	@Override
	public void setStack(int index, ItemStack stack) {
		tile.setInventorySlotContents(index, stack);
	}

	@Override
	public int getNextIndex() {
		return getNextIndex(2);
	}

	private int getNextIndex(int index){
		for(IAssemblerGroup group : groups.values()){
			if(group != null){
				for(IAssemblerSlot slot : group.getSlots().values()){
					if(slot.getIndex() == index){
						return getNextIndex(index+1);
					}
				}
			}
		}
		return index;
	}

	@Override
	public void reload() {
		if(tile.getStackInSlot(0) != null){
			updateControllerSlots(null);

			for(int groupID = 0;groupID < maxControllers;groupID++){
				ItemStack controllerStack = tile.getStackInSlot(2+groupID*83);
				if(controllerStack != null){
					IModuleContainer container = ModuleManager.getContainerFromItem(controllerStack);		
					IModuleController module = (IModuleController) container.getModule();
					IAssemblerGroup group = module.createGroup(this, controllerStack, groupID);
				}
			}
		}
	}

	@Override
	public TileEntity getTile() {
		return tile;
	}

	@Override
	public void updateControllerSlots(EntityPlayer player) {
		if(getCasingStack() != null){
			IModuleContainer container = ModuleManager.getContainerFromItem(getCasingStack());
			IModuleCasing casing = ((IModuleCasing)container.getModule());
			IModuleState state = casing.createState(null, container);
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateCreateEvent(state));
			state.createState();
			this.maxControllers = casing.getControllers(state);
		}else if(maxControllers > 0){
			maxControllers = 0;
		}

		if(player != null && player.worldObj != null && player.worldObj.isRemote){
			if(Minecraft.getMinecraft().currentScreen instanceof GuiModularAssembler){
				((GuiModularAssembler)Minecraft.getMinecraft().currentScreen).initButtons();
			}
		}
	}

	@Override
	public void update(EntityPlayer player, boolean moveItem) {
		for(IAssemblerGroup group : groups.values()){
			if(group != null){
				if(group.getControllerSlot().getStack() == null || getCasingStack() == null || group.getGroupID() + 1 > maxControllers){
					groups.put(group.getGroupID(), null);
					if(currentGroup != null && currentGroup.getGroupID() == group.getGroupID()){
						setCurrentGroup(null, player);
					}
					for(IAssemblerSlot slot : group.getSlots().values()){
						slot.onDeleteSlot(player, moveItem);
					}
				}
			}
		}
		for(IAssemblerGroup group : groups.values()){
			if(group != null){
				for(IAssemblerSlot slot : group.getSlots().values()){
					slot.update(player, moveItem);
				}
			}
		}
		assemble(true);
	}

	@Override
	public IModular assemble(boolean withItem){
		setStack(1, null);
		if(getCasingStack() != null && !groups.values().isEmpty()){
			boolean notNull = false;
			for(IAssemblerGroup group : groups.values()){
				if(group != null){
					notNull = true;
				}
			}
			if(!notNull){
				return null;
			}
			IModular modular = new Modular();
			for(IAssemblerGroup group : groups.values()){
				if(group != null){
					ItemStack controllerStack = group.getControllerSlot().getStack();
					if(controllerStack != null){
						IModuleContainer container = ModuleManager.getContainerFromItem(controllerStack);
						IModuleController controller = (IModuleController) container.getModule();
						if(controller.canAssembleGroup(group)){
							continue;
						}else{
							return modular;
						}
					}
				}
			}
			Map<IAssemblerGroup, List<Pair<IAssemblerSlot, IModuleState>>> modules = new HashMap();
			for(IAssemblerGroup group : groups.values()){
				if(group != null){
					List<Pair<IAssemblerSlot, IModuleState>> stateList = new ArrayList();
					for(IAssemblerSlot slot : group.getSlots().values()) {
						ItemStack stack = slot.getStack();
						if(stack != null){
							IModuleContainer container = ModuleManager.getContainerFromItem(stack);
							IModuleState moduleState = container.getModule().createState(modular, container);
							MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleStateCreateEvent(moduleState));
							moduleState.createState();
							stateList.add(new Pair(slot, moduleState));
						}
					}
					modules.put(group, stateList);
				}
			}
			IModuleContainer casingContainer = ModuleManager.getContainerFromItem(getCasingStack());
			IModuleCasing casing = (IModuleCasing) casingContainer.getModule();
			IModuleState casingState = casing.createState(modular, casingContainer);

			if(casing.canAssembleCasing(casingState)){
				casing.onAssembleModule(null, casingState, casingState, modules, true);
				modular.addModule(getCasingStack(), casing);
				casing.onAssembleModule(null, casingState, casingState, modules, false);
			}else{
				return modular;
			}

			for(Entry<IAssemblerGroup, List<Pair<IAssemblerSlot, IModuleState>>> group : modules.entrySet()){
				for(Pair<IAssemblerSlot, IModuleState> slotPair : group.getValue()){
					IAssemblerSlot slot = slotPair.first();
					IModuleState state = slotPair.second();
					IModule module = state.getModule();

					if(module.canAssembleModule(slot, state)){
						module.onAssembleModule(group.getKey(), state, casingState, modules, true);
						modular.addModule(slot.getStack(), module);
						module.onAssembleModule(group.getKey(), state, casingState, modules, false);
					}else{
						return modular;
					}
				}
			}
			modular.onAssembleModular();
			if(withItem){
				ItemStack modularStack = new ItemStack(BlockManager.blockModular);
				modularStack.setTagCompound(modular.writeToNBT(new NBTTagCompound()));
				tile.setInventorySlotContents(1, modularStack);
			}
			return modular;
		}
		return null;
	}
}
