package nedelosk.forestday.api.crafting;

import net.minecraft.item.ItemStack;

public interface IWorkbenchRecipe {
	
	public void addRecipe(ItemStack input, ItemStack inputTool, ItemStack output, int burnTime);
	
	public void addRecipe(OreStack input, ItemStack inputTool, ItemStack output, int burnTime);
	
	public void addRecipe(ItemStack input, OreStack inputTool, ItemStack output, int burnTime);
	
	public void addRecipe(OreStack input, OreStack inputTool, ItemStack output, int burnTime);
	
	public void addRecipe(ItemStack input, ItemStack inputTool, ItemStack inputPattern, ItemStack output, int burnTime);
	
	public void addRecipe(OreStack input, ItemStack inputTool, ItemStack inputPattern, ItemStack output, int burnTime);
	
	public void addRecipe(ItemStack input, OreStack inputTool, ItemStack inputPattern, ItemStack output, int burnTime);
	
	public void addRecipe(ItemStack input, ItemStack inputTool, OreStack inputPattern, ItemStack output, int burnTime);
	
	public void addRecipe(OreStack input, OreStack inputTool, ItemStack inputPattern, ItemStack output, int burnTime);
	
	public void addRecipe(ItemStack input, OreStack inputTool, OreStack inputPattern, ItemStack output, int burnTime);
	
	public void addRecipe(OreStack input, ItemStack inputTool, OreStack inputPattern, ItemStack output, int burnTime);
	
	public void addRecipe(OreStack input, OreStack inputTool, OreStack inputPattern, ItemStack output, int burnTime);
	
}
