package nedelosk.forestday.structure.blastfurnace.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class BlastFurnaceRecipe {
	private ItemStack[] input = new ItemStack[4];
	private FluidStack[] output = new FluidStack[2];
	private int minHeat, maxHeat;
	
	public BlastFurnaceRecipe(ItemStack[] input, FluidStack[] output, int minHeat, int maxHeat){
		this.input = input;
		this.output = output;
		this.minHeat = minHeat;
		this.maxHeat = maxHeat;
	}
	
	public ItemStack[] getInput(){
		return this.input;
	}
	
	public FluidStack[] getOutput(){
		return this.output.clone();
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
