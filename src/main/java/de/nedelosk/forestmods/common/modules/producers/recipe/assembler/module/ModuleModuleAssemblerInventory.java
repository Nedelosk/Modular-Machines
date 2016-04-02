package de.nedelosk.forestmods.common.modules.producers.recipe.assembler.module;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.forestcore.inventory.IContainerBase;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import de.nedelosk.forestmods.common.modules.producers.recipe.ModuleProducerRecipeInventory;
import net.minecraft.inventory.Slot;

public class ModuleModuleAssemblerInventory extends ModuleProducerRecipeInventory<ModuleModuleAssembler, IModuleSaver> {

	public ModuleModuleAssemblerInventory(ModuleUID UID, int slots) {
		super(UID, slots);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModularDefault modular, ModuleStack<ModuleModuleAssembler, IModuleSaver> stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModularInput(modular.getTile(), 0, 17, 16, stack));
		list.add(new SlotModularInput(modular.getTile(), 1, 35, 16, stack));
		list.add(new SlotModularInput(modular.getTile(), 2, 53, 16, stack));
		list.add(new SlotModularInput(modular.getTile(), 3, 17, 34, stack));
		list.add(new SlotModularInput(modular.getTile(), 4, 35, 34, stack));
		list.add(new SlotModularInput(modular.getTile(), 5, 53, 34, stack));
		list.add(new SlotModularInput(modular.getTile(), 6, 17, 52, stack));
		list.add(new SlotModularInput(modular.getTile(), 7, 35, 52, stack));
		list.add(new SlotModularInput(modular.getTile(), 8, 53, 52, stack));
		list.add(new SlotModularOutput(modular.getTile(), 9, 125, 34, stack));
		list.add(new SlotModularOutput(modular.getTile(), 10, 143, 34, stack));
		return list;
	}
}
