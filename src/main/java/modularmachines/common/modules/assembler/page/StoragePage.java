/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.common.modules.assembler.page;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.items.IItemHandlerModifiable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.assemblers.AssemblerError;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.IModuleSlot;
import modularmachines.api.modules.assemblers.IModuleSlots;
import modularmachines.api.modules.assemblers.IStoragePage;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.api.modules.storages.IStorage;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.common.modules.assembler.ModuleSlots;

public abstract class StoragePage implements IStoragePage {
	
	protected final IAssembler assembler;
	protected final IStoragePosition position;
	protected final IModuleSlots slots;
	protected boolean wasInitialized;
	
	public StoragePage(IAssembler assembler, IStoragePosition position, int size) {
		this.assembler = assembler;
		this.position = position;
		this.slots = new ModuleSlots(size, this);
		this.wasInitialized = false;
	}
	
	public StoragePage(IAssembler assembler, IStoragePosition position) {
		this.assembler = assembler;
		this.position = position;
		this.slots = new ModuleSlots(1, this);
		this.wasInitialized = false;
	}
	
	@Override
	public void init() {
		wasInitialized = true;
	}
	
	@Override
	public boolean wasInitialized() {
		return wasInitialized;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("Slots", slots.writeToNBT(new NBTTagCompound()));
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		slots.readFromNBT(compound.getCompoundTag("Slots"));
		slots.reload();
	}

	@Override
	public void canAssemble(IAssembler assembler, List<AssemblerError> errors) {
	}

	@Override
	public void createContainerSlots(Container container, EntityPlayer player, IAssembler assembler, List<Slot> slots) {
	}

	@Override
	public void onSlotChanged(EntityPlayer player, IAssembler assembler) {
	}
	
	@Override
	public IStorage assemble(IAssembler assembler, IModuleLogic logic) {
		ItemStack storageStack = getStorageStack();
		if (!storageStack.isEmpty()) {
			IModuleContainer container = ModuleHelper.getContainerFromItem(storageStack);
			ModuleData data = container.getData();
			if (data != null) {
				return data.createStorage(logic, position, this);
			}
		}
		return null;
	}
	
	@Override
	public IItemHandlerModifiable getItemHandler() {
		return slots.getItemHandler();
	}
	
	@Override
	public IAssembler getAssembler() {
		return assembler;
	}
	
	@Override
	public IStoragePosition getPosition(){
		return position;
	}
	
	@Override
	public ItemStack getStorageStack(){
		return slots.getStorageSlot().getItem();
	}
	
	@Override
	@Nullable
	public StoragePage getParent(){
		return null;
	}
	
	@Override
	public int getComplexity() {
		int complexity = 0;
		for (IModuleSlot slot : slots) {
			ItemStack slotStack = slot.getItem();
			IModuleContainer container = ModuleHelper.getContainerFromItem(slotStack);
			if (container != null) {
				ModuleData data = container.getData();
				complexity += data.getComplexity();
			}
		}
		return complexity;
	}
	
	@Override
	public int getAllowedComplexity() {
		int allowedComplexity = 0;
		for (IModuleSlot slot : slots) {
			ItemStack slotStack = slot.getItem();
			IModuleContainer container = ModuleHelper.getContainerFromItem(slotStack);
			if (container != null) {
				ModuleData data = container.getData();
				allowedComplexity += data.getAllowedComplexity();
			}
		}
		return allowedComplexity;
	}
	
	@Override
	public IModuleSlots getSlots() {
		return slots;
	}
	
	@Override
	public boolean isEmpty(){
		return false;
	}

	@Override
	public void setContainer(Container container) {
		
	}

	@Override
	public void detectAndSendChanges() {
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void setGui(GuiContainer gui) {
		
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void initGui() {
		
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawTooltips(int mouseX, int mouseY) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleMouseClicked(int mouseX, int mouseY, int mouseButton) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawBackground(int mouseX, int mouseY) {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateGui() {
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void drawForeground(FontRenderer fontRenderer, int mouseX, int mouseY) {
	}
	
}
