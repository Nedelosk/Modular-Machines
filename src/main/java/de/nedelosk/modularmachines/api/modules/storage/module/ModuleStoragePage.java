package de.nedelosk.modularmachines.api.modules.storage.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.AssemblerItemHandler;
import de.nedelosk.modularmachines.api.modular.IAssemblerGui;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerContainer;
import de.nedelosk.modularmachines.api.modular.assembler.SlotAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.SlotAssemblerStorage;
import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.position.IModulePositioned;
import de.nedelosk.modularmachines.api.modules.position.IModulePostion;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.storage.IStorage;
import de.nedelosk.modularmachines.api.modules.storage.StoragePage;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.items.ItemHandlerHelper;

public class ModuleStoragePage extends StoragePage {

	protected EnumModuleSizes size;

	public ModuleStoragePage(IModularAssembler assembler, IDefaultModuleStorage storage) {
		super(assembler, storage);
		this.size = storage.getSize();
	}

	public ModuleStoragePage(IModularAssembler assembler, EnumModuleSizes size, IStoragePosition position) {
		super(assembler, new AssemblerItemHandler(size.slots, assembler, position), position);
		this.size = size;
	}

	@Override
	public void createSlots(IAssemblerContainer container, List<Slot> slots) {
		if (position != null) {
			SlotAssemblerStorage storageSlot;
			slots.add(storageSlot = new SlotAssemblerStorage(assembler, 44, 35, this, position, container));
			if (size == EnumModuleSizes.LARGEST) {
				for(int i = 0; i < 3; ++i) {
					for(int j = 0; j < 3; ++j) {
						slots.add(new SlotAssembler(itemHandler, j + i * 3, 80 + j * 18, 17 + i * 18, this, container, storageSlot));
					}
				}
			} else if (size == EnumModuleSizes.LARGE) {
				for(int i = 0; i < 3; ++i) {
					slots.add(new SlotAssembler(itemHandler, i, 98, 17 + i * 18, this, container, storageSlot));
				}
			}
		}
	}

	@Override
	public void onSlotChanged(IAssemblerContainer container) {
		if (assembler.getHandler().getWorld().isRemote) {
			if (Minecraft.getMinecraft().currentScreen instanceof IAssemblerGui) {
				((IAssemblerGui) Minecraft.getMinecraft().currentScreen).setHasChange();
			}
		}
		SlotAssemblerStorage slotStorage = (SlotAssemblerStorage) container.getSlots().get(36);
		if (!slotStorage.getHasStack()) {
			for(int index = 0; index < size.slots; index++) {
				ItemStack slotStack = itemHandler.getStackInSlot(index);
				if (slotStack != null) {
					ItemHandlerHelper.giveItemToPlayer(container.getPlayer(), slotStack);
					itemHandler.setStackInSlot(index, null);
				}
			}
		}
		List<SlotAssembler> slots = new ArrayList<>();
		for(int index = 0; index < size.slots; index++) {
			Slot slot = container.getSlots().get(37 + index);
			if (slot instanceof SlotAssembler) {
				slots.add((SlotAssembler) slot);
			}
		}
		for(SlotAssembler slot : slots) {
			slot.hasChange = false;
		}
		for(int index = 0; index < slots.size(); index++) {
			SlotAssembler slot = slots.get(index);
			if (slot.getHasStack()) {
				IModuleItemContainer itemContainer = ModuleManager.getContainerFromItem(slot.getStack());
				if (itemContainer == null) {
					ItemHandlerHelper.giveItemToPlayer(container.getPlayer(), slot.getStack());
					itemHandler.setStackInSlot(1 + index, null);
					onSlotChanged(container);
					return;
				}
				EnumModuleSizes size = itemContainer.getSize();
				int usedSlots = 1;
				int testedSlots = 0;
				if (usedSlots < size.slots & index - testedSlots > 0) {
					do {
						SlotAssembler otherSlot = slots.get(index - usedSlots);
						if (otherSlot != null && !otherSlot.getHasStack() && !otherSlot.hasChange) {
							otherSlot.setActive(false);
							usedSlots++;
						}
						testedSlots++;
					} while (usedSlots < size.slots & index - testedSlots > 0);
				}
				if (usedSlots < size.slots) {
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
					} while (usedSlots < size.slots);
				}
				if (usedSlots < size.slots) {
					getClass();
				}
			}
		}
		for(SlotAssembler slot : slots) {
			if (!slot.hasChange) {
				slot.setActive(true);
			}
		}
	}

	@Override
	public boolean isItemValid(ItemStack stack, SlotAssembler slot, SlotAssemblerStorage storageSlot) {
		IModuleItemContainer itemContainer = ModuleManager.getContainerFromItem(stack);
		if (slot == null || itemContainer == null) {
			return false;
		}
		boolean hasPosition = false;
		for(IModuleContainer container : itemContainer.getContainers()) {
			IModule module = container.getModule();
			if (storageSlot == null || !storageSlot.getHasStack()) {
				return false;
			}
			if (module instanceof IModulePositioned) {
				IModulePostion[] positions = ((IModulePositioned) module).getValidPositions(container);
				hasPosition = positions.length <= 0;
				if (!hasPosition) {
					for(IModulePostion otherPositionStorage : position.getPostions()) {
						for(IModulePostion otherPositionModule : positions) {
							if (otherPositionModule == otherPositionStorage) {
								hasPosition = true;
								break;
							}
						}
					}
				}
			}
		}
		if (!hasPosition) {
			return false;
		}
		EnumModuleSizes usedSize = itemContainer.getSize();
		for(int index = 0; index < position.getSize().slots; index++) {
			IModuleItemContainer otherItemContainer = ModuleManager.getContainerFromItem(itemHandler.getStackInSlot(index));
			if (otherItemContainer != null) {
				usedSize = EnumModuleSizes.getSize(usedSize, otherItemContainer.getSize());
			}
		}
		Boolean isValid = null;
		if (usedSize != EnumModuleSizes.UNKNOWN && (usedSize == null || usedSize.ordinal() <= position.getSize().ordinal())) {
			for(IModuleContainer container : itemContainer.getContainers()) {
				if (container.getModule().isValid(assembler, position, stack, slot, storageSlot)) {
					if (itemContainer.needOnlyOnePosition(container)) {
						isValid = true;
						break;
					}
					if (isValid == null) {
						isValid = true;
					}
				} else {
					isValid = false;
				}
			}
		}
		if (isValid == null) {
			isValid = false;
		}
		return isValid;
	}

	@Override
	public IStorage assemble(IModular modular) throws AssemblerException {
		IStorage storage = super.assemble(modular);
		if (storage instanceof IAddableModuleStorage) {
			IAddableModuleStorage moduleStorage = (IAddableModuleStorage) storage;
			for(int index = 0; index < itemHandler.getSlots(); index++) {
				ItemStack stack = itemHandler.getStackInSlot(index);
				if (stack != null) {
					IModuleProvider provider = ModuleManager.loadOrCreateModuleProvider(modular, stack);
					if (provider != null && !provider.getModuleStates().isEmpty()) {
						moduleStorage.addModule(provider);
					}
				}
			}
		}
		return storage;
	}

	@Override
	public void canAssemble(IModular modular) throws AssemblerException {
		int complexity = assembler.getComplexity(false, position);
		int allowedComplexity = assembler.getAllowedComplexity(position);
		if (allowedComplexity == 0) {
			return;
		}
		if (allowedComplexity < complexity) {
			throw new AssemblerException(I18n.translateToLocalFormatted("modular.assembler.error.complexity.position", position.getLocName().toLowerCase(Locale.ENGLISH)));
		}
	}
}
