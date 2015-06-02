package nedelosk.forestday.structure.airheater.crafting;

import net.minecraftforge.fluids.FluidStack;

public class AirHeaterRecipe {
	private FluidStack input;
	private FluidStack output;
	private int minHeat, maxHeat;
	
	public AirHeaterRecipe(FluidStack input, FluidStack output, int minHeat, int maxHeat){
		this.input = input;
		this.output = output;
		this.minHeat = minHeat;
		this.maxHeat = maxHeat;
	}
	
	public FluidStack getInput(){
		return this.input;
	}
	
	public FluidStack getOutput(){
		return this.output.copy();
	}
	
	public int getMinHeat()
	{
		return this.minHeat;
	}
	
	public int getMaxHeat()
	{
		return this.maxHeat;
	}
	
}
