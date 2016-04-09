package de.nedelosk.forestmods.common.modules.producers.recipe.pulverizer;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.forestcore.inventory.IContainerBase;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import net.minecraft.inventory.Slot;

public class ModulePulverizerInventory extends ModuleProducerRecipeInventory<ModulePulverizer, IModuleSaver> {

	public ModulePulverizerInventory(ModuleUID UID, int slots) {
		super(UID, slots);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModularDefault modular, ModuleStack<ModulePulverizer, IModuleSaver> stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.addAll(new SlotModularInput(modular.getTile(), 0, 56, 35, stack));
		list.addAll(new SlotModularOutput(modular.getTile(), 1, 116, 35, stack));
		list.addAll(new SlotModularOutput(modular.getTile(), 2, 134, 35, stack));
		return list;
	}
}
