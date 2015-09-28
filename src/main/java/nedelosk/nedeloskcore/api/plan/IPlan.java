package nedelosk.nedeloskcore.api.plan;

import nedelosk.nedeloskcore.api.crafting.OreStack;
import net.minecraft.item.ItemStack;

public interface IPlan {

	ItemStack[] getInput(ItemStack stack, int stage);
	
	OreStack[] getInputOre(ItemStack stack, int stage);
	
	ItemStack getOutput(ItemStack stack);
	
	int getBuildingStages(ItemStack stack);
	
}
