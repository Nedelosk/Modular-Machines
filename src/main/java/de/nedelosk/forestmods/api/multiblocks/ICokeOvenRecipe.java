package de.nedelosk.forestmods.api.multiblocks;

import de.nedelosk.forestcore.utils.OreStack;
import net.minecraft.item.ItemStack;

public interface ICokeOvenRecipe {

	void addRecipe(int burnTime, ItemStack input, ItemStack output);

	void addRecipe(int burnTime, OreStack input, ItemStack output);
}
