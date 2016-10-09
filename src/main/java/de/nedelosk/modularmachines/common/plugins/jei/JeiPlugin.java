package de.nedelosk.modularmachines.common.plugins.jei;

import java.awt.Rectangle;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemContainer;
import de.nedelosk.modularmachines.api.modules.integration.IModuleJEI;
import de.nedelosk.modularmachines.client.gui.GuiAssembler;
import de.nedelosk.modularmachines.client.gui.GuiModuleCrafter;
import de.nedelosk.modularmachines.common.core.BlockManager;
import de.nedelosk.modularmachines.common.inventory.ContainerModuleCrafter;
import de.nedelosk.modularmachines.common.plugins.jei.alloysmelter.AlloySmelterRecipeCategory;
import de.nedelosk.modularmachines.common.plugins.jei.alloysmelter.AlloySmelterRecipeWrapper;
import de.nedelosk.modularmachines.common.plugins.jei.boiler.BoilerRecipeCategory;
import de.nedelosk.modularmachines.common.plugins.jei.boiler.BoilerRecipeWrapper;
import de.nedelosk.modularmachines.common.plugins.jei.lathe.LatheRecipeCategory;
import de.nedelosk.modularmachines.common.plugins.jei.lathe.LatheRecipeWrapper;
import de.nedelosk.modularmachines.common.plugins.jei.pulverizer.PulverizerRecipeCategory;
import de.nedelosk.modularmachines.common.plugins.jei.pulverizer.PulverizerRecipeWrapper;
import de.nedelosk.modularmachines.common.utils.Translator;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.gui.IAdvancedGuiHandler;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JeiPlugin extends BlankModPlugin {

	public static boolean isAdded;
	private static List<String> producerHandlers = Lists.newArrayList();
	public static IJeiRuntime jeiRuntime;

	@Override
	public void register(IModRegistry registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

		registry.getJeiHelpers().getSubtypeRegistry().useNbtForSubtypes(ModuleManager.defaultModuleItem, ModuleManager.defaultModuleHolderItem);

		registry.addRecipeClickArea(GuiModuleCrafter.class, 93, 35, 22, 15, VanillaRecipeCategoryUid.CRAFTING, CategoryUIDs.CRAFTING);

		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(BlockManager.blockModular));
		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(ModuleManager.defaultModuleItemContainer));
		registry.addRecipeCategories(
				new ModuleCrafterRecipeCategory(guiHelper),
				new AlloySmelterRecipeCategory(guiHelper),
				new BoilerRecipeCategory(guiHelper),
				new PulverizerRecipeCategory(guiHelper),
				new LatheRecipeCategory(guiHelper));

		for(IModuleItemContainer container : ModuleManager.MODULE_CONTAINERS){
			for(IModuleContainer moduleContainer : container.getContainers()){
				IModule module = moduleContainer.getModule();
				if(module instanceof IModuleJEI){
					registry.addRecipeCategoryCraftingItem(container.getItemStack(), ((IModuleJEI) module).getJEIRecipeCategorys(moduleContainer));
				}
				String description = moduleContainer.getDescription();
				if(moduleContainer.getDescription() != null && Translator.canTranslateToLocal(description)){
					registry.addDescription(container.getItemStack(), description);
				}
			}
		}
		registry.addDescription(new ItemStack(BlockManager.blockModular), "tile.modular.description");

		registry.addRecipeCategoryCraftingItem(new ItemStack(BlockManager.blockModuleCrafter), CategoryUIDs.CRAFTING);

		registry.addAdvancedGuiHandlers(new AssemblerAdvancedGuiHandler());

		registry.addRecipeHandlers(
				new ModuleCrafterRecipeHandler(),
				new ModuleRecipeHandler(CategoryUIDs.ALLOYSMELTER, AlloySmelterRecipeWrapper.class),
				new ModuleRecipeHandler(CategoryUIDs.BOILER, BoilerRecipeWrapper.class),
				new ModuleRecipeHandler(CategoryUIDs.PULVERIZER, PulverizerRecipeWrapper.class),
				new ModuleRecipeHandler(CategoryUIDs.LATHE, LatheRecipeWrapper.class));

		registry.addRecipes(ModuleRecipeWrapper.getRecipes("AlloySmelter", CategoryUIDs.ALLOYSMELTER, AlloySmelterRecipeWrapper.class));
		registry.addRecipes(ModuleRecipeWrapper.getRecipes("Boiler", CategoryUIDs.BOILER, BoilerRecipeWrapper.class));
		registry.addRecipes(ModuleRecipeWrapper.getRecipes("Pulverizer", CategoryUIDs.PULVERIZER, PulverizerRecipeWrapper.class));
		registry.addRecipes(ModuleRecipeWrapper.getRecipes("Lathe", CategoryUIDs.LATHE, LatheRecipeWrapper.class, guiHelper));

		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();
		recipeTransferRegistry.addRecipeTransferHandler(ContainerModuleCrafter.class, VanillaRecipeCategoryUid.CRAFTING, 36, 9, 0, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerModuleCrafter.class, CategoryUIDs.CRAFTING, 36, 10, 0, 36);
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		JeiPlugin.jeiRuntime = jeiRuntime;
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
			return guiContainer.getExtraGuiAreas();
		}
	}
}
