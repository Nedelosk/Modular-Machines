package nedelosk.modularmachines.common.producers.machines.sawmill;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.gui.IModuleGui;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.modules.machines.recipe.ModuleMachineRecipe;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.utils.ModuleCategoryUIDs;
import nedelosk.modularmachines.api.utils.ModuleStack;

public class ModuleSawMill extends ModuleMachineRecipe {

	public ModuleSawMill(String moduleModifier, int speedModifier) {
		super(ModuleCategoryUIDs.MACHINE_SAW_MILL, moduleModifier, 1, 2, speedModifier);
	}

	public ModuleSawMill() {
		this("", 60);
	}

	@Override
	public List<NeiStack> addNEIStacks(ModuleStack stack, IRecipe recipe) {
		ArrayList<NeiStack> list = new ArrayList<NeiStack>();
		list.add(new NeiStack(56, 24, true));
		list.add(new NeiStack(116, 24, false));
		list.add(new NeiStack(134, 24, false));
		return list;
	}

	@Override
	public List addNEIWidgets(IGuiBase gui, ModuleStack stack, IRecipe recipe) {
		return null;
	}

	@Override
	public RecipeItem[] getInputs(IModular modular, ModuleStack stack) {
		return getInputItems(modular, stack);
	}

	@Override
	public String getRecipeName(ModuleStack stack) {
		return "SawMill";
	}

	@Override
	public int getColor() {
		return 0xA65005;
	}

	@Override
	public IModuleInventory createInventory(ModuleStack stack) {
		return new ModuleSawMillInventory(getUID(), itemInputs + itemOutputs);
	}

	@Override
	public IModuleGui createGui(ModuleStack stack) {
		return new ModuleSawMillGui(getUID());
	}
}
