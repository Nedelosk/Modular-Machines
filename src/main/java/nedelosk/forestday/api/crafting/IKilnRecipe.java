/**
 * (C) 2015 Nedelosk
 */
package nedelosk.forestday.api.crafting;

import net.minecraft.item.ItemStack;

public interface IKilnRecipe {

	public void addRecipe(ItemStack input1, ItemStack input2, ItemStack output1, ItemStack output2);
	
	public void addRecipe(String input1, ItemStack input2, ItemStack output1, ItemStack output2);
	
	public void addRecipe(ItemStack input1, String input2, ItemStack output1, ItemStack output2);
	
	public void addRecipe(String input1, String input2, ItemStack output1, ItemStack output2);
	
}
