package de.nedelosk.modularmachines.common.modules.tools.jei;

import java.awt.Rectangle;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.ModularMachinesApi;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.integration.IModuleJEI;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.client.gui.GuiAssembler;
import de.nedelosk.modularmachines.common.core.BlockManager;
import de.nedelosk.modularmachines.common.core.ItemManager;
import de.nedelosk.modularmachines.common.modules.tools.jei.alloysmelter.AlloySmelterRecipeCategory;
import de.nedelosk.modularmachines.common.modules.tools.jei.alloysmelter.AlloySmelterRecipeWrapper;
import de.nedelosk.modularmachines.common.modules.tools.jei.boiler.BoilerRecipeCategory;
import de.nedelosk.modularmachines.common.modules.tools.jei.boiler.BoilerRecipeWrapper;
import de.nedelosk.modularmachines.common.modules.tools.jei.lathe.LatheRecipeCategory;
import de.nedelosk.modularmachines.common.modules.tools.jei.lathe.LatheRecipeWrapper;
import de.nedelosk.modularmachines.common.modules.tools.jei.pulverizer.PulverizerRecipeCategory;
import de.nedelosk.modularmachines.common.modules.tools.jei.pulverizer.PulverizerRecipeWrapper;
import de.nedelosk.modularmachines.common.plugins.jei.ModuleRecipeHandler;
import de.nedelosk.modularmachines.common.plugins.jei.ModuleRecipeWrapper;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.gui.IAdvancedGuiHandler;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class ModuleJeiPlugin extends BlankModPlugin {

	public static boolean isAdded;
	private static List<String> producerHandlers = Lists.newArrayList();
	public static IJeiRuntime jeiRuntime;

	@Override
	public void register(IModRegistry registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

		registry.getJeiHelpers().getSubtypeRegistry().useNbtForSubtypes(ItemManager.itemModules);

		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(BlockManager.blockModular));
		registry.addRecipeCategories(
				new AlloySmelterRecipeCategory(guiHelper),
				new BoilerRecipeCategory(guiHelper),
				new PulverizerRecipeCategory(guiHelper),
				new LatheRecipeCategory(guiHelper));

		for(IModuleContainer container : ModularMachinesApi.MODULE_CONTAINERS){
			IModule module = container.getModule();
			if(module instanceof IModuleJEI){
				registry.addRecipeCategoryCraftingItem(container.getItemStack(), ((IModuleJEI) module).getJEIRecipeCategorys(container));
			}
			if(container.getDescription() != null){
				registry.addDescription(container.getItemStack(), container.getDescription());
			}
		}
		registry.addDescription(new ItemStack(BlockManager.blockModular), "tile.modular.description");

		registry.addAdvancedGuiHandlers(new AssemblerAdvancedGuiHandler());

		registry.addRecipeHandlers(
				new ModuleRecipeHandler(ModuleCategoryUIDs.ALLOYSMELTER, AlloySmelterRecipeWrapper.class),
				new ModuleRecipeHandler(ModuleCategoryUIDs.BOILER, BoilerRecipeWrapper.class),
				new ModuleRecipeHandler(ModuleCategoryUIDs.PULVERIZER, PulverizerRecipeWrapper.class),
				new ModuleRecipeHandler(ModuleCategoryUIDs.LATHE, LatheRecipeWrapper.class));

		registry.addRecipes(ModuleRecipeWrapper.getRecipes("AlloySmelter", ModuleCategoryUIDs.ALLOYSMELTER, AlloySmelterRecipeWrapper.class));
		registry.addRecipes(ModuleRecipeWrapper.getRecipes("Boiler", ModuleCategoryUIDs.BOILER, BoilerRecipeWrapper.class));
		registry.addRecipes(ModuleRecipeWrapper.getRecipes("Pulverizer", ModuleCategoryUIDs.PULVERIZER, PulverizerRecipeWrapper.class));
		registry.addRecipes(ModuleRecipeWrapper.getRecipes("Lathe", ModuleCategoryUIDs.LATHE, LatheRecipeWrapper.class, guiHelper));
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		ModuleJeiPlugin.jeiRuntime = jeiRuntime;
	}

	private static class AssemblerAdvancedGuiHandler implements IAdvancedGuiHandler<GuiAssembler> {
		@Nonnull
		@Override
		public Class<GuiAssembler> getGuiContainerClass() {
			return GuiAssembler.class;
		}

		@Nullable
		@Override
		public List<Rectangle> getGuiExtraAreas(GuiAssembler guiContainer) {
			GuiAssembler guiAssembler = guiContainer;
			return guiAssembler.getExtraGuiAreas();
		}
	}
}
