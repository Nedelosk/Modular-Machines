/**
 * (C) 2015 Nedelosk
 */
package nedelosk.forestday.api.crafting;

import net.minecraft.item.ItemStack;

public interface IKilnRecipe {

	void addRecipe(ItemStack input1, ItemStack input2, ItemStack output1, ItemStack output2);

	void addRecipe(String input1, ItemStack input2, ItemStack output1, ItemStack output2);

	void addRecipe(ItemStack input1, String input2, ItemStack output1, ItemStack output2);

	void addRecipe(String input1, String input2, ItemStack output1, ItemStack output2);

}
