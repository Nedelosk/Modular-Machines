package nedelosk.modularmachines.common.producers.machines.pulverizer;

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

public class ModulePulverizer extends ModuleMachineRecipe {

	public ModulePulverizer() {
		this(65);
	}

	public ModulePulverizer(int speedModifier) {
		super(ModuleCategoryUIDs.MACHINE_PULVERIZER, 1, 2, speedModifier);
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
	public RecipeItem[] getInputs(IModular modular, ModuleStack stack) {
		return getInputItems(modular, stack);
	}

	@Override
	public String getRecipeName(ModuleStack stack) {
		return "Pulverizer";
	}

	@Override
	public List<Widget> addNEIWidgets(IGuiBase gui, ModuleStack stack, IRecipe recipe) {
		gui.getWidgetManager().add(new WidgetProgressBar(82, 25, 0, 0));
		return gui.getWidgetManager().getWidgets();
	}

	@Override
	public int getColor() {
		return 0x88A7D1;
	}

	@Override
	public IModuleInventory getInventory(ModuleStack stack) {
		return new ModulePulverizerInventory(getCategoryUID(), getModuleUID(), itemInputs + itemOutputs);
	}

	@Override
	public IModuleGui getGui(ModuleStack stack) {
		return new ModulePulverizerGui(getCategoryUID(), getModuleUID());
	}
}
