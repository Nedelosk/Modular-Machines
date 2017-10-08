/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.common.modules.assembler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.IModuleSlot;
import modularmachines.api.modules.assemblers.IModuleSlots;
import modularmachines.api.modules.assemblers.IStoragePage;
import modularmachines.api.modules.assemblers.ItemStackHandlerPage;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.storages.IStoragePosition;

public class ModuleSlots implements IModuleSlots {
	private final List<IModuleSlot> slots;
	protected final ItemStackHandlerPage itemHandler;
	private final int size;
	private final IStoragePage page;
	private final IModuleSlot storageSlot;
	private int usedSpace;
	
	public ModuleSlots(int size, IStoragePage page) {
		this.page = page;
		this.itemHandler = new ItemStackHandlerPage(size, page);
		size-=1;
		this.size = size;
		this.slots = new ArrayList<>();
		slots.add(this.storageSlot = new ModuleSlot(this, 0, true, EnumModuleSizes.LARGEST.slotNumbers));
		for(int i = 1;i <= size;i++){
			slots.add(new ModuleSlot(this, i, false, 0));
		};
		this.usedSpace = 0;
	}
	
	@Override
	public IStoragePage getPage() {
		return page;
	}
	
	@Override
	public void reload() {
		usedSpace=0;
		for(IModuleSlot slot : slots){
			if(slot.isStorage()){
				continue;
			}
			ItemStack itemStack = slot.getItem();
			if(itemStack.isEmpty()){
				continue;
			}
			onAddItem(slot, itemStack);
		}
	}
	
	@Override
	public boolean isItemValid(ItemStack itemStack, boolean storage) {
		IModuleContainer container = ModuleHelper.getContainerFromItem(itemStack);
		if (container == null) {
			return false;
		}
		ModuleData data = container.getData();
		IStoragePosition pagePosition = page.getPosition();
		IAssembler assembler = page.getAssembler();
		if(storage){
			if(storageSlot.hasItem()){
				return false;
			}
			if(!data.isStorage(pagePosition) || !data.isItemValid(assembler, pagePosition, itemStack)){
				return false;
			}
			return true;
		}
		if (!data.isPositionValid(pagePosition)) {
			return false;
		}
		int neededSpace = data.getSize().slotNumbers;
		if(neededSpace + usedSpace > size){
			return false;
		}
		if(!data.isItemValid(assembler, pagePosition, itemStack)){
			return false;
		}
		return true;
	}
	
	@Override
	public boolean addItem(int index, ItemStack itemStack) {
		IModuleSlot slot = getSlot(index);
		if(slot == null){
			return false;
		}
		boolean storage = slot.isStorage();
		if(!isItemValid(itemStack, storage)){
			return false;
		}
		ItemStack addedItem = itemHandler.insertItem(index, itemStack, false);
		if(!addedItem.isEmpty()){
			return false;
		}
		onAddItem(slot, itemStack);
		return true;
	}
	
	@Override
	public void setItem(int index, ItemStack itemStack) {
		IModuleSlot slot = getSlot(index);
		if(slot == null){
			return;
		}
		itemHandler.setStackInSlot(index, itemStack);
		if(!slot.isStorage()) {
			if (itemStack.isEmpty()) {
				usedSpace -= slot.getSize();
			} else {
				onAddItem(slot, itemStack);
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("Items", itemHandler.serializeNBT());
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		itemHandler.deserializeNBT(compound.getCompoundTag("Items"));
		reload();
	}
	
	protected void onAddItem(IModuleSlot slot, ItemStack itemStack) {
		if(slot.isStorage()){
			return;
		}
		IModuleContainer container = ModuleHelper.getContainerFromItem(itemStack);
		if(container == null){
			return;
		}
		ModuleData data = container.getData();
		int neededSpace = data.getSize().slotNumbers;
		usedSpace +=neededSpace;
		slot.setSize(neededSpace);
	}
	
	@Override
	public ItemStack removeItem(int index, ItemStack originalStack) {
		IModuleSlot slot = getSlot(index);
		if(slot == null || originalStack.isEmpty()){
			return ItemStack.EMPTY;
		}
		if(!slot.isStorage()){
			usedSpace -=slot.getSize();
		}
		ItemStack itemStack = getItem(index);
		if(itemStack.isEmpty()){
			return originalStack;
		}
		ItemStack extractedItem = itemHandler.extractItem(index, 1, false);
		if(extractedItem.isEmpty()){
			return ItemStack.EMPTY;
		}
		return extractedItem;
	}
	
	@Override
	@Nullable
	public IModuleSlot getSlot(int index) {
		if(index >= slots.size()){
			return null;
		}
		return slots.get(index);
	}
	
	@Override
	public ItemStack getItem(int index) {
		return itemHandler.getStackInSlot(index);
	}
	
	@Override
	public ItemStackHandlerPage getItemHandler() {
		return itemHandler;
	}
	
	@Override
	public int getUsedSpace() {
		return usedSpace;
	}
	
	@Override
	public int getSize() {
		return size;
	}
	
	@Override
	public List<IModuleSlot> getSlots() {
		return Collections.unmodifiableList(slots);
	}
	
	@Override
	public IModuleSlot getStorageSlot() {
		return storageSlot;
	}
	
	@Override
	public Iterator<IModuleSlot> iterator() {
		return slots.iterator();
	}
}
