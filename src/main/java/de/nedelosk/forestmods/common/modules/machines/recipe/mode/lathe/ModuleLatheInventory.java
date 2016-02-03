package de.nedelosk.forestmods.common.modules.machines.recipe.mode.lathe;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.forestcore.inventory.IContainerBase;
import de.nedelosk.forestmods.api.modular.basic.IModularDefault;
import de.nedelosk.forestmods.api.modules.machines.recipe.IModuleMachineRecipeModeSaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.inventory.slots.SlotModularInput;
import de.nedelosk.forestmods.common.inventory.slots.SlotModularOutput;
import de.nedelosk.forestmods.common.modules.machines.recipe.ModuleMachineRecipeInventory;
import net.minecraft.inventory.Slot;

public class ModuleLatheInventory extends ModuleMachineRecipeInventory<ModuleLathe, IModuleMachineRecipeModeSaver> {

	public ModuleLatheInventory(String UID, int slots) {
		super(UID, slots);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModularDefault modular, ModuleStack<ModuleLathe, IModuleMachineRecipeModeSaver> stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModularInput(modular.getMachine(), 0, 54, 35, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 1, 116, 35, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 2, 134, 35, stack));
		return list;
	}
}
