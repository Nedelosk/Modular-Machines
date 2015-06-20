/**
 * (C) 2015 Nedelosk
 */
package nedelosk.forestday.api.crafting;

import nedelosk.forestday.common.machines.base.furnace.alloysmelter.AlloySmelterRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IAlloySmelterRecipe {

	void addRecipe(ItemStack input, int burnTime, ItemStack... output);
	
	void addRecipe(ItemStack input, FluidStack inputFluid, int burnTime, ItemStack... output);
	
	void addRecipe(ItemStack input, FluidStack inputFluid, int burnTime, int energy, ItemStack... output);
	
	void addRecipe(ItemStack input, int burnTime, int energy, ItemStack... output);
	
}
