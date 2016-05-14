package de.nedelosk.forestmods.common.modular.assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import akka.japi.Pair;
import de.nedelosk.forestmods.client.gui.GuiModularAssembler;
import de.nedelosk.forestmods.common.blocks.tile.TileModularAssembler;
import de.nedelosk.forestmods.common.core.BlockManager;
import de.nedelosk.forestmods.common.inventory.ContainerModularAssembler;
import de.nedelosk.forestmods.common.modular.Modular;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modular.assembler.IAssembler;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerGroup;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerSlot;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.IModuleController;
import de.nedelosk.forestmods.library.modules.ModuleManager;
import de.nedelosk.forestmods.library.modules.casing.IModuleCasing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

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
				//Reload all butoons, to remove the slot buttons.
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
	public int getNextIndex(IAssemblerGroup group) {
		return getNextIndex(2 + group.getGroupID() * 81);
	}

	private int getNextIndex(int index){
		for(IAssemblerGroup group : groups.values()){
			if(group != null){
				for(IAssemblerSlot slot : group.getSlots()){
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
					IModuleContainer container = ModuleManager.moduleRegistry.getContainerFromItem(controllerStack);		
					IModuleController module = ModuleManager.moduleRegistry.getFakeModule(container);
					IAssemblerGroup group = module.createGroup(this, controllerStack, groupID);
				}
			}
		}
	}

	@Override
	public IAssemblerSlot getSlot(int index) {
		for(IAssemblerGroup group : groups.values()){
			if(group != null){
				for(IAssemblerSlot slot : group.getSlots()){
					if(slot.getIndex() == index){
						return slot;
					}
				}
			}
		}
		return null;
	}

	@Override
	public TileEntity getTile() {
		return tile;
	}

	@Override
	public void updateControllerSlots(EntityPlayer player) {
		if(getCasingStack() != null){
			this.maxControllers = ((IModuleCasing)ModuleManager.moduleRegistry.getFakeModule(ModuleManager.moduleRegistry.getContainerFromItem(getCasingStack()))).getControllers();
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
					for(IAssemblerSlot slot : group.getSlots()){
						slot.onDeleteSlot(player, moveItem);
					}
				}
			}
		}
		for(IAssemblerGroup group : groups.values()){
			if(group != null){
				for(IAssemblerSlot slot : group.getSlots()){
					slot.update(player, moveItem);
				}
			}
		}
		assemble();
	}

	@Override
	public void assemble(){
		setStack(1, null);
		if(getCasingStack() != null && !groups.values().isEmpty()){
			boolean notNull = false;
			for(IAssemblerGroup group : groups.values()){
				if(group != null){
					notNull = true;
				}
			}
			if(!notNull){
				return;
			}
			IModular modular = new Modular();
			for(IAssemblerGroup group : groups.values()){
				if(group != null){
					ItemStack controllerStack = group.getControllerSlot().getStack();
					if(controllerStack != null){
						IModuleContainer container = ModuleManager.moduleRegistry.getContainerFromItem(controllerStack);
						IModuleController controller = ModuleManager.moduleRegistry.createModule(modular, container);
						if(controller.canAssembleGroup(group)){
							continue;
						}else{
							return;
						}
					}
				}
			}
			Map<IAssemblerGroup, List<Pair<IAssemblerSlot, IModule>>> modules = new HashMap();
			for(IAssemblerGroup group : groups.values()){
				if(group != null){
					List<Pair<IAssemblerSlot, IModule>> moduleList = new ArrayList();
					for(IAssemblerSlot slot : group.getSlots()) {
						ItemStack stack = slot.getStack();
						if(stack != null){
							IModuleContainer container = ModuleManager.moduleRegistry.getContainerFromItem(stack);
							moduleList.add(new Pair(slot, ModuleManager.moduleRegistry.createModule(modular, container)));
						}
					}
					modules.put(group, moduleList);
				}
			}
			IModuleContainer container = ModuleManager.moduleRegistry.getContainerFromItem(getCasingStack());
			IModuleCasing casing = ModuleManager.moduleRegistry.createModule(modular, container);

			if(casing.canAssembleCasing()){
				casing.onAddToModular(null, casing, modules, true);
				modular.addModule(getCasingStack(), casing);
				casing.onAddToModular(null, casing, modules, false);
			}else{
				return;
			}

			for(Entry<IAssemblerGroup, List<Pair<IAssemblerSlot, IModule>>> group : modules.entrySet()){
				for(Pair<IAssemblerSlot, IModule> slotPair : group.getValue()){
					IAssemblerSlot slot = slotPair.first();
					IModule module = slotPair.second();
					if(module.canAssembleSlot(slot)){
						module.onAddToModular(group.getKey(), casing, modules, true);
						modular.addModule(slot.getStack(), module);
						module.onAddToModular(group.getKey(), casing, modules, false);
					}else{
						return;
					}
				}
			}
			modular.onAssembleModular();
			ItemStack modularStack = new ItemStack( BlockManager.blockModular);
			NBTTagCompound compoundTag = new NBTTagCompound();
			modular.writeToNBT(compoundTag);
			modularStack.setTagCompound(compoundTag);
			tile.setInventorySlotContents(1, modularStack);
		}
	}
}
