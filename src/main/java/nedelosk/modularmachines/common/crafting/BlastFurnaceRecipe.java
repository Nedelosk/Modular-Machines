package nedelosk.modularmachines.common.crafting;

import net.minecraftforge.fluids.FluidStack;

public class BlastFurnaceRecipe {
	private Object[] input;
	private FluidStack[] output = new FluidStack[2];
	private int burntTime;
	private int heat;

	public BlastFurnaceRecipe(int burnTime, FluidStack[] output, Object[] input, int heat) {
		this.input = input;
		this.output = output;
		this.burntTime = burnTime;
		this.heat = heat;
	}

	public int getBurntTime() {
		return burntTime;
	}

	public Object[] getInput() {
		return input;
	}

	public FluidStack[] getOutput() {
		return output;
	}

	public int getHeat() {
		return heat;
	}

}
