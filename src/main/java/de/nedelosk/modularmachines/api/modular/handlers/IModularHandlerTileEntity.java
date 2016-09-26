package de.nedelosk.modularmachines.api.modular.handlers;

import net.minecraft.nbt.NBTBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IModularHandlerTileEntity<N extends NBTBase> extends IModularHandler<N> {

	EnumFacing getFacing();

	void setFacing(EnumFacing facing);

	BlockPos getPos();

	TileEntity getTile();

	void invalidate();

}
