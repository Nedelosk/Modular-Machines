package de.nedelosk.modularmachines.api.modular.handlers;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IModularHandlerTileEntity<N extends NBTBase, K> extends IModularHandler<N, K> {

	EnumFacing getFacing();

	void setFacing(EnumFacing facing);

	BlockPos getPos();

	void setTile(TileEntity tileEntity);

	@Nullable
	TileEntity getTile();

	void invalidate();
}
