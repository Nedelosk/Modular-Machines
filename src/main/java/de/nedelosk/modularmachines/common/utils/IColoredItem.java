package de.nedelosk.modularmachines.common.utils;

import net.minecraft.item.ItemStack;

public interface IColoredItem {

	int getColorFromItemstack(ItemStack stack, int tintIndex);
}
