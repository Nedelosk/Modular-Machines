package nedelosk.modularmachines.common.producers.machines.energyinfuser;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestcore.library.inventory.IContainerBase;
import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.inventory.SlotModular;
import nedelosk.modularmachines.api.modular.inventory.SlotModularOutput;
import nedelosk.modularmachines.api.modules.machines.recipe.ModuleMachineRecipeInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;
import nedelosk.modularmachines.common.producers.machines.assembler.ModuleAssembler;
import net.minecraft.inventory.Slot;

public class ModuleAssemblerInventory extends ModuleMachineRecipeInventory<ModuleAssembler> {

	public ModuleAssemblerInventory(String categoryUID, String moduleUID, int slots) {
		super(categoryUID, moduleUID, slots);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModularInventory modular, ModuleStack<ModuleAssembler> stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModular(modular.getMachine(), 0, 36, 35, stack));
		list.add(new SlotModular(modular.getMachine(), 1, 54, 35, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 2, 116, 35, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 3, 134, 35, stack));
		return list;
	}
}
