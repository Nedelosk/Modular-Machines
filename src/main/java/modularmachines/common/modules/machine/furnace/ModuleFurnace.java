package modularmachines.common.modules.machine.furnace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import modularmachines.api.modules.IModuleStorage;
import modularmachines.api.recipes.IRecipeConsumer;
import modularmachines.api.recipes.IRecipeHeat;
import modularmachines.api.recipes.RecipeItem;
import modularmachines.common.inventory.ItemHandlerModule;
import modularmachines.common.modules.IModuleJei;
import modularmachines.common.modules.filters.FilterMachine;
import modularmachines.common.modules.filters.OutputFilter;
import modularmachines.common.modules.machine.MachineCategorys;
import modularmachines.common.modules.machine.ModuleHeatMachine;

public class ModuleFurnace extends ModuleHeatMachine<IRecipeHeat> implements IModuleJei {

	public static final List<IRecipeHeat> FURNACE_RECIPES = new ArrayList<>();
	public final ItemHandlerModule itemHandler;

	public ModuleFurnace(IModuleStorage storage, int workTimeModifier) {
		super(storage, workTimeModifier);
		itemHandler = new ItemHandlerModule(this);
		itemHandler.addSlot(true).addFilter(FilterMachine.INSTANCE);
		itemHandler.addSlot(false).addFilter(OutputFilter.INSTANCE);
	}
	
	@Override
	public String[] getJeiRecipeCategorys() {
		return new String[] { MachineCategorys.FURNACE };
	}
	
	@Override
	protected IRecipeConsumer[] getConsumers() {
		return new IRecipeConsumer[]{itemHandler};
	}

	@Override
	public RecipeItem[] getInputs() {
		return itemHandler.getInputs();
	}
	
	@Override
	protected String getRecipeCategory() {
		return MachineCategorys.FURNACE;
	}
	
	@Override
	protected void createComponents() {
		super.createComponents();
		addComponent(new ModuleComponentFurnace(this));
	}
	
	@Override
	public ItemHandlerModule getItemHandler() {
		return itemHandler;
	}
	
	@Override
	public List<IRecipeHeat> getRecipes() {
		if(FURNACE_RECIPES.isEmpty()){
			for (Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet()) {
				ItemStack input = entry.getKey();
				ItemStack output = entry.getValue();
				if (input != null && output != null) {
					FURNACE_RECIPES.add(new RecipeFurnace(entry.getKey(),entry.getValue(), 2, 0.15D, 50D));
				}
			}
		}
		return FURNACE_RECIPES;
	}
}
