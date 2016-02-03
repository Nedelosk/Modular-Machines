package de.nedelosk.forestmods.common.modules.machines.recipe.assembler.module;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.forestcore.inventory.IContainerBase;
import de.nedelosk.forestmods.api.modular.basic.IModularDefault;
import de.nedelosk.forestmods.api.modules.machines.IModuleMachineSaver;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.inventory.slots.SlotModularInput;
import de.nedelosk.forestmods.common.inventory.slots.SlotModularOutput;
import de.nedelosk.forestmods.common.modules.machines.recipe.ModuleMachineRecipeInventory;
import net.minecraft.inventory.Slot;

public class ModuleModuleAssemblerInventory extends ModuleMachineRecipeInventory<ModuleModuleAssembler, IModuleMachineSaver> {

	public ModuleModuleAssemblerInventory(String UID, int slots) {
		super(UID, slots);
	}

	@Override
	public List<Slot> addSlots(IContainerBase container, IModularDefault modular, ModuleStack<ModuleModuleAssembler, IModuleMachineSaver> stack) {
		ArrayList<Slot> list = new ArrayList<Slot>();
		list.add(new SlotModularInput(modular.getMachine(), 0, 17, 16, stack));
		list.add(new SlotModularInput(modular.getMachine(), 1, 35, 16, stack));
		list.add(new SlotModularInput(modular.getMachine(), 2, 53, 16, stack));
		list.add(new SlotModularInput(modular.getMachine(), 3, 17, 34, stack));
		list.add(new SlotModularInput(modular.getMachine(), 4, 35, 34, stack));
		list.add(new SlotModularInput(modular.getMachine(), 5, 53, 34, stack));
		list.add(new SlotModularInput(modular.getMachine(), 6, 17, 52, stack));
		list.add(new SlotModularInput(modular.getMachine(), 7, 35, 52, stack));
		list.add(new SlotModularInput(modular.getMachine(), 8, 53, 52, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 9, 125, 34, stack));
		list.add(new SlotModularOutput(modular.getMachine(), 10, 143, 34, stack));
		return list;
	}
}
