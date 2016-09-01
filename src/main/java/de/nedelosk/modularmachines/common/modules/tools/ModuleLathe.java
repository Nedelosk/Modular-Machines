package de.nedelosk.modularmachines.common.modules.tools;

import java.util.Arrays;
import java.util.List;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.integration.IModuleJEI;
import de.nedelosk.modularmachines.api.modules.items.IModuleColored;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tools.EnumToolType;
import de.nedelosk.modularmachines.api.modules.tools.ModuleModeMachine;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.IToolMode;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.api.recipes.RecipeUtil;
import de.nedelosk.modularmachines.api.recipes.RecipeUtil.LatheModes;
import de.nedelosk.modularmachines.common.modules.pages.LathePage;
import de.nedelosk.modularmachines.common.modules.tools.jei.ModuleCategoryUIDs;
import de.nedelosk.modularmachines.common.modules.tools.jei.ModuleJeiPlugin;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleLathe extends ModuleModeMachine implements IModuleColored, IModuleJEI{

	public ModuleLathe() {
		super("lathe", LatheModes.ROD);
	}

	// Recipe
	@Override
	public RecipeItem[] getInputs(IModuleState state) {
		return ((IModuleInventory)state.getContentHandler(IModuleInventory.class)).getInputItems();
	}

	@Override
	public String[] getJEIRecipeCategorys(IModuleContainer container) {
		return new String[]{ModuleCategoryUIDs.LATHE};
	}

	@Override
	public void sendModuleUpdate(IModuleState state){
		IModularHandler handler = state.getModular().getHandler();
		if(handler instanceof IModularHandlerTileEntity){
			PacketHandler.sendToNetwork(new PacketSyncModule(handler, state), ((IModularHandlerTileEntity)handler).getPos(), (WorldServer) handler.getWorld());
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	protected String getModelFolder(IModuleContainer container) {
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
		pages.add(new LathePage("Basic", state));
		return pages;
	}

	@Override
	protected boolean isRecipeValid(IRecipe recipe, IModuleState state) {
		if(super.isRecipeValid(recipe, state)){
			if(recipe.get(RecipeUtil.LATHEMODE) == getCurrentMode(state)){
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
	public void openJEI(IModuleState state){
		if(this instanceof IModuleJEI){
			Loader.instance();
			if(Loader.isModLoaded("JEI")){
				ModuleJeiPlugin.jeiRuntime.getRecipesGui().showCategories(Arrays.asList(((IModuleJEI)this).getJEIRecipeCategorys(state.getContainer())));
			}
		}
	}

}
