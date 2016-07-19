package de.nedelosk.modularmachines.api.modular.handlers;

import de.nedelosk.modularmachines.api.modular.IModular;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.math.BlockPos;

public interface IModularHandlerItem<M extends IModular, N extends NBTBase> extends IModularHandler<M, N> {

	String getUID();

	void setUID();

	BlockPos getPlayerPos();

	ItemStack getParent();
}
