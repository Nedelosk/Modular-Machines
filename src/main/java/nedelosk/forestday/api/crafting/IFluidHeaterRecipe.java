/**
 * (C) 2015 Nedelosk
 */
package nedelosk.forestday.api.crafting;

import nedelosk.forestday.common.machines.base.fluid.heater.FluidHeaterRecipe;
import nedelosk.forestday.common.machines.base.furnace.coke.CokeFurnaceRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidHeaterRecipe {

	void addRecipe(FluidStack input, FluidStack input2, FluidStack output, int burnTime);
	
	void addRecipe(ItemStack input, FluidStack input2, FluidStack output, int burnTime);
	
	void addRecipe(ItemStack input, FluidStack input2, ItemStack output, int burnTime);
	
	void addRecipe(FluidStack input, FluidStack output, int burnTime);
	
	void addRecipe(ItemStack input, FluidStack output, int burnTime);
	
	void addRecipe(FluidStack input, ItemStack output, int burnTime);
	
}
