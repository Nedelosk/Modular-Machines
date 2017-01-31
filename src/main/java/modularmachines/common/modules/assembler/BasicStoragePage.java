package modularmachines.common.modules.assembler;

import java.util.List;

import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.assemblers.SlotAssembler;
import modularmachines.api.modules.assemblers.SlotAssemblerStorage;
import modularmachines.api.modules.assemblers.StoragePage;
import modularmachines.api.modules.storages.IStoragePosition;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class BasicStoragePage extends StoragePage {

	public BasicStoragePage(IAssembler assembler, IStoragePosition position, int size) {
		super(assembler, position, size);
	}

	public BasicStoragePage(IAssembler assembler, IStoragePosition position) {
		super(assembler, position);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack, SlotAssembler slot, SlotAssemblerStorage storageSlot) {
		return false;
	}
	
	@Override
	public void createSlots(Container container, IAssembler assembler, List<Slot> slots) {
		if (position != null) {
			slots.add(new SlotAssemblerStorage(assembler, container, 44, 35, this));
		}
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
}
