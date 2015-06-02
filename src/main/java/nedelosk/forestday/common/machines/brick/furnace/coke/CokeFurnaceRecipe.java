package nedelosk.forestday.common.machines.brick.furnace.coke;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class CokeFurnaceRecipe {
	private ItemStack input;
	private ItemStack output1, output2, output;
	private FluidStack outputFluid;
	private String sInput;
	
	public CokeFurnaceRecipe(ItemStack input, ItemStack output, ItemStack  output1 , ItemStack  output2, FluidStack outputF){
		this.input = input;
		this.outputFluid = outputF;
		this.output = output;
		this.output1 = output1;
		this.output2 = output2;
	}
	
	public CokeFurnaceRecipe(String input, ItemStack output, ItemStack  output1 , ItemStack  output2, FluidStack outputFluid){
		this.sInput = input;
		this.outputFluid = outputFluid;
		this.output = output;
		this.output1 = output1;
		this.output2 = output2;
	}
	
	public ItemStack getInput(){
		if (this.input != null){
			return this.input;
		}
		ArrayList<ItemStack> tmp = OreDictionary.getOres(this.sInput);
		if (tmp.size() > 0){
			this.input = tmp.get(0);
		}
		return this.input;
	}
	
	public ItemStack getOutput(){
		return this.output.copy();
	}
	
	public ItemStack getOutput1(){
		if(output1 != null)
		{
		return this.output1.copy();
		}
		return null;
	}
	
	public ItemStack getOutput2(){
		if(output2 != null)
		{
		return this.output2.copy();
		}
		return null;
	}
	
	public FluidStack getOutputFluid(){
		return this.outputFluid.copy();
	}
	
}
