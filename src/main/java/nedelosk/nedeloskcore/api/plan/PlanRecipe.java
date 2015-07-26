package nedelosk.nedeloskcore.api.plan;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

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
