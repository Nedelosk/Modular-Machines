package de.nedelosk.forestmods.library.multiblocks;

import de.nedelosk.forestmods.library.utils.OreStack;
import net.minecraft.item.ItemStack;

public interface ICokeOvenRecipe {

	void addRecipe(int burnTime, ItemStack input, ItemStack output);

	void addRecipe(int burnTime, OreStack input, ItemStack output);
}
