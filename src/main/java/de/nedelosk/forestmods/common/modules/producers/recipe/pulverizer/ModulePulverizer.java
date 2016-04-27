package de.nedelosk.forestmods.common.modules.producers.recipe.pulverizer;

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

public class ModulePulverizer extends ModuleProducerRecipe {

	public ModulePulverizer() {
		super(ModuleUIDs.MACHINE_PULVERIZER, 1, 2);
	}

	@Override
	public List<NeiSlot> addNEIStacks(ModuleStack stack, IRecipe recipe) {
		ArrayList<NeiSlot> list = new ArrayList<NeiSlot>();
		list.addAll(new NeiSlot(56, 24, true));
		list.addAll(new NeiSlot(116, 24, false));
		list.addAll(new NeiSlot(134, 24, false));
		return list;
	}

	@Override
	public RecipeItem[] getInputs(IModular modular, ModuleStack stack) {
		return getInputItems(modular, stack);
	}

	@Override
	public String getRecipeCategory(ModuleStack stack) {
		return "Pulverizer";
	}

	@Override
	public Class<? extends IRecipe> getRecipeClass() {
		return RecipePulverizer.class;
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
	public IModuleInventory createInventory(ModuleStack stack) {
		return new ModulePulverizerInventory(getUID(), itemInputs + itemOutputs);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui createGui(ModuleStack stack) {
		return new ModulePulverizerGui(getUID());
	}
}
