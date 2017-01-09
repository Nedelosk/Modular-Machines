package modularmachines.common.modules.tools;

import java.util.Arrays;
import java.util.List;

import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.containers.IModuleColoredItem;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.api.modules.integration.IModuleJEI;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.tools.EnumToolType;
import modularmachines.api.recipes.IRecipe;
import modularmachines.api.recipes.IToolMode;
import modularmachines.api.recipes.RecipeItem;
import modularmachines.api.recipes.RecipeUtil;
import modularmachines.api.recipes.RecipeUtil.LatheModes;
import modularmachines.common.modules.pages.LathePage;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.packets.PacketSyncModule;
import modularmachines.common.plugins.jei.CategoryUIDs;
import modularmachines.common.plugins.jei.JeiPlugin;

public class ModuleLathe extends ModuleModeMachine implements IModuleColoredItem, IModuleJEI {

	public ModuleLathe() {
		super("lathe", LatheModes.ROD);
	}

	// Recipe
	@Override
	public RecipeItem[] getInputs(IModuleState state) {
		return state.getPage(LathePage.class).getInventory().getRecipeItems();
	}

	@Override
	public String[] getJEIRecipeCategorys(IModuleContainer container) {
		return new String[] { CategoryUIDs.LATHE };
	}

	@Override
	public void sendModuleUpdate(IModuleState state) {
		IModularHandler handler = state.getModular().getHandler();
		if (handler instanceof IModularHandlerTileEntity) {
			PacketHandler.sendToNetwork(new PacketSyncModule(state), ((IModularHandlerTileEntity) handler).getPos(), (WorldServer) handler.getWorld());
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	protected String getModelFolder(IModuleItemContainer container) {
		return "lathes";
	}

	@Override
	public EnumToolType getType(IModuleState state) {
		return EnumToolType.KINETIC;
	}

	@Override
	public String getRecipeCategory(IModuleState state) {
		return "Lathe";
	}

	@Override
	public Class<? extends IToolMode> getModeClass() {
		return LatheModes.class;
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new LathePage(state));
		return pages;
	}

	@Override
	protected boolean isRecipeValid(IRecipe recipe, IModuleState state) {
		if (super.isRecipeValid(recipe, state)) {
			if (recipe.getValue(RecipeUtil.LATHEMODE) == getCurrentMode(state)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getColor(IModuleContainer container) {
		return 0xC4C09C;
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
}
