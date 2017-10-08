/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.common.modules.assembler.page;

import java.util.List;
import java.util.Locale;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;

import net.minecraftforge.items.ItemHandlerHelper;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.assemblers.AssemblerError;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.IModuleSlot;
import modularmachines.api.modules.assemblers.SlotModule;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.common.core.ModularMachines;
import modularmachines.common.utils.ItemUtil;

public class ModuleStoragePage extends StoragePage {

	protected EnumModuleSizes size;

	public ModuleStoragePage(IAssembler assembler, IStoragePosition position, EnumModuleSizes size) {
		super(assembler, position, size.slotNumbers + 1);
		this.size = size;
	}
	
	@Override
	public void createContainerSlots(Container container, EntityPlayer player, IAssembler assembler, List<Slot> slots) {
		if (position != null) {
			slots.add(new SlotModule(this.slots.getStorageSlot(), 44, 35, player));
			if (size == EnumModuleSizes.LARGEST) {
				for (int i = 0; i < 3; ++i) {
					for (int j = 0; j < 3; ++j) {
						IModuleSlot slot = this.slots.getSlot(1 + j + i * 3);
						slots.add(new SlotModule(slot, 80 + j * 18, 17 + i * 18, player));
					}
				}
			} else if (size == EnumModuleSizes.LARGE) {
				for (int i = 0; i < 3; ++i) {
					IModuleSlot slot = this.slots.getSlot(i + 1);
					slots.add(new SlotModule(slot, 98, 17 + i * 18, player));
				}
			}
		}
	}
	
	@Override
	public void onSlotChanged(EntityPlayer player, IAssembler assembler) {
		ModularMachines.proxy.onAssemblerGuiChange();
		IModuleSlot storageSlot = slots.getStorageSlot();
		if(!storageSlot.hasItem()){
			for (IModuleSlot slot : slots.getSlots()) {
				if(slot.isStorage()){
					continue;
				}
				int index = slot.getIndex();
				ItemStack slotStack = slots.getItem(index);
				if (!ItemUtil.isEmpty(slotStack)) {
					ItemHandlerHelper.giveItemToPlayer(player, slotStack);
					slots.setItem(index, ItemUtil.empty());
				}
			}
			slots.reload();
		}
		/*List<SlotAssembler> slots = new ArrayList<>();
		for (int index = 0; index < size.slotNumbers; index++) {
			Slot slot = container.inventorySlots.get(37 + index);
			if (slot instanceof SlotAssembler) {
				slots.add((SlotAssembler) slot);
			}
		}
		for (SlotAssembler slot : slots) {
			slot.hasChange = false;
		}
		for (int index = 0; index < slots.size(); index++) {
			SlotAssembler slot = slots.get(index);
			if (slot.getHasStack()) {
				IModuleContainer moduleContainer = ModuleHelper.getContainerFromItem(slot.getStack());
				if (moduleContainer == null) {
					ItemHandlerHelper.giveItemToPlayer(player, slot.getStack());
					itemHandler.setStackInSlot(1 + index, ItemUtil.empty());
					onSlotChanged(container, assembler);
					return;
				}
				EnumModuleSizes size = moduleContainer.getData().getSize();
				int usedSlots = 1;
				int testedSlots = 0;
				if (usedSlots < size.slotNumbers & index - testedSlots > 0) {
					do {
						SlotAssembler otherSlot = slots.get(index - usedSlots);
						if (otherSlot != null && !otherSlot.getHasStack() && !otherSlot.hasChange) {
							otherSlot.setActive(false);
							usedSlots++;
						}
						testedSlots++;
					} while (usedSlots < size.slotNumbers & index - testedSlots > 0);
				}
				if (usedSlots < size.slotNumbers) {
					int newIndex = index;
					do {
						newIndex++;
						if (newIndex < slots.size()) {
							SlotAssembler otherSlot = slots.get(newIndex);
							if (otherSlot != null && !otherSlot.getHasStack() && !otherSlot.hasChange) {
								otherSlot.setActive(false);
								usedSlots++;
							}
						} else {
							break;
						}
					} while (usedSlots < size.slotNumbers);
				}
				if (usedSlots < size.slotNumbers) {
					getClass();
				}
			}
		}
		for (SlotAssembler slot : slots) {
			if (!slot.hasChange) {
				slot.setActive(true);
			}
		}*/
	}
	
	/*@Override
	public boolean isItemValid(ItemStack stack) {
		IModuleContainer container = ModuleHelper.getContainerFromItem(stack);
		if (container == null) {
			return false;
		}
		boolean hasPosition = false;
		ModuleData data = container.getData();
		if (!data.isPositionValid(position)) {
			return false;
		}
		EnumModuleSizes usedSize = data.getSize();
		for (int index = 0; index < position.getSize().slotNumbers; index++) {
			IModuleContainer otherContainer = ModuleHelper.getContainerFromItem(itemHandler.getStackInSlot(index + 1));
			if (otherContainer != null) {
				usedSize = EnumModuleSizes.getSize(usedSize, otherContainer.getData().getSize());
			}
		}
		if (usedSize != EnumModuleSizes.UNKNOWN && (usedSize == null || usedSize.ordinal() <= position.getSize().ordinal())) {
			return data.isItemValid(assembler, position, stack);
		}
		return false;
	}*/
	
	@Override
	public void canAssemble(IAssembler assembler, List<AssemblerError> errors) {
		int complexity = getComplexity();
		int allowedComplexity = getAllowedComplexity();
		if (allowedComplexity == 0) {
			return;
		}
		if (allowedComplexity < complexity) {
			errors.add(new AssemblerError(I18n.translateToLocalFormatted("modular.assembler.error.complexity.position", position.getDisplayName().toLowerCase(Locale.ENGLISH))));
		}
	}
}
