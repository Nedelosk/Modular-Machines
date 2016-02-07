package de.nedelosk.forestmods.common.modules.machines.recipe.centrifuge;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.forestcore.inventory.IContainerBase;
import de.nedelosk.forestmods.api.modular.basic.IModularDefault;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.inventory.slots.SlotModularInput;
import de.nedelosk.forestmods.common.inventory.slots.SlotModularOutput;
import de.nedelosk.forestmods.common.modules.machines.recipe.ModuleMachineRecipeInventory;
import net.minecraft.inventory.Slot;

public class ModuleCentrifugeInventory extends ModuleMachineRecipeInventory<ModuleCentrifuge, IModuleSaver> {

	public ModuleCentrifugeInventory(String UID, int slots) {
		super(UID, slots);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModularDefault modular, ModuleStack<ModuleCentrifuge, IModuleSaver> stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModularInput(modular.getMachine(), 0, 56, 35, stack));
		list.add(new SlotModularInput(modular.getMachine(), 1, 74, 35, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 2, 116, 35, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 3, 134, 35, stack));
		return list;
	}
}
