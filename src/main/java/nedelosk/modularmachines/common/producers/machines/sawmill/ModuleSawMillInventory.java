package nedelosk.modularmachines.common.producers.machines.sawmill;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestcore.library.inventory.IContainerBase;
import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.inventory.SlotModular;
import nedelosk.modularmachines.api.modules.machines.recipe.ModuleMachineRecipeInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.inventory.Slot;

public class ModuleSawMillInventory extends ModuleMachineRecipeInventory<ModuleSawMill> {

	public ModuleSawMillInventory(String categoryUID, String moduleUID, int slots) {
		super(categoryUID, moduleUID, slots);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModularInventory modular, ModuleStack<ModuleSawMill> stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModular(modular.getMachine(), 0, 56, 35, stack));
		list.add(new SlotModular(modular.getMachine(), 1, 116, 35, stack));
		list.add(new SlotModular(modular.getMachine(), 2, 134, 35, stack));
		return list;
	}
}
