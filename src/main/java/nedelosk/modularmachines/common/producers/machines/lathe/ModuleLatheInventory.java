package nedelosk.modularmachines.common.producers.machines.lathe;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestcore.library.inventory.IContainerBase;
import nedelosk.modularmachines.api.inventory.slots.SlotModularInput;
import nedelosk.modularmachines.api.inventory.slots.SlotModularOutput;
import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modules.machines.recipe.IModuleMachineRecipeModeSaver;
import nedelosk.modularmachines.api.modules.machines.recipe.ModuleMachineRecipeInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.inventory.Slot;

public class ModuleLatheInventory extends ModuleMachineRecipeInventory<ModuleLathe, IModuleMachineRecipeModeSaver> {

	public ModuleLatheInventory(String UID, int slots) {
		super(UID, slots);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModularInventory modular, ModuleStack<ModuleLathe, IModuleMachineRecipeModeSaver> stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModularInput(modular.getMachine(), 0, 54, 35, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 1, 116, 35, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 2, 134, 35, stack));
		return list;
	}
}
