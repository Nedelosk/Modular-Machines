/**
 * (C) 2015 Nedelosk
 */
package nedelosk.forestday.api.structure.crafting;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class BrokenItemStack {

	public BrokenItemStack(ItemStack input, ItemStack output, int minRoughness, int maxRoughness) {
		this.output = output;
		this.input = input;
		this.maxRoughness = maxRoughness;
		this.minRoughness = minRoughness;
	}
	
	public BrokenItemStack(String input, ItemStack output, int minRoughness, int maxRoughness) {
		this.output = output;
		this.sInput = input;
		this.maxRoughness = maxRoughness;
		this.minRoughness = minRoughness;
	}
	
	private ItemStack input;
	private String sInput;
	private ItemStack output;
	private int minRoughness;
	private int maxRoughness;
	
	public ItemStack getInput() {
		return input;
	}
	
	public ItemStack getOutput() {
		return output;
	}
	
	public int getMaxRoughness() {
		return maxRoughness;
	}
	
	public int getMinRoughness() {
		return minRoughness;
	}
	
	public String getOreDict() {
		return sInput;
	}
	
}
