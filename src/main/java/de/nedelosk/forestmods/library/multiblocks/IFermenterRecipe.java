package de.nedelosk.forestmods.library.multiblocks;

import net.minecraftforge.fluids.FluidStack;

public interface IFermenterRecipe {

	void addRecipe(int burnTime, FluidStack input, FluidStack output);
}
