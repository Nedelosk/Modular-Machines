package nedelosk.forestday.structure.alloysmelter.crafting;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class AlloySmelterRecipe {
	private ItemStack input1, input2;
	private ItemStack output1;
	private String sInput1, sInput2;
	private int minHeat, maxHeat;
	
	public AlloySmelterRecipe(ItemStack input1, ItemStack input2, ItemStack output1, int minHeat, int maxHeat){
		this.input1 = input1;
		this.input2 = input2;
		this.output1 = output1;
		this.minHeat = minHeat;
		this.maxHeat = maxHeat;
	}
	
	public AlloySmelterRecipe(String input1, String input2, ItemStack output1, int minHeat, int maxHeat){
		this.sInput1 = input1;
		this.sInput2 = input2;
		this.output1 = output1;
		this.minHeat = minHeat;
		this.maxHeat = maxHeat;
	}
	
	public AlloySmelterRecipe(String input1, ItemStack input2, ItemStack output1, int minHeat, int maxHeat){
		this.sInput1 = input1;
		this.input2 = input2;
		this.output1 = output1;
		this.minHeat = minHeat;
		this.maxHeat = maxHeat;
	}
	
	public AlloySmelterRecipe(ItemStack input1, String input2, ItemStack output1, int minHeat, int maxHeat){
		this.input1 = input1;
		this.sInput2 = input2;
		this.output1 = output1;
		this.minHeat = minHeat;
		this.maxHeat = maxHeat;
	}
	
	public ItemStack getInput1(){
		if (this.input1 != null){
			return this.input1;
		}
		ArrayList<ItemStack> tmp = OreDictionary.getOres(this.sInput1);
		if (tmp.size() > 0){
			this.input1 = tmp.get(0);
		}
		return this.input1;
	}
	
	public ItemStack getInput2(){
		if (this.input2 != null){
			return this.input2;
		}
		ArrayList<ItemStack> tmp = OreDictionary.getOres(this.sInput2);
		if (tmp.size() > 0){
			this.input2 = tmp.get(0);
		}
		return this.input2;
	}
	
	public ItemStack getOutput1(){
		return this.output1.copy();
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
