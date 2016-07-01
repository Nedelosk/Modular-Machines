package de.nedelosk.modularmachines.api.modular;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IModularHandlerTileEntity<M extends IModular, N extends NBTBase> extends IModularHandler<M, N> {

	EnumFacing getFacing();

	void setFacing(EnumFacing facing);

	BlockPos getPos();

}
