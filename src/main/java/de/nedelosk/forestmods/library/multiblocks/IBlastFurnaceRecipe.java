package de.nedelosk.forestmods.library.multiblocks;

import net.minecraftforge.fluids.FluidStack;

public interface IBlastFurnaceRecipe {

	void addRecipe(int burnTime, FluidStack[] output, Object[] input, int heat);
}
