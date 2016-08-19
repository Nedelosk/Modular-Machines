package de.nedelosk.modularmachines.common.inventory;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modular.IAssemblerLogic;
import de.nedelosk.modularmachines.api.modular.ISimpleModular;
import de.nedelosk.modularmachines.api.modular.ISimpleModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.client.gui.GuiAssembler;
import de.nedelosk.modularmachines.common.inventory.slots.SlotAssembler;
import de.nedelosk.modularmachines.common.inventory.slots.SlotAssemblerStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;

public class ContainerSimpleAssembler extends ContainerBase<IModularHandler<ISimpleModular, ISimpleModularAssembler, NBTTagCompound>> {

	public ContainerSimpleAssembler(IModularHandler<ISimpleModular, ISimpleModularAssembler, NBTTagCompound> tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		ISimpleModularAssembler assembler = handler.getAssembler();
		IItemHandler itemHandler = assembler.getAssemblerHandler();
		IAssemblerLogic logic = assembler.getLogic();
		SlotAssemblerStorage storageSlot;
		addSlotToContainer(storageSlot = new SlotAssemblerStorage(itemHandler, 0, 44, 35, this, logic));
		addSlotToContainer(new SlotAssembler(itemHandler, 1, 98, 17, logic, this, storageSlot));
		addSlotToContainer(new SlotAssembler(itemHandler, 2, 98, 35, logic, this, storageSlot));
		addSlotToContainer(new SlotAssembler(itemHandler, 3, 98, 53, logic, this, storageSlot));
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory) {
		if(handler.getWorld().isRemote){
			if(Minecraft.getMinecraft().currentScreen instanceof GuiAssembler){
				GuiAssembler gui = (GuiAssembler) Minecraft.getMinecraft().currentScreen;
				gui.hasChange = true;
			}
		}

		ISimpleModularAssembler assembler = handler.getAssembler();
		SlotAssembler slotFirst = (SlotAssembler) inventorySlots.get(37);
		SlotAssembler slotSecond = (SlotAssembler) inventorySlots.get(38);
		SlotAssembler slotLast = (SlotAssembler) inventorySlots.get(39);
		slotFirst.hasChange = false;
		slotSecond.hasChange = false;
		slotLast.hasChange = false;
		if(slotFirst.getHasStack()){
			IModuleContainer containerFirst = ModularMachinesApi.getContainerFromItem(slotFirst.getStack());
			if(containerFirst.getModule().getSize(containerFirst) == EnumModuleSize.LARGE){
				slotSecond.setActive(false);
				slotLast.setActive(false);
			}else if(containerFirst.getModule().getSize(containerFirst) == EnumModuleSize.MIDDLE){
				if(!slotSecond.getHasStack()){
					slotSecond.setActive(false);
				}else{
					slotLast.setActive(false);
				}
			}
		}
		if(slotSecond.getHasStack()){
			IModuleContainer containerSecond = ModularMachinesApi.getContainerFromItem(slotSecond.getStack());
			if(containerSecond.getModule().getSize(containerSecond) == EnumModuleSize.LARGE){
				slotFirst.setActive(false);
				slotLast.setActive(false);
			}else if(containerSecond.getModule().getSize(containerSecond) == EnumModuleSize.MIDDLE){
				if(!slotFirst.getHasStack()){
					slotFirst.setActive(false);
				}else{
					slotLast.setActive(false);
				}
			}
		}
		if(slotLast.getHasStack()){
			IModuleContainer containerLast = ModularMachinesApi.getContainerFromItem(slotLast.getStack());
			if(containerLast.getModule().getSize(containerLast) == EnumModuleSize.LARGE){
				slotFirst.setActive(false);
				slotSecond.setActive(false);
			}else if(containerLast.getModule().getSize(containerLast) == EnumModuleSize.MIDDLE){
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
		super.onCraftMatrixChanged(inventory);
	}
}
