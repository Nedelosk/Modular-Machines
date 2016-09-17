package de.nedelosk.modularmachines.common.modules.tools;

import java.util.Arrays;
import java.util.List;

import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.integration.IModuleJEI;
import de.nedelosk.modularmachines.api.modules.items.IModuleColoredItem;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.models.IModelHandler;
import de.nedelosk.modularmachines.api.modules.models.ModelHandlerStatus;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.IModuleStateClient;
import de.nedelosk.modularmachines.api.modules.tools.EnumToolType;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.common.modules.pages.PulverizerPage;
import de.nedelosk.modularmachines.common.plugins.jei.CategoryUIDs;
import de.nedelosk.modularmachines.common.plugins.jei.JeiPlugin;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModulePulverizer extends ModuleBasicMachine implements IModuleColoredItem, IModuleJEI{

	public ModulePulverizer() {
		super("pulverizer");
	}

	@Override
	public String getRecipeCategory(IModuleState state) {
		return "Pulverizer";
	}

	@Override
	public String[] getJEIRecipeCategorys(IModuleContainer container) {
		return new String[]{CategoryUIDs.PULVERIZER};
	}

	@SideOnly(Side.CLIENT)
	@Override
	protected String getModelFolder(IModuleContainer container) {
		return "pulverizers";
	}

	@Override
	public EnumToolType getType(IModuleState state) {
		return EnumToolType.KINETIC;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean needHandlerReload(IModuleStateClient state) {
		IModelHandler handler = state.getModelHandler();
		if(handler instanceof ModelHandlerStatus){
			ModelHandlerStatus status = (ModelHandlerStatus) handler;
			if(getWorkTime(state) > 0){
				if(!status.status){
					status.status = true;
					return true;
				}
			}else{
				if(status.status){
					status.status = false;
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int getColor(IModuleContainer container) {
		return 0x286F92;
	}

	@Override
	public void openJEI(IModuleState state){
		if(this instanceof IModuleJEI){
			Loader.instance();
			if(Loader.isModLoaded("JEI")){
				JeiPlugin.jeiRuntime.getRecipesGui().showCategories(Arrays.asList(((IModuleJEI)this).getJEIRecipeCategorys(state.getContainer())));
			}
		}
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new PulverizerPage(state));
		return pages;
	}

	@Override
	public RecipeItem[] getInputs(IModuleState state) {
		return state.getPage(PulverizerPage.class).getInventory().getRecipeItems();
	}

}
