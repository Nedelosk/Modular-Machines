package de.nedelosk.forestmods.api.utils;

import net.minecraft.item.ItemStack;

public interface IModuleHandler {

	ItemStack getStack();

	boolean ignorNBT();

	ModuleStack getModuleStack();

	@Override
	boolean equals(Object obj);

	IModuleHandler copy();
}