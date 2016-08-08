package de.nedelosk.modularmachines.common.inventory;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modular.IAssemblerLogic;
import de.nedelosk.modularmachines.api.modular.IPositionedModular;
import de.nedelosk.modularmachines.api.modular.IPositionedModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.common.inventory.slots.SlotAssembler;
import de.nedelosk.modularmachines.common.inventory.slots.SlotAssemblerStorage;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;

public class ContainerPositionedAssembler extends ContainerBase<IModularHandler<IPositionedModular, IPositionedModularAssembler, NBTTagCompound>> {

	public ContainerPositionedAssembler(IModularHandler<IPositionedModular, IPositionedModularAssembler, NBTTagCompound> tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		IPositionedModularAssembler assembler = handler.getAssembler();
		EnumPosition pos = assembler.getSelectedPosition();
		IItemHandler itemHandler = assembler.getAssemblerHandler();
		if(pos != null){
			int startSlotIndex = pos.startSlotIndex;
			SlotAssemblerStorage storageSlot;
			IAssemblerLogic logic = assembler.getLogic(pos);
			addSlotToContainer(storageSlot = new SlotAssemblerStorage(itemHandler, startSlotIndex + 0, 44, 35, logic));
			if(pos == EnumPosition.INTERNAL){
				for (int i = 0; i < 3; ++i){
					for (int j = 0; j < 3; ++j){
						this.addSlotToContainer(new SlotAssembler(itemHandler, startSlotIndex + 1 + j + i * 3, 80 + j * 18, 17 + i * 18, logic, this, storageSlot));
					}
				}
			}else{
				addSlotToContainer(new SlotAssembler(itemHandler, startSlotIndex + 1, 98, 17, logic, this, storageSlot));
				addSlotToContainer(new SlotAssembler(itemHandler, startSlotIndex + 2, 98, 35, logic, this, storageSlot));
				addSlotToContainer(new SlotAssembler(itemHandler, startSlotIndex + 3, 98, 53, logic, this, storageSlot));
			}
		}
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		IPositionedModularAssembler assembler = handler.getAssembler();
		EnumPosition pos = assembler.getSelectedPosition();
		if(pos != EnumPosition.INTERNAL){
			Slot slot = inventorySlots.get(37);
			SlotAssembler slotFirst = (SlotAssembler) inventorySlots.get(37);
			SlotAssembler slotSecond = (SlotAssembler) inventorySlots.get(38);
			SlotAssembler slotLast = (SlotAssembler) inventorySlots.get(39);
			slotFirst.hasChange = false;
			slotSecond.hasChange = false;
			slotLast.hasChange = false;
			if(slotFirst.getHasStack()){
				IModuleContainer containerFirst = ModularMachinesApi.getContainerFromItem(slotFirst.getStack());
				if(containerFirst.getModule().getSize() == EnumModuleSize.LARGE){
					slotSecond.setActive(false);
					slotLast.setActive(false);
				}else if(containerFirst.getModule().getSize() == EnumModuleSize.MIDDLE){
					if(!slotSecond.getHasStack()){
						slotSecond.setActive(false);
					}else{
						slotLast.setActive(false);
					}
				}
			}
			if(slotSecond.getHasStack()){
				IModuleContainer containerSecond = ModularMachinesApi.getContainerFromItem(slotSecond.getStack());
				if(containerSecond.getModule().getSize() == EnumModuleSize.LARGE){
					slotFirst.setActive(false);
					slotLast.setActive(false);
				}else if(containerSecond.getModule().getSize() == EnumModuleSize.MIDDLE){
					if(!slotFirst.getHasStack()){
						slotFirst.setActive(false);
					}else{
						slotLast.setActive(false);
					}
				}
			}
			if(slotLast.getHasStack()){
				IModuleContainer containerLast = ModularMachinesApi.getContainerFromItem(slotLast.getStack());
				if(containerLast.getModule().getSize() == EnumModuleSize.LARGE){
					slotFirst.setActive(false);
					slotSecond.setActive(false);
				}else if(containerLast.getModule().getSize() == EnumModuleSize.MIDDLE){
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
		}
		super.onCraftMatrixChanged(inventory);
	}
}
