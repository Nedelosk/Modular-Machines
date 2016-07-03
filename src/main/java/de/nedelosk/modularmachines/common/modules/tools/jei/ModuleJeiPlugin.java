package de.nedelosk.modularmachines.common.modules.tools.jei;

import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.material.EnumMaterials;
import de.nedelosk.modularmachines.common.core.BlockManager;
import de.nedelosk.modularmachines.common.core.ItemManager;
import de.nedelosk.modularmachines.common.core.ModuleManager;
import de.nedelosk.modularmachines.common.items.ItemModule;
import de.nedelosk.modularmachines.common.modules.tools.jei.alloysmelter.AlloySmelterRecipeCategory;
import de.nedelosk.modularmachines.common.modules.tools.jei.alloysmelter.AlloySmelterRecipeWrapper;
import de.nedelosk.modularmachines.common.modules.tools.jei.boiler.BoilerRecipeCategory;
import de.nedelosk.modularmachines.common.modules.tools.jei.boiler.BoilerRecipeWrapper;
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

		registry.addRecipeCategoryCraftingItem(ItemModule.getItem(ModuleManager.moduleAlloySmelterStone.getRegistryName(), EnumMaterials.STONE), ModuleCategoryUIDs.ALLOYSMELTER);
		registry.addRecipeCategoryCraftingItem(ItemModule.getItem(ModuleManager.moduleBoilerStone.getRegistryName(), EnumMaterials.STONE), ModuleCategoryUIDs.BOILER);
		registry.addRecipeCategoryCraftingItem(ItemModule.getItem(ModuleManager.moduleBoilerIron.getRegistryName(), EnumMaterials.IRON), ModuleCategoryUIDs.BOILER);
		registry.addRecipeCategoryCraftingItem(ItemModule.getItem(ModuleManager.moduleBoilerBronze.getRegistryName(), EnumMaterials.BRONZE), ModuleCategoryUIDs.BOILER);
		//registry.addRecipeCategoryCraftingItem(ItemModule.getItem(ModuleManager.moduleAlloySmelterStone.getRegistryName(), EnumMaterials.STONE), ModuleCategoryUIDs.PULVERIZER);

		registry.addRecipeHandlers(
				new ModuleRecipeHandler(ModuleCategoryUIDs.ALLOYSMELTER, AlloySmelterRecipeWrapper.class),
				new ModuleRecipeHandler(ModuleCategoryUIDs.BOILER, BoilerRecipeWrapper.class),
				new ModuleRecipeHandler(ModuleCategoryUIDs.PULVERIZER, PulverizerRecipeWrapper.class));

		registry.addRecipes(ModuleRecipeWrapper.getRecipes("AlloySmelter", ModuleCategoryUIDs.ALLOYSMELTER, AlloySmelterRecipeWrapper.class));
		registry.addRecipes(ModuleRecipeWrapper.getRecipes("Boiler", ModuleCategoryUIDs.BOILER, BoilerRecipeWrapper.class));
		registry.addRecipes(ModuleRecipeWrapper.getRecipes("Pulverizer", ModuleCategoryUIDs.PULVERIZER, PulverizerRecipeWrapper.class));
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
	}

	/*@Override
	public void loadConfig() {
		registerHandler(new CharcoalKilnHandler());
		registerHandler(new CampfireHandler());
		registerHandler(new ShapedModuleRecipeHandler());
		registerHandler(new CraftingRecipeKilnHandler());
		isAdded = false;
		for(IModuleContainer container : ModuleManager.moduleRegistry.getModuleContainers()) {
			IModule module = ModuleManager.moduleRegistry.getFakeModule(container);
			if (module instanceof IModuleTool) {
				IModuleTool machine = (IModuleTool) module;
				if (!producerHandlers.contains(machine.getRecipeCategory())) {
					new ModularMachinesHandler(machine.getRecipeCategory(), machine.createNEIPage(machine));
					producerHandlers.add(machine.getRecipeCategory());
				}
			}
		}
		isAdded = true;
	}*/
}
