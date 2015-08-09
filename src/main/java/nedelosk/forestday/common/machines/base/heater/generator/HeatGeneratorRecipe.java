package nedelosk.forestday.common.machines.base.heater.generator;

import nedelosk.forestday.api.crafting.BurningMode;
import net.minecraft.item.ItemStack;

public class HeatGeneratorRecipe {
	private ItemStack input1;
	private ItemStack output;
	private String sInput1;
	private int burnTime;
	private BurningMode mode;
	
	public HeatGeneratorRecipe(ItemStack input1, ItemStack output, int burnTime, BurningMode mode){
		this.input1 = input1;
		this.output = output;
		this.burnTime = burnTime;
		this.mode = mode;
	}
	
	public HeatGeneratorRecipe(String input1, ItemStack output, int burnTime, BurningMode mode){
		this.sInput1 = input1;
		this.output = output;
		this.burnTime = burnTime;
		this.mode = mode;
	}
	
	public ItemStack getInput1(){
		return this.input1;
	}
	
	public String getOredictInput() {
		return sInput1;
	}
	
	public int getBurnTime()
	{
		if(this.burnTime != 0)
		{
			return this.burnTime;
		}
		return this.burnTime;
	}
	
	public ItemStack getOutput(){
		return this.output.copy();
	}
	
	public BurningMode getMode() {
		return mode;
	}
	
}
