package de.nedelosk.forestmods.common.modules.producers.recipe.lathe;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.forestcore.inventory.IContainerBase;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import de.nedelosk.forestmods.common.modules.producers.recipe.ModuleProducerRecipeInventory;
import net.minecraft.inventory.Slot;

public class ModuleLatheInventory extends ModuleProducerRecipeInventory<ModuleLathe, IModuleProducerRecipeModeSaver> {

	public ModuleLatheInventory(ModuleUID UID, int slots) {
		super(UID, slots);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModularDefault modular, ModuleStack<ModuleLathe, IModuleProducerRecipeModeSaver> stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModularInput(modular.getTile(), 0, 54, 35, stack));
		list.add(new SlotModularOutput(modular.getTile(), 1, 116, 35, stack));
		list.add(new SlotModularOutput(modular.getTile(), 2, 134, 35, stack));
		return list;
	}
}
