package nedelosk.modularmachines.api.multiblocks;

import net.minecraftforge.fluids.FluidStack;

public interface IFermenterRecipe {

	void addRecipe(int burnTime, FluidStack input, FluidStack output);
	
}
