package modularmachines.common.modules.tools;

import java.util.Arrays;
import java.util.List;

import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.containers.IModuleColoredItem;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.api.modules.integration.IModuleJEI;
import modularmachines.api.modules.models.IModelHandler;
import modularmachines.api.modules.models.ModelHandlerStatus;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.state.IModuleStateClient;
import modularmachines.api.modules.tools.EnumToolType;
import modularmachines.api.recipes.RecipeItem;
import modularmachines.common.modules.pages.AlloySmelterPage;
import modularmachines.common.plugins.jei.CategoryUIDs;
import modularmachines.common.plugins.jei.JeiPlugin;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleAlloySmelter extends ModuleBasicMachine implements IModuleColoredItem, IModuleJEI {

	public ModuleAlloySmelter() {
		super("alloysmelter");
	}

	@Override
	public String getRecipeCategory(IModuleState state) {
		return "AlloySmelter";
	}

	@Override
	public String[] getJEIRecipeCategorys(IModuleContainer container) {
		return new String[] { CategoryUIDs.ALLOYSMELTER };
	}

	@Override
	public RecipeItem[] getInputs(IModuleState state) {
		return state.getPage(AlloySmelterPage.class).getInventory().getRecipeItems();
	}

	@Override
	public EnumToolType getType(IModuleState state) {
		return EnumToolType.HEAT;
	}

	@SideOnly(Side.CLIENT)
	@Override
	protected String getModelFolder(IModuleItemContainer container) {
		return "alloysmelters";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean needHandlerReload(IModuleStateClient state) {
		IModelHandler handler = state.getModelHandler();
		if (handler instanceof ModelHandlerStatus) {
			ModelHandlerStatus status = (ModelHandlerStatus) handler;
			if (getWorkTime(state) > 0) {
				if (!status.status) {
					status.status = true;
					return true;
				}
			} else {
				if (status.status) {
					status.status = false;
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new AlloySmelterPage(state));
		return pages;
	}

	@Override
	public void openJEI(IModuleState state) {
		if (this instanceof IModuleJEI) {
			Loader.instance();
			if (Loader.isModLoaded("JEI")) {
				JeiPlugin.jeiRuntime.getRecipesGui().showCategories(Arrays.asList(((IModuleJEI) this).getJEIRecipeCategorys(state.getContainer())));
			}
		}
	}

	@Override
	public int getColor(IModuleContainer container) {
		return 0x9C1645;
	}
}
