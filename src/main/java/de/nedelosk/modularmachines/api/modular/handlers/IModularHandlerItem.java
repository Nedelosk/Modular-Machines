package de.nedelosk.modularmachines.api.modular.handlers;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.math.BlockPos;

public interface IModularHandlerItem<M extends IModular, A extends IModularAssembler, N extends NBTBase> extends IModularHandler<M, A, N> {

	String getUID();

	void setUID();

	BlockPos getPlayerPos();

	ItemStack getParent();
}
