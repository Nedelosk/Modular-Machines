package nedelosk.modularmachines.common.producers.machines.assembler;

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

public class ModuleAssembler extends ModuleMachineRecipe {

	public ModuleAssembler() {
		this(75);
	}

	public ModuleAssembler(int speedModifier) {
		super(ModuleCategoryUIDs.MACHINE_ASSEMBLER, 2, 2, speedModifier);
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
		return "Assembler";
	}

	@Override
	public int getColor() {
		return 0x601C93;
	}

	@Override
	public IModuleInventory getInventory(ModuleStack stack) {
		return new ModuleAssemblerInventory(getCategoryUID(), getModuleUID(), itemInputs + itemOutputs);
	}

	@Override
	public IModuleGui getGui(ModuleStack stack) {
		return new ModuleAssemblerGui(getCategoryUID(), getModuleUID());
	}
}
