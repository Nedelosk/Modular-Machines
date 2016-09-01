package de.nedelosk.modularmachines.common.modules.tools;

import java.awt.Color;
import java.util.List;

import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.items.IModuleColored;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tools.EnumToolType;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.common.modules.pages.SawMillPage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleSawMill extends ModuleBasicMachine implements IModuleColored{

	public ModuleSawMill() {
		super("sawmill");
	}

	@Override
	public RecipeItem[] getInputs(IModuleState state) {
		return ((IModuleInventory)state.getContentHandler(IModuleInventory.class)).getInputItems();
	}

	@SideOnly(Side.CLIENT)
	@Override
	protected String getModelFolder(IModuleContainer container) {
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
		pages.add(new SawMillPage("Basic", state));
		return pages;
	}

	/*@Override
	public void openJEI(IModuleState state){
		if(this instanceof IModuleJEI){
			Loader.instance();
			if(Loader.isModLoaded("JEI")){
				ModuleJeiPlugin.jeiRuntime.getRecipesGui().showCategories(Arrays.asList(((IModuleJEI)this).getJEIRecipeCategorys(state.getContainer())));
			}
		}
	}*/

	@Override
	public EnumToolType getType(IModuleState state) {
		return EnumToolType.KINETIC;
	}
}
