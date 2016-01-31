package nedelosk.modularmachines.common.producers.machines.centrifuge;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestcore.library.inventory.IContainerBase;
import nedelosk.modularmachines.api.inventory.slots.SlotModularInput;
import nedelosk.modularmachines.api.inventory.slots.SlotModularOutput;
import nedelosk.modularmachines.api.modular.basic.IModularInventory;
import nedelosk.modularmachines.api.modules.machines.IModuleMachineSaver;
import nedelosk.modularmachines.api.modules.machines.recipe.ModuleMachineRecipeInventory;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.inventory.Slot;

public class ModuleCentrifugeInventory extends ModuleMachineRecipeInventory<ModuleCentrifuge, IModuleMachineSaver> {

	public ModuleCentrifugeInventory(String UID, int slots) {
		super(UID, slots);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModularInventory modular, ModuleStack<ModuleCentrifuge, IModuleMachineSaver> stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModularInput(modular.getMachine(), 0, 56, 35, stack));
		list.add(new SlotModularInput(modular.getMachine(), 1, 74, 35, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 2, 116, 35, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 3, 134, 35, stack));
		return list;
	}
}
