package de.nedelosk.forestmods.common.modules.producers.recipe.assembler.module;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.inventory.IContainerBase;
import net.minecraft.inventory.Slot;

public class ModuleModuleAssemblerInventory extends ModuleProducerRecipeInventory<ModuleModuleAssembler, IModuleSaver> {

	public ModuleModuleAssemblerInventory(ModuleUID UID, int slots) {
		super(UID, slots);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModularDefault modular, ModuleStack<ModuleModuleAssembler, IModuleSaver> stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.addAll(new SlotModularInput(modular.getHandler(), 0, 17, 16, stack));
		list.addAll(new SlotModularInput(modular.getHandler(), 1, 35, 16, stack));
		list.addAll(new SlotModularInput(modular.getHandler(), 2, 53, 16, stack));
		list.addAll(new SlotModularInput(modular.getHandler(), 3, 17, 34, stack));
		list.addAll(new SlotModularInput(modular.getHandler(), 4, 35, 34, stack));
		list.addAll(new SlotModularInput(modular.getHandler(), 5, 53, 34, stack));
		list.addAll(new SlotModularInput(modular.getHandler(), 6, 17, 52, stack));
		list.addAll(new SlotModularInput(modular.getHandler(), 7, 35, 52, stack));
		list.addAll(new SlotModularInput(modular.getHandler(), 8, 53, 52, stack));
		list.addAll(new SlotModularOutput(modular.getHandler(), 9, 125, 34, stack));
		list.addAll(new SlotModularOutput(modular.getHandler(), 10, 143, 34, stack));
		return list;
	}
}
