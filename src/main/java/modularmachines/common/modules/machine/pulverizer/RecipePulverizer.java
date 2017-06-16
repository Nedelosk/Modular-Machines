package modularmachines.common.modules.machine.pulverizer;

import net.minecraft.item.ItemStack;

import modularmachines.api.recipes.OreStack;
import modularmachines.api.recipes.RecipeItem;
import modularmachines.common.modules.machine.MachineCategorys;
import modularmachines.common.recipes.Recipe;

public class RecipePulverizer extends Recipe{
	
	public RecipePulverizer(ItemStack input, ItemStack output, ItemStack outputSecond, float chance, int speed) {
		this(new RecipeItem(input), new RecipeItem[]{new RecipeItem(output), new RecipeItem(outputSecond, chance)}, speed);
	}
	
	public RecipePulverizer(ItemStack input, ItemStack output, int speed) {
		this(new RecipeItem(input), new RecipeItem(output), speed);
	}
	
	public RecipePulverizer(OreStack input, ItemStack output, float chance, int speed) {
		this(new RecipeItem(input), new RecipeItem(output, chance), speed);
	}
	
	public RecipePulverizer(OreStack input, ItemStack output, int speed) {
		this(new RecipeItem(input), new RecipeItem(output), speed);
	}
	
	public RecipePulverizer(OreStack input, ItemStack output, ItemStack outputSecond, float chance, int speed) {
		this(new RecipeItem(input), new RecipeItem[]{new RecipeItem(output), new RecipeItem(outputSecond, chance)}, speed);
	}
	
	public RecipePulverizer(RecipeItem input, RecipeItem[] outputItems, int speed) {
		super(new RecipeItem[]{input}, outputItems, speed);
	}
	
	public RecipePulverizer(RecipeItem input, RecipeItem output, int speed) {
		super(new RecipeItem[]{input}, new RecipeItem[]{output}, speed);
	}
	
	public RecipePulverizer(RecipeItem[] inputItems, RecipeItem[] outputItems, int speed) {
		super(inputItems, outputItems, speed);
	}

	@Override
	public String getRecipeCategory() {
		return MachineCategorys.PULVERIZER;
	}

}
