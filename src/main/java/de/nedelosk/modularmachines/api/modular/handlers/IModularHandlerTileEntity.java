package de.nedelosk.modularmachines.api.modular.handlers;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IModularHandlerTileEntity<M extends IModular, A extends IModularAssembler, N extends NBTBase> extends IModularHandler<M, A, N> {

	EnumFacing getFacing();

	void setFacing(EnumFacing facing);

	BlockPos getPos();

}
