package modularmachines.common.modules.machine.furnace;

import net.minecraft.item.ItemStack;

import modularmachines.api.recipes.RecipeItem;
import modularmachines.common.modules.machine.MachineCategorys;
import modularmachines.common.recipes.RecipeHeat;

public class RecipeFurnace extends RecipeHeat {

	public RecipeFurnace(ItemStack inputItem, ItemStack outputItem, int speed, double heatToRemove, double requiredHeat) {
		super(new RecipeItem[]{new RecipeItem(inputItem)}, new RecipeItem[]{new RecipeItem(outputItem)}, speed, heatToRemove, requiredHeat);
	}

	@Override
	public String getRecipeCategory() {
		return MachineCategorys.FURNACE;
	}

}
