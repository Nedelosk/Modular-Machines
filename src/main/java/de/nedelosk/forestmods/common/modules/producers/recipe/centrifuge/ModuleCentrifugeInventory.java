package de.nedelosk.forestmods.common.modules.producers.recipe.centrifuge;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.forestmods.library.inventory.IContainerBase;
import de.nedelosk.forestmods.library.modules.ModuleUID;
import net.minecraft.inventory.Slot;

public class ModuleCentrifugeInventory extends ModuleProducerRecipeInventory<ModuleCentrifuge, IModuleSaver> {

	public ModuleCentrifugeInventory(ModuleUID UID, int slots) {
		super(UID, slots);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModularDefault modular, ModuleStack<ModuleCentrifuge, IModuleSaver> stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.addAll(new SlotModularInput(modular.getTile(), 0, 56, 35, stack));
		list.addAll(new SlotModularInput(modular.getTile(), 1, 74, 35, stack));
		list.addAll(new SlotModularOutput(modular.getTile(), 2, 116, 35, stack));
		list.addAll(new SlotModularOutput(modular.getTile(), 3, 134, 35, stack));
		return list;
	}
}
