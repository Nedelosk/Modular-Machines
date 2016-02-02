package nedelosk.modularmachines.common.producers.machines.assembler.module;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import nedelosk.forestcore.library.gui.IGuiBase;
import nedelosk.forestcore.library.gui.Widget;
import nedelosk.forestcore.library.gui.WidgetProgressBar;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.gui.IModuleGui;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import nedelosk.modularmachines.api.modules.machines.recipe.ModuleMachineRecipe;
import nedelosk.modularmachines.api.recipes.IRecipe;
import nedelosk.modularmachines.api.recipes.NeiStack;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.api.utils.ModuleCategoryUIDs;
import nedelosk.modularmachines.api.utils.ModuleStack;

public class ModuleModuleAssembler extends ModuleMachineRecipe {

	public ModuleModuleAssembler() {
		this("", 75);
	}

	public ModuleModuleAssembler(String moduleModifier, int speedModifier) {
		super(ModuleCategoryUIDs.MACHINE_ASSEMBLER_MODULE, moduleModifier, 9, 2, speedModifier);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<Widget> addNEIWidgets(IGuiBase gui, ModuleStack stack, IRecipe recipe) {
		gui.getWidgetManager().add(new WidgetProgressBar(87, 35, 0, 0));
		return gui.getWidgetManager().getWidgets();
	}

	// NEI
	@SideOnly(Side.CLIENT)
	@Override
	public List<NeiStack> addNEIStacks(ModuleStack stack, IRecipe recipe) {
		ArrayList<NeiStack> list = new ArrayList<NeiStack>();
		list.add(new NeiStack(17, 16, true));
		list.add(new NeiStack(35, 16, true));
		list.add(new NeiStack(53, 16, true));
		list.add(new NeiStack(17, 34, true));
		list.add(new NeiStack(35, 34, true));
		list.add(new NeiStack(53, 34, true));
		list.add(new NeiStack(17, 52, true));
		list.add(new NeiStack(35, 52, true));
		list.add(new NeiStack(53, 52, true));
		list.add(new NeiStack(125, 34, false));
		list.add(new NeiStack(143, 34, false));
		return list;
	}

	// Recipe
	@Override
	public RecipeItem[] getInputs(IModular modular, ModuleStack stack) {
		return getInputItems(modular, stack);
	}

	@Override
	public String getRecipeName(ModuleStack stack) {
		return "AssemblerModule";
	}

	@Override
	public int getColor() {
		return 0x601C93;
	}

	@Override
	public IModuleInventory createInventory(ModuleStack stack) {
		return new ModuleModuleAssemblerInventory(getUID(), itemInputs + itemOutputs);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IModuleGui createGui(ModuleStack stack) {
		return new ModuleModuleAssemblerGui(getUID());
	}
}
