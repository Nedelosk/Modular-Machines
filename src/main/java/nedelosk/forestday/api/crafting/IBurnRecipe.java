/**
 * (C) 2015 Nedelosk
 */
package nedelosk.forestday.api.crafting;

import net.minecraft.item.ItemStack;

public interface IBurnRecipe {

	public void addRecipe(ItemStack input, ItemStack output, int burnTime, BurningMode mode);
	
	public void addRecipe(String input, ItemStack output, int burnTime, BurningMode mode);
	
}
