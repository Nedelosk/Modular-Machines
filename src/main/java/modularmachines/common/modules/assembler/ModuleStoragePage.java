package modularmachines.common.modules.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.items.ItemHandlerHelper;

import modularmachines.api.modules.EnumModuleSizes;
import modularmachines.api.modules.ModuleData;
import modularmachines.api.modules.ModuleHelper;
import modularmachines.api.modules.assemblers.AssemblerError;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.SlotAssembler;
import modularmachines.api.modules.assemblers.SlotAssemblerStorage;
import modularmachines.api.modules.assemblers.StoragePage;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.common.core.ModularMachines;
import modularmachines.common.utils.ContainerUtil;
import modularmachines.common.utils.ItemUtil;

public class ModuleStoragePage extends StoragePage {

	protected EnumModuleSizes size;

	public ModuleStoragePage(IAssembler assembler, IStoragePosition position, EnumModuleSizes size) {
		super(assembler, position, size.slotNumbers + 1);
		this.size = size;
	}
	
	@Override
	public void createSlots(Container container, IAssembler assembler, List<Slot> slots) {
		if (position != null) {
			SlotAssemblerStorage storageSlot;
			slots.add(storageSlot = new SlotAssemblerStorage(assembler, container, 44, 35, this));
			if (size == EnumModuleSizes.LARGEST) {
				for (int i = 0; i < 3; ++i) {
					for (int j = 0; j < 3; ++j) {
						slots.add(new SlotAssembler(assembler, container, 1 + j + i * 3, 80 + j * 18, 17 + i * 18, this, storageSlot));
					}
				}
			} else if (size == EnumModuleSizes.LARGE) {
				for (int i = 0; i < 3; ++i) {
					slots.add(new SlotAssembler(assembler, container, i + 1, 98, 17 + i * 18, this, storageSlot));
				}
			}
		}
	}
	
	@Override
	public void onSlotChanged(Container container, IAssembler assembler) {
		EntityPlayer player = ContainerUtil.getPlayer(container);
		ModularMachines.proxy.onAssemblerGuiChange();
		SlotAssemblerStorage slotStorage = (SlotAssemblerStorage) container.inventorySlots.get(36);
		if (!slotStorage.getHasStack()) {
			for (int index = 0; index < size.slotNumbers; index++) {
				ItemStack slotStack = itemHandler.getStackInSlot(index);
				if (!ItemUtil.isEmpty(slotStack)) {
					ItemHandlerHelper.giveItemToPlayer(player, slotStack);
					itemHandler.setStackInSlot(index, ItemUtil.empty());
				}
			}
		}
		List<SlotAssembler> slots = new ArrayList<>();
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
		}
	}
	
	@Override
	public boolean isItemValid(ItemStack stack, SlotAssembler slot, SlotAssemblerStorage storageSlot) {
		IModuleContainer container = ModuleHelper.getContainerFromItem(stack);
		if (slot == null || container == null) {
			return false;
		}
		boolean hasPosition = false;
		ModuleData data = container.getData();
		if (storageSlot == null || !storageSlot.getHasStack()) {
			return false;
		}
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
			return data.isItemValid(assembler, position, stack, slot, storageSlot);
		}
		return false;
	}
	
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
