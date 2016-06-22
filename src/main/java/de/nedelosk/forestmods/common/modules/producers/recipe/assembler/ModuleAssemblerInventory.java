package de.nedelosk.forestmods.common.modules.producers.recipe.assembler;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.inventory.IContainerBase;
import net.minecraft.inventory.Slot;

public class ModuleAssemblerInventory extends ModuleProducerRecipeInventory<ModuleAssembler, IModuleSaver> {

	public ModuleAssemblerInventory(ModuleUID UID, int slots) {
		super(UID, slots);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModularDefault modular, ModuleStack<ModuleAssembler, IModuleSaver> stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.addAll(new SlotModularInput(modular.getHandler(), 0, 36, 35, stack));
		list.addAll(new SlotModularInput(modular.getHandler(), 1, 54, 35, stack));
		list.addAll(new SlotModularOutput(modular.getHandler(), 2, 116, 35, stack));
		list.addAll(new SlotModularOutput(modular.getHandler(), 3, 134, 35, stack));
		return list;
	}
}
