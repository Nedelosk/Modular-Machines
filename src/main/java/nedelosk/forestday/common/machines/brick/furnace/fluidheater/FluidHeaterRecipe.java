package nedelosk.forestday.common.machines.brick.furnace.fluidheater;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class FluidHeaterRecipe {
	private FluidStack input, input2;
	private FluidStack output;
	private ItemStack inputItem;
	private ItemStack outputItem;
	private int burnTime;
	
	public FluidHeaterRecipe(FluidStack input, FluidStack input2, FluidStack  output, int burnTime){
		this.input = input;
		this.input2 = input2;
		this.output = output;
		this.burnTime = burnTime;
	}
	
	public FluidHeaterRecipe(ItemStack input, FluidStack input2, FluidStack  output, int burnTime){
		this.inputItem = input;
		this.input2 = input2;
		this.output = output;
		this.burnTime = burnTime;
	}
	public FluidHeaterRecipe(ItemStack input, FluidStack input2, ItemStack  output, int burnTime){
		this.inputItem = input;
		this.input2 = input2;
		this.outputItem = output;
		this.burnTime = burnTime;
	}
	
	public FluidHeaterRecipe(FluidStack input, FluidStack input2, ItemStack  output, int burnTime){
		this.input = input;
		this.input2 = input2;
		this.outputItem = output;
		this.burnTime = burnTime;
	}
	
	public FluidHeaterRecipe(FluidStack input, FluidStack  output, int burnTime){
		this.input = input;
		this.output = output;
		this.burnTime = burnTime;
	}
	
	public FluidHeaterRecipe(ItemStack input, FluidStack  output, int burnTime){
		this.inputItem = input;
		this.output = output;
		this.burnTime = burnTime;
	}
	
	public FluidHeaterRecipe(FluidStack input, ItemStack  output, int burnTime){
		this.input = input;
		this.outputItem = output;
		this.burnTime = burnTime;
	}
	
	public FluidStack getInput(){
		return this.input;
	}
	
	public FluidStack getInput2(){
		return this.input2;
	}
	
	public FluidStack getOutput(){
		return this.output.copy();
	}
	
	public int getBurnTime() {
		return burnTime;
	}
	
	public ItemStack getInputItem() {
		return inputItem;
	}
	
	public ItemStack getOutputItem() {
		return outputItem;
	}
	
}
