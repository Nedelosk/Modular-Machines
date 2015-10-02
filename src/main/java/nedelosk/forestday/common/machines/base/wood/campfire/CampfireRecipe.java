package nedelosk.forestday.common.machines.base.wood.campfire;

import net.minecraft.item.ItemStack;

public class CampfireRecipe {
	
	private ItemStack input;
	private ItemStack input2;
	private ItemStack output;
	private int burnTime;
	private int potTier;
	
	public CampfireRecipe(ItemStack input, ItemStack input2, ItemStack output, int potTier, int burnTime){
		this.input = input;
		this.input2 = input2;
		this.output = output;
		this.burnTime = burnTime;
		this.potTier = potTier;
	}
	
	public CampfireRecipe(ItemStack input, ItemStack output, int potTier, int burnTime){
		this.input = input;
		this.output = output;
		this.burnTime = burnTime;
		this.potTier = potTier;
	}
	
	public ItemStack getOutput() {
		return output;
	}
	
	public ItemStack getInput() {
		return input;
	}
	
	public ItemStack getInput2() {
		return input2;
	}
	
	public int getPotTier() {
		return potTier;
	}
	
	public int getBurnTime() {
		return burnTime;
	}
	
}
