package nedelosk.modularmachines.api.multiblocks;

import nedelosk.nedeloskcore.api.crafting.OreStack;
import net.minecraft.item.ItemStack;

public interface ICokeOvenRecipe {

	void addRecipe(int burnTime, ItemStack input, ItemStack output);
	
	void addRecipe(int burnTime, OreStack input, ItemStack output);
	
}
