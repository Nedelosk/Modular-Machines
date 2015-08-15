package nedelosk.modularmachines.api.multiblocks;

import net.minecraftforge.fluids.FluidStack;

public interface IBlastFurnaceRecipe {

	void addRecipe(int burnTime, FluidStack[] output, Object[] input, int heat);
	
}
