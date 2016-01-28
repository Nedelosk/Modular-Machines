package nedelosk.modularmachines.common.producers.machines.alloysmelter;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.forestcore.library.gui.WidgetProgressBar;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModuleGui;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.modules.machines.recipe.ModuleMachineRecipe;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.utils.ModuleCategoryUIDs;
import nedelosk.modularmachines.api.utils.ModuleStack;

public class ModuleAlloySmelter extends ModuleMachineRecipe {

	public ModuleAlloySmelter() {
		this(60);
	}

	public ModuleAlloySmelter(int speedModifier) {
		super(ModuleCategoryUIDs.MACHINE_ALLOY_SMELTER, 2, 2, speedModifier);
	}

	@Override
	public List<Widget> addNEIWidgets(IGuiBase gui, ModuleStack stack, IRecipe recipe) {
		gui.getWidgetManager().add(new WidgetProgressBar(82, 25, 0, 0));
		return gui.getWidgetManager().getWidgets();
	}

	// NEI
	@Override
	public List<NeiStack> addNEIStacks(ModuleStack stack, IRecipe recipe) {
		ArrayList<NeiStack> list = new ArrayList<NeiStack>();
		list.add(new NeiStack(36, 24, true));
		list.add(new NeiStack(54, 24, true));
		list.add(new NeiStack(116, 24, false));
		list.add(new NeiStack(134, 24, false));
		return list;
	}

	// Recipe
	@Override
	public RecipeItem[] getInputs(IModular modular, ModuleStack stack) {
		return getInputItems(modular, stack);
	}

	@Override
	public String getRecipeName(ModuleStack stack) {
		return "AlloySmelter";
	}

	@Override
	public int getColor() {
		return 0xB22222;
	}

	@Override
	public IModuleGui getGui(ModuleStack stack) {
		return new ModuleAlloySmelterGui(getCategoryUID(), getModuleUID());
	}

	@Override
	public IModuleInventory getInventory(ModuleStack stack) {
		return new ModuleAlloySmelterInventory(getCategoryUID(), getModuleUID(), itemInputs + itemOutputs);
	}
}
