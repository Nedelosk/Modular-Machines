package de.nedelosk.forestmods.common.modules.machines.recipe.alloysmelter;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.library.gui.IGuiBase;
import de.nedelosk.forestcore.library.gui.Widget;
import de.nedelosk.forestcore.library.gui.WidgetProgressBar;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.gui.IModuleGui;
import de.nedelosk.forestmods.api.modules.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.modules.machines.recipes.RecipeAlloySmelter;
import de.nedelosk.forestmods.api.recipes.IRecipe;
import de.nedelosk.forestmods.api.recipes.NeiStack;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.machines.recipe.ModuleMachineRecipe;

public class ModuleAlloySmelter extends ModuleMachineRecipe {

	public ModuleAlloySmelter() {
		this("", 60);
	}

	public ModuleAlloySmelter(String moduleModifier, int speedModifier) {
		super(ModuleCategoryUIDs.MACHINE_ALLOY_SMELTER, moduleModifier, 2, 2, speedModifier);
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
	public String getRecipeCategory(ModuleStack stack) {
		return "AlloySmelter";
	}

	@Override
	public Class<? extends IRecipe> getRecipeClass() {
		return RecipeAlloySmelter.class;
	}

	@Override
	public int getColor() {
		return 0xB22222;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui createGui(ModuleStack stack) {
		return new ModuleAlloySmelterGui(getUID());
	}

	@Override
	public IModuleInventory createInventory(ModuleStack stack) {
		return new ModuleAlloySmelterInventory(getUID(), itemInputs + itemOutputs);
	}
}
