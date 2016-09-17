package de.nedelosk.modularmachines.common.inventory;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.api.modules.storage.IStoragePage;
import de.nedelosk.modularmachines.api.modules.storage.SlotAssemblerStorage;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerAssembler extends BaseContainer<IModularHandler> {

	public ContainerAssembler(IModularHandler tile, InventoryPlayer inventory) {
		super(tile, inventory);
		IModularAssembler assembler = handler.getAssembler();
		IStoragePage page = assembler.getStoragePage(assembler.getSelectedPosition());
		if(page != null){
			page.onSlotChanged(this);
		}
	}

	@Override
	protected void addSlots(InventoryPlayer inventory) {
		IModularAssembler assembler = handler.getAssembler();
		IStoragePosition position = assembler.getSelectedPosition();
		IStoragePage page = assembler.getStoragePage(position);
		if(page == null){
			addSlotToContainer(new SlotAssemblerStorage(assembler.getItemHandler(), assembler.getIndex(position), 44, 35, null, position, this));
		}else{
			List<Slot> slots = new ArrayList<>();
			page.createSlots(this, slots);
			for(Slot slot : slots){
				addSlotToContainer(slot);
			}
		}
	}
}
