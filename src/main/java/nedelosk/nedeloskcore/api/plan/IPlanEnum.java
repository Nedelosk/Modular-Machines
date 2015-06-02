package nedelosk.nedeloskcore.api.plan;

import nedelosk.nedeloskcore.api.crafting.OreStack;
import net.minecraft.item.ItemStack;

public interface IPlanEnum {

	ItemStack[][] getInput();
	
	OreStack[][] getInputOre();
	
	ItemStack getOutput();
	
	int getBuildingStages();
	
}
