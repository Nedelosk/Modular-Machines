package de.nedelosk.forestmods.common.modules.producers.recipe.assembler;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.forestmods.library.inventory.IContainerBase;
import de.nedelosk.forestmods.library.modules.ModuleUID;
import net.minecraft.inventory.Slot;

public class ModuleAssemblerInventory extends ModuleProducerRecipeInventory<ModuleAssembler, IModuleSaver> {

	public ModuleAssemblerInventory(ModuleUID UID, int slots) {
		super(UID, slots);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModularDefault modular, ModuleStack<ModuleAssembler, IModuleSaver> stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.addAll(new SlotModularInput(modular.getTile(), 0, 36, 35, stack));
		list.addAll(new SlotModularInput(modular.getTile(), 1, 54, 35, stack));
		list.addAll(new SlotModularOutput(modular.getTile(), 2, 116, 35, stack));
		list.addAll(new SlotModularOutput(modular.getTile(), 3, 134, 35, stack));
		return list;
	}
}
