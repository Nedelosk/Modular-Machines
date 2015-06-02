/**
 * (C) 2015 Nedelosk
 */
package nedelosk.forestday.api.crafting;

import net.minecraftforge.fluids.FluidStack;

public interface IAirHeaterRecipe {

	public void addRecipe(FluidStack input, FluidStack output, int minHeat, int maxHeat);
	
}
