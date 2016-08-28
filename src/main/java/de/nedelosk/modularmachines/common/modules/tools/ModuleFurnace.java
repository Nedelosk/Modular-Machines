package de.nedelosk.modularmachines.common.modules.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import de.nedelosk.modularmachines.api.ItemUtil;
import de.nedelosk.modularmachines.api.gui.IContainerBase;
import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.handlers.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.slots.SlotModule;
import de.nedelosk.modularmachines.api.modules.integration.IModuleJEI;
import de.nedelosk.modularmachines.api.modules.items.IModuleColored;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tools.EnumToolType;
import de.nedelosk.modularmachines.api.modules.tools.IModuleMachine;
import de.nedelosk.modularmachines.api.recipes.IRecipe;
import de.nedelosk.modularmachines.api.recipes.IRecipeBuilder;
import de.nedelosk.modularmachines.api.recipes.Recipe;
import de.nedelosk.modularmachines.api.recipes.RecipeItem;
import de.nedelosk.modularmachines.client.gui.widgets.WidgetProgressBar;
import de.nedelosk.modularmachines.common.modules.handlers.ItemFilterMachine;
import de.nedelosk.modularmachines.common.modules.handlers.ModulePage;
import de.nedelosk.modularmachines.common.modules.handlers.OutputAllFilter;
import de.nedelosk.modularmachines.common.modules.tools.jei.ModuleJeiPlugin;
import de.nedelosk.modularmachines.common.recipse.RecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModuleFurnace extends ModuleBasicMachine implements IModuleColored, IModuleJEI {

	private static final List<IRecipe> furnaceRecipe = new ArrayList<>();
	public static final PropertyFurnaceRecipe FURNACERECIPE = new PropertyFurnaceRecipe("currentRecipe", furnaceRecipe);

	public ModuleFurnace() {
		super("furnace");
	}

	@Override
	public EnumToolType getType(IModuleState state) {
		return EnumToolType.HEAT;
	}

	@Override
	public String[] getJEIRecipeCategorys(IModuleContainer container) {
		return new String[]{"minecraft.smelting"};
	}

	@Override
	protected String getModelFolder(IModuleContainer container) {
		return "furnace";
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

	@Override
	public IModuleState createState(IModular modular, IModuleContainer container) {
		IModuleState state = createDefaultState(modular, container)
				.register(WORKTIME)
				.register(WORKTIMETOTAL)
				.register(CHANCE)
				.register(FURNACERECIPE)
				.register(HEATTOREMOVE)
				.register(HEATREQUIRED);
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
		return ((IModuleInventory)state.getContentHandler(IModuleInventory.class)).getInputItems();
	}

	@Override
	protected String getRecipeCategory(IModuleState state) {
		return null;
	}

	@Override
	public List<IRecipe> getRecipes(IModuleState state) {
		if(furnaceRecipe.isEmpty()){
			for(Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet()){
				ItemStack input = entry.getKey();
				ItemStack output = entry.getValue();
				if(input != null && output != null){
					IRecipeBuilder builder = new RecipeBuilder();
					builder.set(Recipe.NAME, ItemUtil.getStackToString(input) + "To" + ItemUtil.getStackToString(output))
					.set(Recipe.INPUTS, new RecipeItem[]{new RecipeItem(entry.getKey())})
					.set(Recipe.OUTPUTS, new RecipeItem[]{new RecipeItem(entry.getValue())})
					.set(Recipe.HEAT, 50D)
					.set(Recipe.HEATTOREMOVE, 0.15D)
					.set(Recipe.SPEED, 1);
					furnaceRecipe.add(builder.build());
				}
			}
		}
		return furnaceRecipe;
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new FurnacePage("Basic", state));
		return pages;
	}

	public static class FurnacePage extends ModulePage<IModuleMachine> {
		public FurnacePage(String pageID, IModuleState<IModuleMachine> module) {
			super(pageID, "furnace", module);
		}

		@Override
		public void createInventory(IModuleInventoryBuilder invBuilder) {
			invBuilder.addInventorySlot(true, 55, 35, new ItemFilterMachine());
			invBuilder.addInventorySlot(false, 116, 35, new OutputAllFilter());
		}

		@Override
		public void createSlots(IContainerBase<IModularHandler> container, List<SlotModule> modularSlots) {
			modularSlots.add(new SlotModule(state, 0));
			modularSlots.add(new SlotModule(state, 1));
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void addWidgets() {
			add(new WidgetProgressBar(82, 35, state.getModule().getWorkTime(state), state.getModule().getWorkTimeTotal(state)));
		}
	}

	@Override
	public int getColor(IModuleContainer container) {
		return 0xD6A829;
	}
}
