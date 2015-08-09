/**
 * (C) 2015 Nedelosk
 */
package nedelosk.forestday.api.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface ICokeFurnaceRecipe {

	public void addRecipe(ItemStack input, ItemStack output, ItemStack output1, ItemStack output2, FluidStack outputFluid);
	
	public void addRecipe(String input, ItemStack output, ItemStack output1, ItemStack output2, FluidStack outputFluid);
	
}
