package nedelosk.forestday.structure.macerator.crafting;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class MaceratorRecipe {
	private ItemStack input;
	private ItemStack output;
	private String sInput;
	private int minRoughness, maxRoughness;
	private boolean isNEI;
	private int burnTime;
	
	public MaceratorRecipe(ItemStack input, ItemStack output, int minRoughness, int maxRoughness, int burnTime, boolean isNEI){
		this.input = input;
		this.output = output;
		this.minRoughness = minRoughness;
		this.maxRoughness = maxRoughness;
		this.isNEI = isNEI;
		this.burnTime = burnTime;
	}
	
	public MaceratorRecipe(String input, ItemStack output, int minRoughness, int maxRoughness, int burnTime , boolean isNEI){
		this.sInput = input;
		this.output = output;
		this.minRoughness = minRoughness;
		this.maxRoughness = maxRoughness;
		this.isNEI = isNEI;
		this.burnTime = burnTime;
	}
	
	public ItemStack getInput(){
		return this.input;
	}
	
	public String getOredictInput() {
		return sInput;
	}
	
	public ItemStack getOutput1(){
		return this.output.copy();
	}
	
	public int getMinRoughness()
	{
		return this.minRoughness;
	}
	
	public int getMaxRoughness()
	{
		return this.maxRoughness;
	}
	
	public boolean isNEI() {
		return isNEI;
	}
	
	public int getBurnTime() {
		return burnTime;
	}
	
}
