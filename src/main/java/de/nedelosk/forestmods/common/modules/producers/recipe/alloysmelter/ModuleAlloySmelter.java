package de.nedelosk.forestmods.common.modules.producers.recipe.alloysmelter;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.client.gui.widgets.WidgetProgressBar;
import de.nedelosk.forestmods.library.gui.IGuiBase;
import de.nedelosk.forestmods.library.gui.Widget;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modules.ModuleUIDs;
import de.nedelosk.forestmods.library.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.forestmods.library.recipes.IRecipe;
import de.nedelosk.forestmods.library.recipes.RecipeItem;

public class ModuleAlloySmelter extends ModuleProducerRecipe {

	public ModuleAlloySmelter() {
		super(ModuleUIDs.MACHINE_ALLOY_SMELTER, 2, 2);
	}

	@Override
	public List<Widget> addNEIWidgets(IGuiBase gui, ModuleStack stack, IRecipe recipe) {
		gui.getWidgetManager().add(new WidgetProgressBar(82, 25, 0, 0));
		return gui.getWidgetManager().getWidgets();
	}

	// NEI
	@Override
	public List<NeiSlot> addNEIStacks(ModuleStack stack, IRecipe recipe) {
		ArrayList<NeiSlot> list = new ArrayList<NeiSlot>();
		list.addAll(new NeiSlot(36, 24, true));
		list.addAll(new NeiSlot(54, 24, true));
		list.addAll(new NeiSlot(116, 24, false));
		list.addAll(new NeiSlot(134, 24, false));
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
