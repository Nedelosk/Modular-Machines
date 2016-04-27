package de.nedelosk.forestmods.common.modules.producers.recipe.centrifuge;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.library.gui.IGuiBase;
import de.nedelosk.forestmods.library.modular.IModular;
import de.nedelosk.forestmods.library.modules.ModuleUIDs;
import de.nedelosk.forestmods.library.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.forestmods.library.modules.recipes.RecipeCentrifuge;
import de.nedelosk.forestmods.library.recipes.IRecipe;
import de.nedelosk.forestmods.library.recipes.RecipeItem;

public class ModuleCentrifuge extends ModuleProducerRecipe {

	public ModuleCentrifuge() {
		super(ModuleUIDs.MACHINE_CENTRIFUGE, 2, 2);
	}

	@Override
	public List<NeiSlot> addNEIStacks(ModuleStack stack, IRecipe recipe) {
		ArrayList<NeiSlot> list = new ArrayList<NeiSlot>();
		list.addAll(new NeiSlot(56, 24, true));
		list.addAll(new NeiSlot(74, 24, true));
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
		return "Centrifuge";
	}

	@Override
	public Class<? extends IRecipe> getRecipeClass() {
		return RecipeCentrifuge.class;
	}

	@Override
	public int getColor() {
		return 0xABA8A8;
	}

	@Override
	public IModuleInventory createInventory(ModuleStack stack) {
		return new ModuleCentrifugeInventory(getUID(), itemInputs + itemOutputs);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui createGui(ModuleStack stack) {
		return new ModuleCentrifugeGui(getUID());
	}

	@Override
	public List addNEIWidgets(IGuiBase gui, ModuleStack stack, IRecipe recipe) {
		return null;
	}
}
