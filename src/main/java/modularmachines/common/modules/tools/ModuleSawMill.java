package modularmachines.common.modules.tools;

import java.awt.Color;
import java.util.List;

import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.containers.IModuleColoredItem;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.tools.EnumToolType;
import modularmachines.api.recipes.RecipeItem;
import modularmachines.common.modules.pages.SawMillPage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleSawMill extends ModuleBasicMachine implements IModuleColoredItem {

	public ModuleSawMill() {
		super("sawmill");
	}

	@Override
	public RecipeItem[] getInputs(IModuleState state) {
		return state.getPage(SawMillPage.class).getInventory().getRecipeItems();
	}

	@SideOnly(Side.CLIENT)
	@Override
	protected String getModelFolder(IModuleItemContainer container) {
		return "sawmills";
	}

	@Override
	public String getRecipeCategory(IModuleState state) {
		return "SawMill";
	}

	@Override
	public int getColor(IModuleContainer container) {
		return new Color(102, 51, 0).getRGB();
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new SawMillPage(state));
		return pages;
	}

	/*
	 * @Override public void openJEI(IModuleState state){ if(this instanceof
	 * IModuleJEI){ Loader.instance(); if(Loader.isModLoaded("JEI")){
	 * ModuleJeiPlugin.jeiRuntime.getRecipesGui().showCategories(Arrays.asList((
	 * (IModuleJEI)this).getJEIRecipeCategorys(state.getContainer()))); } } }
	 */
	@Override
	public EnumToolType getType(IModuleState state) {
		return EnumToolType.KINETIC;
	}
}
