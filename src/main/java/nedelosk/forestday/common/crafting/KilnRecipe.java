package nedelosk.forestday.common.crafting;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class KilnRecipe {
	private ItemStack input1, input2;
	private ItemStack output1, output2;
	private String sInput1, sInput2;

	public KilnRecipe(ItemStack input1, ItemStack input2, ItemStack output1, ItemStack output2) {
		this.input1 = input1;
		this.input2 = input2;
		this.output1 = output1;
		this.output2 = output2;
	}

	public KilnRecipe(String input1, String input2, ItemStack output1, ItemStack output2) {
		this.sInput1 = input1;
		this.sInput2 = input2;
		this.output1 = output1;
		this.output2 = output2;
	}

	public KilnRecipe(String input1, ItemStack input2, ItemStack output1, ItemStack output2) {
		this.sInput1 = input1;
		this.input2 = input2;
		this.output1 = output1;
		this.output2 = output2;
	}

	public KilnRecipe(ItemStack input1, String input2, ItemStack output1, ItemStack output2) {
		this.input1 = input1;
		this.sInput2 = input2;
		this.output1 = output1;
		this.output2 = output2;
	}

	public ItemStack getInput1() {
		if (this.input1 != null) {
			return this.input1;
		}
		ArrayList<ItemStack> tmp = OreDictionary.getOres(this.sInput1);
		if (tmp.size() > 0) {
			this.input1 = tmp.get(0);
		}
		return this.input1;
	}

	public ItemStack getInput2() {
		if (this.input2 != null) {
			return this.input2;
		}
		ArrayList<ItemStack> tmp = OreDictionary.getOres(this.sInput2);
		if (tmp.size() > 0) {
			this.input2 = tmp.get(0);
		}
		return this.input2;
	}

	public ItemStack getOutput1() {
		return this.output1.copy();
	}

	public ItemStack getOutput2() {
		return this.output2.copy();
	}

}
