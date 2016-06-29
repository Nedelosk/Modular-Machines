package de.nedelosk.modularmachines.api.modular;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IModularHandlerTileEntity extends IModularHandler {
	
	EnumFacing getFacing();

	void setFacing(EnumFacing facing);
	
	BlockPos getPos();
	
}
