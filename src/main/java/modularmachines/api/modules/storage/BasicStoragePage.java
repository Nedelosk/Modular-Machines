package modularmachines.api.modules.storage;

import java.util.List;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import modularmachines.api.modular.AssemblerException;
import modularmachines.api.modular.IModular;
import modularmachines.api.modular.IModularAssembler;
import modularmachines.api.modular.assembler.IAssemblerContainer;
import modularmachines.api.modular.assembler.SlotAssembler;
import modularmachines.api.modular.assembler.SlotAssemblerStorage;
import modularmachines.api.modules.position.IStoragePosition;

public class BasicStoragePage extends StoragePage {

	public BasicStoragePage(IModularAssembler assembler, IStorage storage) {
		super(assembler, storage);
	}

	public BasicStoragePage(IModularAssembler assembler, IItemHandlerStorage itemHandler, IStoragePosition position) {
		super(assembler, itemHandler, position);
	}

	public BasicStoragePage(IModularAssembler assembler, IStoragePosition position) {
		super(assembler, position);
	}

	@Override
	public boolean isItemValid(ItemStack stack, SlotAssembler slot, SlotAssemblerStorage storageSlot) {
		return false;
	}

	@Override
	public void createSlots(IAssemblerContainer container, List<Slot> slots) {
		if (position != null) {
			slots.add(new SlotAssemblerStorage(assembler, 44, 35, this, position, container));
		}
	}

	@Override
	public void onSlotChanged(IAssemblerContainer container) {
	}

	@Override
	public void canAssemble(IModular modular) throws AssemblerException {
	}
}
