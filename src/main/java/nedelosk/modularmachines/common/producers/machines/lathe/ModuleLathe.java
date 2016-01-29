package nedelosk.modularmachines.common.producers.machines.lathe;

import java.util.ArrayList;
import java.util.List;

import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.forestcore.library.gui.WidgetProgressBar;
import nedelosk.modularmachines.api.client.widget.WidgetButtonMode;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModuleGui;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.modules.machines.recipe.ModuleMachineRecipeMode;
import nedelosk.modularmachines.api.modules.machines.recipes.RecipeLathe.LatheModes;
import nedelosk.modularmachines.api.recipes.IMachineMode;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.utils.ModuleCategoryUIDs;
import nedelosk.modularmachines.api.utils.ModuleStack;

public class ModuleLathe extends ModuleMachineRecipeMode {

	public ModuleLathe() {
		this("", 60);
	}

	public ModuleLathe(String moduleModifier, int speedModifier) {
		super(ModuleCategoryUIDs.MACHINE_LATHE, moduleModifier, 1, 2, speedModifier, LatheModes.ROD);
	}

	@Override
	public List<Widget> addNEIWidgets(IGuiBase gui, ModuleStack stack, IRecipe recipe) {
		gui.getWidgetManager().add(new WidgetProgressBar(82, 25, 0, 0));
		gui.getWidgetManager().add(new WidgetButtonMode(86, 0, (IMachineMode) recipe.getModifiers()[0]));
		return gui.getWidgetManager().getWidgets();
	}

	// NEI
	@Override
	public List<NeiStack> addNEIStacks(ModuleStack stack, IRecipe recipe) {
		ArrayList<NeiStack> list = new ArrayList<NeiStack>();
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
		return "Lathe";
	}

	@Override
	public int getColor() {
		return 0x49D18B;
	}

	@Override
	public Class<? extends IMachineMode> getModeClass() {
		return LatheModes.class;
	}

	@Override
	public IModuleInventory getInventory(ModuleStack stack) {
		return new ModuleLatheInventory(getCategoryUID(), getName(stack), itemInputs + itemOutputs);
	}

	@Override
	public IModuleGui getGui(ModuleStack stack) {
		return new ModuleLatheGui(getCategoryUID(), getName(stack));
	}
}
