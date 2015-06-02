package nedelosk.forestday.common.machines.iron.saw;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SawRecipe {
	
	private ItemStack input;
	private ItemStack output;
	
	public SawRecipe(ItemStack input, ItemStack output){
		this.input = input;
		this.output = output;
	}
	
	public ItemStack getInput(){
		return this.input;
	}
	
	public ItemStack getOutput(){
		return this.output.copy();
	}
	
}
