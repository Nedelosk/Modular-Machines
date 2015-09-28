package nedelosk.nedeloskcore.api.plan;

import net.minecraft.item.ItemStack;

public class PlanRecipe {
	
	public ItemStack[][] input;
	public ItemStack outputBlock;
	public int stages;
	public ItemStack updateBlock;
	
	public PlanRecipe(ItemStack outputBlock, int stages, ItemStack[]... input) {
		this.outputBlock = outputBlock;
		this.stages = stages;
		this.input = input;
	}
	
	public PlanRecipe(ItemStack updateBlock, ItemStack outputBlock, int stages, ItemStack[]... input) {
		this.updateBlock = updateBlock;
		this.outputBlock = outputBlock;
		this.stages = stages;
		this.input = input;
	}
	
}
