package de.nedelosk.modularmachines.api.modular.handlers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.math.BlockPos;

public interface IModularHandlerItem<N extends NBTBase, K> extends IModularHandler<N, K> {

	String getUID();

	void setUID();

	BlockPos getPlayerPos();

	ItemStack getParent();
}
