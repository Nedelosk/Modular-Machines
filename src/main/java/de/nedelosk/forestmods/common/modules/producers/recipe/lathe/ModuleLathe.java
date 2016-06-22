package de.nedelosk.forestmods.common.modules.producers.recipe.lathe;

import java.util.ArrayList;
import java.util.List;

import de.nedelosk.modularmachines.api.gui.IGuiBase;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.recipes.RecipeLathe;
import de.nedelosk.modularmachines.api.modules.recipes.RecipeLathe.LatheModes;
import de.nedelosk.modularmachines.api.recipes.IMachineMode;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.client.gui.Widget;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetButtonMode;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetProgressBar;

public class ModuleLathe extends ModuleMachineRecipeMode {

	public ModuleLathe() {
		super(ModuleUIDs.MACHINE_LATHE, 1, 2, LatheModes.ROD);
	}

	@Override
	public List<Widget> addNEIWidgets(IGuiBase gui, ModuleStack stack, IRecipe recipe) {
		gui.getWidgetManager().add(new WidgetProgressBar(82, 25, 0, 0));
		gui.getWidgetManager().add(new WidgetButtonMode(86, 0, (IMachineMode) recipe.getModifiers()[0]));
		return gui.getWidgetManager().getWidgets();
	}

	// NEI
	@Override
	public List<NeiSlot> addNEIStacks(ModuleStack stack, IRecipe recipe) {
		ArrayList<NeiSlot> list = new ArrayList<NeiSlot>();
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
		return "Lathe";
	}

	@Override
	public Class<? extends IRecipe> getRecipeClass() {
		return RecipeLathe.class;
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
	public IModuleInventory createInventory(ModuleStack stack) {
		return new ModuleLatheInventory(getUID(), itemInputs + itemOutputs);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui createGui(ModuleStack stack) {
		return new ModuleLatheGui(getUID());
	}
}