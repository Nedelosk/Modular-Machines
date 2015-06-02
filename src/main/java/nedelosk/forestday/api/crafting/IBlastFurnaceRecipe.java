/**
 * (C) 2015 Nedelosk
 */
package nedelosk.forestday.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IBlastFurnaceRecipe {

	public void addRecipe(ItemStack[] input, FluidStack[] output, int minHeat, int maxHeat);
	
}
