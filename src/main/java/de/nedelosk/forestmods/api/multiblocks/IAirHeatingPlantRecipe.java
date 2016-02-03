package de.nedelosk.forestmods.api.multiblocks;

import net.minecraftforge.fluids.FluidStack;

public interface IAirHeatingPlantRecipe {

	void addRecipe(int burnTime, FluidStack intput, FluidStack output);
}
