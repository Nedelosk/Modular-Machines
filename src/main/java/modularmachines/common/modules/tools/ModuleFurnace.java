package modularmachines.common.modules.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.Loader;

import modularmachines.api.ItemUtil;
import modularmachines.api.modules.IModulePage;
import modularmachines.api.modules.containers.IModuleColoredItem;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.api.modules.containers.IModuleProvider;
import modularmachines.api.modules.integration.IModuleJEI;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.tools.EnumToolType;
import modularmachines.api.recipes.IRecipe;
import modularmachines.api.recipes.IRecipeBuilder;
import modularmachines.api.recipes.Recipe;
import modularmachines.api.recipes.RecipeItem;
import modularmachines.common.modules.pages.FurnacePage;
import modularmachines.common.modules.propertys.PropertyFurnaceRecipe;
import modularmachines.common.plugins.jei.JeiPlugin;
import modularmachines.common.recipse.RecipeBuilder;

public class ModuleFurnace extends ModuleBasicMachine implements IModuleColoredItem, IModuleJEI {

	public static final PropertyFurnaceRecipe FURNACERECIPE = new PropertyFurnaceRecipe("currentRecipe", makeRecipes());

	public ModuleFurnace() {
		super("furnace");
	}

	@Override
	public EnumToolType getType(IModuleState state) {
		return EnumToolType.HEAT;
	}

	@Override
	public String[] getJEIRecipeCategorys(IModuleContainer container) {
		return new String[] { "minecraft.smelting" };
	}

	@Override
	protected String getModelFolder(IModuleItemContainer container) {
		return "furnace";
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
	public IModuleState createState(IModuleProvider provider, IModuleContainer container) {
		IModuleState state = createDefaultState(provider, container).register(WORKTIME).register(WORKTIMETOTAL).register(CHANCE).register(FURNACERECIPE).register(HEATTOREMOVE).register(HEATREQUIRED);
		return state;
	}

	@Override
	public IRecipe getCurrentRecipe(IModuleState state) {
		return state.get(FURNACERECIPE);
	}

	@Override
	public void setCurrentRecipe(IModuleState state, IRecipe recipe) {
		state.set(FURNACERECIPE, recipe);
	}

	@Override
	public RecipeItem[] getInputs(IModuleState state) {
		return state.getPage(FurnacePage.class).getInventory().getRecipeItems();
	}

	@Override
	protected String getRecipeCategory(IModuleState state) {
		return null;
	}

	@Override
	public List<IRecipe> getRecipes(IModuleState state) {
		return FURNACERECIPE.getRecipes();
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new FurnacePage(state));
		return pages;
	}

	protected static List<IRecipe> makeRecipes() {
		List<IRecipe> furnaceRecipe = new ArrayList<>();
		for (Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet()) {
			ItemStack input = entry.getKey();
			ItemStack output = entry.getValue();
			if (input != null && output != null) {
				IRecipeBuilder builder = new RecipeBuilder();
				builder.set(Recipe.NAME, ItemUtil.getStackToString(input) + "To" + ItemUtil.getStackToString(output)).set(Recipe.INPUTS, new RecipeItem[] { new RecipeItem(entry.getKey()) })
						.set(Recipe.OUTPUTS, new RecipeItem[] { new RecipeItem(entry.getValue()) }).set(Recipe.HEAT, 50D).set(Recipe.HEATTOREMOVE, 0.15D).set(Recipe.SPEED, 1);
				furnaceRecipe.add(builder.build());
			}
		}
		return furnaceRecipe;
	}

	@Override
	public int getColor(IModuleContainer container) {
		return 0xD6A829;
	}
}
