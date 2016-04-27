package de.nedelosk.forestmods.library.modules;

import de.nedelosk.forestmods.library.material.IMaterial;
import net.minecraft.item.ItemStack;

public interface IModuleContainer {

	Class<? extends IModule> getModuleClass();

	ItemStack getItemStack();

	void setItemStack(ItemStack stack);

	boolean ignorNBT();

	IMaterial getMaterial();

	ModuleUID getUID();

	@Override
	boolean equals(Object obj);

	IModuleContainer copy();
}