package de.nedelosk.forestmods.common.modules.producers.recipe.centrifuge;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.forestcore.inventory.IContainerBase;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import net.minecraft.inventory.Slot;

public class ModuleCentrifugeInventory extends ModuleProducerRecipeInventory<ModuleCentrifuge, IModuleSaver> {

	public ModuleCentrifugeInventory(ModuleUID UID, int slots) {
		super(UID, slots);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModularDefault modular, ModuleStack<ModuleCentrifuge, IModuleSaver> stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModularInput(modular.getTile(), 0, 56, 35, stack));
		list.add(new SlotModularInput(modular.getTile(), 1, 74, 35, stack));
		list.add(new SlotModularOutput(modular.getTile(), 2, 116, 35, stack));
		list.add(new SlotModularOutput(modular.getTile(), 3, 134, 35, stack));
		return list;
	}
}
