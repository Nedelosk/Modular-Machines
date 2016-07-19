package de.nedelosk.modularmachines.common.inventory;

import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.storaged.EnumPosition;
import de.nedelosk.modularmachines.common.inventory.slots.SlotAssembler;
import de.nedelosk.modularmachines.common.inventory.slots.SlotAssemblerStorage;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.IItemHandler;

public class ContainerAssembler extends ContainerBase<IModularHandler> {

	public ContainerAssembler(IModularHandler tile, InventoryPlayer inventory) {
		super(tile, inventory);
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		IModularAssembler assembler = handler.getAssembler();
		EnumPosition pos = assembler.getSelectedPosition();
		IItemHandler itemHandler = assembler.getAssemblerHandler();
		if(pos != null){
			int startSlotIndex = pos.startSlotIndex;
			SlotAssemblerStorage storageSlot;
			addSlotToContainer(storageSlot = new SlotAssemblerStorage(itemHandler, startSlotIndex + 0, 44, 35, assembler));
			if(pos == EnumPosition.INTERNAL){
				for (int i = 0; i < 3; ++i){
					for (int j = 0; j < 3; ++j){
						this.addSlotToContainer(new SlotAssembler(itemHandler, startSlotIndex + 1 + j + i * 3, 80 + j * 18, 17 + i * 18, assembler, storageSlot));
					}
				}
			}else{
				addSlotToContainer(new SlotAssembler(itemHandler, startSlotIndex + 1, 98, 17, assembler, storageSlot));
				addSlotToContainer(new SlotAssembler(itemHandler, startSlotIndex + 2, 98, 35, assembler, storageSlot));
				addSlotToContainer(new SlotAssembler(itemHandler, startSlotIndex + 3, 98, 53, assembler, storageSlot));
			}
		}
	}
}
