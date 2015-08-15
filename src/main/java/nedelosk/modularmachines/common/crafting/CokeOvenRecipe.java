package nedelosk.modularmachines.common.crafting;

import nedelosk.nedeloskcore.api.crafting.OreStack;
import net.minecraft.item.ItemStack;

public class CokeOvenRecipe {
	private Object input;
	private ItemStack output;
	private int burntTime;
	
	public CokeOvenRecipe(int burnTime, ItemStack input, ItemStack output){
		this.input = input;
		this.output = output;
		this.burntTime = burnTime;
	}
	
	public CokeOvenRecipe(int burnTime, OreStack input, ItemStack output){
		this.input = input;
		this.output = output;
		this.burntTime = burnTime;
	}
	
	public int getBurntTime() {
		return burntTime;
	}
	
	public Object getInput() {
		return input;
	}
	
	public ItemStack getOutput() {
		return output;
	}
	
}
