package nedelosk.modularmachines.common.crafting;

import net.minecraftforge.fluids.FluidStack;

public class AirHeatingPlantRecipe {
	private FluidStack input;
	private FluidStack output;
	private int burntTime;
	
	public AirHeatingPlantRecipe(int burnTime, FluidStack input, FluidStack output){
		this.input = input;
		this.output = output;
		this.burntTime = burnTime;
	}
	
	public int getBurntTime() {
		return burntTime;
	}
	
	public FluidStack getInput() {
		return input;
	}
	
	public FluidStack getOutput() {
		return output;
	}
	
}
