package modularmachines.common.modules.filters;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import modularmachines.api.recipes.RecipeItem;
import modularmachines.common.inventory.IContentFilter;
import modularmachines.common.modules.machine.ModuleMachine;

public class FilterMachine<O> implements IContentFilter<O, ModuleMachine> {

	public static final FilterMachine INSTANCE = new FilterMachine();

	public static FilterMachine<ItemStack> itemFilter(){
		return INSTANCE;
	}
	
	public static FilterMachine<FluidStack> fluidFilter(){
		return INSTANCE;
	}
	
	private FilterMachine() {
	}

	@Override
	public boolean isValid(int index, Object content, ModuleMachine module) {
		if (content == null) {
			return false;
		}
		RecipeItem recipeItem = null;
		if (content instanceof ItemStack) {
			recipeItem = new RecipeItem((ItemStack) content);
		} else if (content instanceof FluidStack) {
			recipeItem = new RecipeItem((FluidStack) content);
		} else {
			return false;
		}
		return module.isRecipeInput(index, recipeItem);
	}
}
