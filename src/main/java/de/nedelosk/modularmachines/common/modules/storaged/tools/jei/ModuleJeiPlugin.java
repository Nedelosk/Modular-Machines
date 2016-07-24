package de.nedelosk.modularmachines.common.modules.storaged.tools.jei;

import java.awt.Rectangle;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.integration.IModuleJEI;
import de.nedelosk.modularmachines.client.gui.GuiAssembler;
import de.nedelosk.modularmachines.common.core.BlockManager;
import de.nedelosk.modularmachines.common.core.ItemManager;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import de.nedelosk.modularmachines.common.core.ModuleManager;
import de.nedelosk.modularmachines.common.items.ItemModule;
import de.nedelosk.modularmachines.common.modules.storaged.tools.jei.alloysmelter.AlloySmelterRecipeCategory;
import de.nedelosk.modularmachines.common.modules.storaged.tools.jei.alloysmelter.AlloySmelterRecipeWrapper;
import de.nedelosk.modularmachines.common.modules.storaged.tools.jei.boiler.BoilerRecipeCategory;
import de.nedelosk.modularmachines.common.modules.storaged.tools.jei.boiler.BoilerRecipeWrapper;
import de.nedelosk.modularmachines.common.modules.storaged.tools.jei.pulverizer.PulverizerRecipeCategory;
import de.nedelosk.modularmachines.common.modules.storaged.tools.jei.pulverizer.PulverizerRecipeWrapper;
import de.nedelosk.modularmachines.common.plugins.jei.ModuleRecipeHandler;
import de.nedelosk.modularmachines.common.plugins.jei.ModuleRecipeWrapper;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.gui.IAdvancedGuiHandler;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class ModuleJeiPlugin extends BlankModPlugin {

	public static boolean isAdded;
	private static List<String> producerHandlers = Lists.newArrayList();

	@Override
	public void register(IModRegistry registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

		registry.getJeiHelpers().getSubtypeRegistry().useNbtForSubtypes(ItemManager.itemModules);

		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(BlockManager.blockModular));
		registry.addRecipeCategories(
				new AlloySmelterRecipeCategory(guiHelper),
				new BoilerRecipeCategory(guiHelper),
				new PulverizerRecipeCategory(guiHelper));

		for(IModuleContainer container : ModularMachines.iModuleContainerRegistry){
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

		registry.addRecipeCategoryCraftingItem(ItemModule.getItem(ModuleManager.moduleBoilerIron.getRegistryName(), EnumMetalMaterials.IRON), ModuleCategoryUIDs.BOILER);
		registry.addRecipeCategoryCraftingItem(ItemModule.getItem(ModuleManager.moduleBoilerBronze.getRegistryName(), EnumMetalMaterials.BRONZE), ModuleCategoryUIDs.BOILER);
		registry.addRecipeCategoryCraftingItem(ItemModule.getItem(ModuleManager.moduleAlloySmelterIron.getRegistryName(), EnumMetalMaterials.IRON), ModuleCategoryUIDs.ALLOYSMELTER);
		registry.addRecipeCategoryCraftingItem(ItemModule.getItem(ModuleManager.moduleAlloySmelterBronze.getRegistryName(), EnumMetalMaterials.BRONZE), ModuleCategoryUIDs.ALLOYSMELTER);
		registry.addRecipeCategoryCraftingItem(ItemModule.getItem(ModuleManager.modulePulverizerIron.getRegistryName(), EnumMetalMaterials.IRON), ModuleCategoryUIDs.PULVERIZER);
		registry.addRecipeCategoryCraftingItem(ItemModule.getItem(ModuleManager.modulePulverizerBronze.getRegistryName(), EnumMetalMaterials.BRONZE), ModuleCategoryUIDs.PULVERIZER);

		registry.addRecipeHandlers(
				new ModuleRecipeHandler(ModuleCategoryUIDs.ALLOYSMELTER, AlloySmelterRecipeWrapper.class),
				new ModuleRecipeHandler(ModuleCategoryUIDs.BOILER, BoilerRecipeWrapper.class),
				new ModuleRecipeHandler(ModuleCategoryUIDs.PULVERIZER, PulverizerRecipeWrapper.class));

		registry.addRecipes(ModuleRecipeWrapper.getRecipes("AlloySmelter", ModuleCategoryUIDs.ALLOYSMELTER, AlloySmelterRecipeWrapper.class));
		registry.addRecipes(ModuleRecipeWrapper.getRecipes("Boiler", ModuleCategoryUIDs.BOILER, BoilerRecipeWrapper.class));
		registry.addRecipes(ModuleRecipeWrapper.getRecipes("Pulverizer", ModuleCategoryUIDs.PULVERIZER, PulverizerRecipeWrapper.class));
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
