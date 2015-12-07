package nedelosk.forestday.api.crafting;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public interface IWorkbenchRecipe {

	void addRecipe(ItemStack input, ItemStack inputTool, ItemStack output, int burnTime);

	void addRecipe(OreStack input, ItemStack inputTool, ItemStack output, int burnTime);

	void addRecipe(ItemStack input, OreStack inputTool, ItemStack output, int burnTime);

	void addRecipe(OreStack input, OreStack inputTool, ItemStack output, int burnTime);

	void addRecipe(ItemStack input, ItemStack inputTool, ItemStack inputPattern, ItemStack output, int burnTime);

	void addRecipe(OreStack input, ItemStack inputTool, ItemStack inputPattern, ItemStack output, int burnTime);

	void addRecipe(ItemStack input, OreStack inputTool, ItemStack inputPattern, ItemStack output, int burnTime);

	void addRecipe(ItemStack input, ItemStack inputTool, OreStack inputPattern, ItemStack output, int burnTime);

	void addRecipe(OreStack input, OreStack inputTool, ItemStack inputPattern, ItemStack output, int burnTime);

	void addRecipe(ItemStack input, OreStack inputTool, OreStack inputPattern, ItemStack output, int burnTime);

	void addRecipe(OreStack input, ItemStack inputTool, OreStack inputPattern, ItemStack output, int burnTime);

	void addRecipe(OreStack input, OreStack inputTool, OreStack inputPattern, ItemStack output, int burnTime);

	void addOutput(ItemStack stack);

	ArrayList<ItemStack> getOutputs();

}
