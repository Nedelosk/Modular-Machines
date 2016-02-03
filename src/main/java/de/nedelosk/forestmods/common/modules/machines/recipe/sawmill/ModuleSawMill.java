package de.nedelosk.forestmods.common.modules.machines.recipe.sawmill;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.library.gui.IGuiBase;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.gui.IModuleGui;
import de.nedelosk.forestmods.api.modules.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.recipes.IRecipe;
import de.nedelosk.forestmods.api.recipes.NeiStack;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.modules.machines.recipe.ModuleMachineRecipe;

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

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui createGui(ModuleStack stack) {
		return new ModuleSawMillGui(getUID());
	}
}
