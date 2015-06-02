/**
 * (C) 2015 Nedelosk
 */
package nedelosk.forestday.api.crafting;

import net.minecraft.item.ItemStack;

public interface IMaceratorRecipe {

	public void addRecipe(ItemStack input, ItemStack output, int minRoughness, int maxRoughness, int burnTime, boolean isNEI);
	
	public void addRecipe(String input, ItemStack output, int minRoughness, int maxRoughness, int burnTime, boolean isNEI);
	
}
