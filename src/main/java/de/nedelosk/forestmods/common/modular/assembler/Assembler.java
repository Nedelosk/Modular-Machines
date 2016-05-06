package de.nedelosk.forestmods.common.modular.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import de.nedelosk.forestmods.client.gui.GuiModularAssembler;
import de.nedelosk.forestmods.common.blocks.tile.TileModularAssembler;
import de.nedelosk.forestmods.common.core.ForestMods;
import de.nedelosk.forestmods.common.inventory.ContainerModularAssembler;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.network.packets.PacketModularAssemblerCreateGroup;
import de.nedelosk.forestmods.common.network.packets.PacketModularAssemblerSelectGroup;
import de.nedelosk.forestmods.library.modular.assembler.IAssembler;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerGroup;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerSlot;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.IModuleController;
import de.nedelosk.forestmods.library.modules.ModuleManager;
import de.nedelosk.forestmods.library.modules.casing.IModuleCasing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
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
		for(int groupID = 0;groupID < maxControllers;groupID++){
			ItemStack controllerStack = tile.getStackInSlot(2+groupID*83);
			if(controllerStack != null){
				IModuleContainer container = ModuleManager.moduleRegistry.getContainerFromItem(controllerStack);		
				IModuleController module = ModuleManager.moduleRegistry.getFakeModule(container);
				IAssemblerGroup group = module.createGroup(this, controllerStack, groupID);
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
	public void updateControllerSlots() {
		if(getCasingStack() != null){
			this.maxControllers = ((IModuleCasing)ModuleManager.moduleRegistry.getFakeModule(ModuleManager.moduleRegistry.getContainerFromItem(getCasingStack()))).getControllers();
		}else if(maxControllers > 0){
			maxControllers = 0;
		}
	}
	
	@Override
	public void updateActivitys(EntityPlayer player, boolean moveItem) {
		for(IAssemblerGroup group : groups.values()){
			if(group != null){
				if(group.getControllerSlot().getStack() == null || getCasingStack() == null || group.getGroupID() + 1 >= maxControllers){
					groups.put(group.getGroupID(), null);
					if(currentGroup != null && currentGroup.getGroupID() == group.getGroupID()){
						setCurrentGroup(null, player);
					}
					for(IAssemblerSlot slot : group.getSlots()){
						slot.onDeleteSlot(player, true);
					}
				}
			}
		}
		for(IAssemblerGroup group : groups.values()){
			if(group != null){
				for(IAssemblerSlot slot : group.getSlots()){
					slot.testActivity(player);
				}
			}
		}
	}
	
	@Override
	public void assemble(){
		if(getCasingStack() != null){
			for(IAssemblerGroup group : groups.values()){
				if(group != null){
					if(group.getControllerSlot().getStack() != null){
						IModuleContainer container = ModuleManager.moduleRegistry.getContainerFromItem(group.getControllerSlot().getStack());
						IModuleController controller = ModuleManager.moduleRegistry.getFakeModule(container);
						if(controller.canAssembleModular(container, this, group)){
							continue;
						}else{
							return;
						}
					}
				}
			}
		}
	}
}
