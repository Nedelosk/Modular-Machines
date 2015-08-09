package nedelosk.forestday.api.crafting;

import net.minecraft.item.ItemStack;

public interface ICampfireRecipe {

	void addRecipe(ItemStack input, ItemStack input2, ItemStack output, int potTier, int burnTime);
	
	void addRecipe(ItemStack input, ItemStack output, int potTier, int burnTime);
	
}
