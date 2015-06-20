package nedelosk.forestday.api.crafting;

import nedelosk.forestday.common.machines.base.wood.campfire.CampfireRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface ICampfireRecipe {

	void addRecipe(ItemStack input, ItemStack input2, ItemStack output, int potTier, int burnTime);
	
	void addRecipe(ItemStack input, ItemStack output, int potTier, int burnTime);
	
}
