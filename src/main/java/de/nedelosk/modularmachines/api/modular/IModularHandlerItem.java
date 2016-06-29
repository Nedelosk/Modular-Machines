package de.nedelosk.modularmachines.api.modular;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.world.World;

public interface IModularHandlerItem<M extends IModular, N extends NBTBase> extends IModularHandler<M, N> {
	
	void setWorld(World world);
	
	int getUID();
	
	ItemStack getParent();
}
