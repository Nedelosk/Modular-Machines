package de.nedelosk.modularmachines.api.modules.storage.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.AssemblerItemHandler;
import de.nedelosk.modularmachines.api.modular.IAssemblerGui;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.position.IModulePositioned;
import de.nedelosk.modularmachines.api.modules.position.IModulePostion;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storage.IStorage;
import de.nedelosk.modularmachines.api.modules.storage.SlotAssembler;
import de.nedelosk.modularmachines.api.modules.storage.SlotAssemblerStorage;
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
	public void createSlots(IContainerBase<IModularHandler> container, List<Slot> slots) {
		if(position != null){
			SlotAssemblerStorage storageSlot;
			slots.add(storageSlot = new SlotAssemblerStorage(assembler.getItemHandler(), assembler.getIndex(position), 44, 35, this, position, container));
			if(size	== EnumModuleSizes.LARGEST){
				for (int i = 0; i < 3; ++i){
					for (int j = 0; j < 3; ++j){
						slots.add(new SlotAssembler(itemHandler, j + i * 3, 80 + j * 18, 17 + i * 18, this, container, storageSlot));
					}
				}
			}else if(size == EnumModuleSizes.LARGE){
				for (int i = 0; i < 3; ++i){
					slots.add(new SlotAssembler(itemHandler, i, 98, 17 + i * 18, this, container, storageSlot));
				}
			}
		}
	}

	@Override
	public void onSlotChanged(IContainerBase<IModularHandler> container) {
		if(assembler.getHandler().getWorld().isRemote){
			if(Minecraft.getMinecraft().currentScreen instanceof IAssemblerGui){
				((IAssemblerGui) Minecraft.getMinecraft().currentScreen).setHasChange();
			}
		}
		SlotAssemblerStorage slotStorage = (SlotAssemblerStorage) container.getSlots().get(36);
		if(!slotStorage.getHasStack()){
			for(int index = 0;index < size.slots;index++){
				ItemStack slotStack = itemHandler.getStackInSlot(index);
				if(slotStack != null){
					ItemHandlerHelper.giveItemToPlayer(container.getPlayer(), slotStack);
					itemHandler.setStackInSlot(index, null);
				}
			}
		}
		List<SlotAssembler> slots = new ArrayList<>();
		for(int index = 0;index < size.slots;index++){
			Slot slot = container.getSlots().get(37 + index);
			if(slot instanceof SlotAssembler){
				slots.add((SlotAssembler) slot);
			}
		}
		for(SlotAssembler slot : slots){
			slot.hasChange = false;
		}
		for(int index = 0;index < slots.size();index++){
			SlotAssembler slot = slots.get(index);
			if(slot.getHasStack()){
				IModuleContainer moduleContainer = ModularMachinesApi.getContainerFromItem(slot.getStack());
				if(moduleContainer == null){
					ItemHandlerHelper.giveItemToPlayer(container.getPlayer(), slot.getStack());
					itemHandler.setStackInSlot(1 + index, null);
					onSlotChanged(container);
					return;
				}
				EnumModuleSizes size = moduleContainer.getModule().getSize(moduleContainer);
				int usedSlots = 1;
				int testedSlots = 0;
				if(usedSlots < size.slots & index - testedSlots > 0){
					do{
						SlotAssembler otherSlot = slots.get(index - usedSlots);
						if(otherSlot != null && !otherSlot.getHasStack() && !otherSlot.hasChange){
							otherSlot.setActive(false);
							usedSlots++;
						}
						testedSlots++;
					}while(usedSlots < size.slots & index - testedSlots > 0);
				}
				if(usedSlots < size.slots){
					int newIndex = index;
					do{
						newIndex++;
						if(newIndex < slots.size()){
							SlotAssembler otherSlot = slots.get(newIndex);
							if(otherSlot != null && !otherSlot.getHasStack() && !otherSlot.hasChange){
								otherSlot.setActive(false);
								usedSlots++;
							}
						}else{
							break;
						}
					}while(usedSlots < size.slots);
				}
				if(usedSlots < size.slots){
					getClass();
				}
			}
		}
		for(SlotAssembler slot : slots){
			if(!slot.hasChange){
				slot.setActive(true);
			}
		}
		/* if(pos != EnumStoragePositions.INTERNAL){
			SlotAssembler slotFirst = (SlotAssembler) inventorySlots.get(37);
			SlotAssembler slotSecond = (SlotAssembler) inventorySlots.get(38);
			SlotAssembler slotLast = (SlotAssembler) inventorySlots.get(39);
			slotFirst.hasChange = false;
			slotSecond.hasChange = false;
			slotLast.hasChange = false;
			if(slotFirst.getHasStack()){
				IModuleContainer containerFirst = ModularMachinesApi.getContainerFromItem(slotFirst.getStack());
				if(containerFirst.getModule().getSize(containerFirst) == EnumModuleSizes.LARGE){
					slotSecond.setActive(false);
					slotLast.setActive(false);
				}else if(containerFirst.getModule().getSize(containerFirst) == EnumModuleSizes.MEDIUM){
					if(!slotSecond.getHasStack()){
						slotSecond.setActive(false);
					}else{
						slotLast.setActive(false);
					}
				}
			}
			if(slotSecond.getHasStack()){
				IModuleContainer containerSecond = ModularMachinesApi.getContainerFromItem(slotSecond.getStack());
				if(containerSecond.getModule().getSize(containerSecond) == EnumModuleSizes.LARGE){
					slotFirst.setActive(false);
					slotLast.setActive(false);
				}else if(containerSecond.getModule().getSize(containerSecond) == EnumModuleSizes.MEDIUM){
					if(!slotFirst.getHasStack()){
						slotFirst.setActive(false);
					}else{
						slotLast.setActive(false);
					}
				}
			}
			if(slotLast.getHasStack()){
				IModuleContainer containerLast = ModularMachinesApi.getContainerFromItem(slotLast.getStack());
				if(containerLast.getModule().getSize(containerLast) == EnumModuleSizes.LARGE){
					slotFirst.setActive(false);
					slotSecond.setActive(false);
				}else if(containerLast.getModule().getSize(containerLast) == EnumModuleSizes.MEDIUM){
					if(!slotSecond.getHasStack()){
						slotSecond.setActive(false);
					}else{
						slotFirst.setActive(false);
					}
				}
			}
			if(!slotFirst.hasChange){
				slotFirst.setActive(true);
			}
			if(!slotSecond.hasChange){
				slotSecond.setActive(true);
			}
			if(!slotLast.hasChange){
				slotLast.setActive(true);
			}
		}*/
	}

	public boolean isValidForPosition(IStoragePosition position, IModuleContainer container){
		return true;
	}

	@Override
	public boolean isItemValid(ItemStack stack, SlotAssembler slot, SlotAssemblerStorage storageSlot) {
		IModuleContainer container = ModularMachinesApi.getContainerFromItem(stack);
		if(slot == null || container == null){
			return false;
		}
		IModule module = container.getModule();
		if(storageSlot == null || !storageSlot.getHasStack()){
			return false;
		}
		if(module instanceof IModulePositioned){
			IModulePostion[] positions = ((IModulePositioned) module).getValidPositions(container);
			boolean hasPosition = positions.length <= 0;
			if(!hasPosition){
				for(IModulePostion otherPositionStorage : position.getPostions()){
					for(IModulePostion otherPositionModule : positions){
						if(otherPositionModule == otherPositionStorage){
							hasPosition = true;
						}
					}
				}
			}
			if(!hasPosition){
				return false;
			}
		}
		EnumModuleSizes usedSize = null;
		for(int index = 0;index < position.getSize().slots;index++){
			IModuleContainer otherContainer = ModularMachinesApi.getContainerFromItem(itemHandler.getStackInSlot(index));
			if(otherContainer != null){
				usedSize = EnumModuleSizes.getSize(usedSize, otherContainer.getModule().getSize(otherContainer));
			}
		}
		if(usedSize != EnumModuleSizes.UNKNOWN && (usedSize == null || usedSize.ordinal() <= position.getSize().ordinal())){
			return true;
		}
		return false;
		/*EnumStoragePositions pos = assembler.getSelectedPosition();
		IItemHandler itemHandler = assembler.getItemHandler();
		int index = slot.getSlotIndex() - pos.startSlotIndex;
		IModuleContainer container = ModularMachinesApi.getContainerFromItem(stack);
		if(container == null){
			return false;
		}
		switch (pos) {
			case INTERNAL:
				if(storageSlot == null){
					if(container.getModule() instanceof IModuleCasing){
						return true;
					}
				}else{
					if(!storageSlot.getHasStack()){
						return false;
					}
					if(container.getModule().getValidPositions(container) == EnumModulePositions.CASING){
						return true;
					}
				}
				break;
			default:
				if(storageSlot == null){
					if(container.getModule() instanceof IModuleModuleStorage){
						if(((IModuleModuleStorage)container.getModule()).isValidForPosition(pos, container)){
							return true;
						}
					}
				}else{
					if(!storageSlot.getHasStack()){
						return false;
					}
					EnumModulePositions modulePosition = container.getModule().getValidPositions(container);
					if(!(modulePosition == EnumModulePositions.SIDE && (pos == EnumStoragePositions.LEFT || pos == EnumStoragePositions.RIGHT) || modulePosition == EnumModulePositions.BACK && pos == EnumStoragePositions.BACK || modulePosition == EnumModulePositions.TOP && pos == EnumStoragePositions.TOP)){
						return false;
					}
					EnumModuleSizes usedSize = null;
					for(int i = pos.startSlotIndex + 1;i < pos.endSlotIndex + 1;i++){
						IModuleContainer otherContainer = ModularMachinesApi.getContainerFromItem(itemHandler.getStackInSlot(i));
						if(otherContainer != null){
							usedSize = EnumModuleSizes.getSize(usedSize, otherContainer.getModule().getSize(otherContainer));
						}
					}
					usedSize = EnumModuleSizes.getSize(usedSize, container.getModule().getSize(container));
					if(usedSize != EnumModuleSizes.UNKNOWN){
						return true;
					}
				}
				break;
		}
		return false;*/
	}

	@Override
	public IStorage assemble(IModular modular) throws AssemblerException {
		IStorage storage = super.assemble(modular);
		storage.getModule().setIndex(modular.getNextIndex());
		if(storage instanceof IAddableModuleStorage){
			IAddableModuleStorage moduleStorage = (IAddableModuleStorage) storage;
			for(int index = 0;index < itemHandler.getSlots();index++){
				ItemStack stack = itemHandler.getStackInSlot(index);
				if(stack != null){
					IModuleState state = ModularMachinesApi.loadOrCreateModuleState(modular, stack);
					if(state != null){
						moduleStorage.addModule(stack, state);
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
		if(allowedComplexity == 0){
			return;
		}
		if(allowedComplexity < complexity){
			throw new AssemblerException(I18n.translateToLocalFormatted("modular.assembler.error.complexity.position", position.getLocName().toLowerCase(Locale.ENGLISH)));
		}
	}
}
