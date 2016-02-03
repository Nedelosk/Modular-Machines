package de.nedelosk.forestmods.common.modules.machines.generator;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.forestcore.inventory.IContainerBase;
import de.nedelosk.forestmods.api.modular.basic.IModularDefault;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.inventory.slots.SlotModular;
import de.nedelosk.forestmods.common.inventory.slots.SlotModularOutput;
import de.nedelosk.forestmods.common.modules.machines.recipe.ModuleMachineRecipeInventory;
import de.nedelosk.forestmods.common.modules.machines.recipe.assembler.ModuleAssembler;
import net.minecraft.inventory.Slot;

public class ModuleAssemblerInventory extends ModuleMachineRecipeInventory<ModuleAssembler> {

	public ModuleAssemblerInventory(String categoryUID, String moduleUID, int slots) {
		super(categoryUID, moduleUID, slots);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModularDefault modular, ModuleStack<ModuleAssembler> stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModular(modular.getMachine(), 0, 36, 35, stack));
		list.add(new SlotModular(modular.getMachine(), 1, 54, 35, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 2, 116, 35, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 3, 134, 35, stack));
		return list;
	}
}
