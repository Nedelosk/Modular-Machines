/*
 * Copyright (c) 2017 Nedelosk
 *
 * This work (the MOD) is licensed under the "MIT" License, see LICENSE for details.
 */
package modularmachines.common.modules.transfer;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public interface IModuleTransfer<H> {
	
	NBTBase writeWrapper(ITransferHandlerWrapper<H> wrapper);
	
	ITransferHandlerWrapper<H> getWrapper(NBTBase base);
	
	ITransferCycle<H> getCycle(NBTTagCompound compound);
	
	Class<H> getHandlerClass();
	
	H getHandler(ITransferHandlerWrapper<H> wrapper);
	
	boolean isValid(ITransferHandlerWrapper<H> wrapper);
	
	ITransferHandlerWrapper<H> createModuleWrapper(int moduleIndex);
	
	ITransferHandlerWrapper<H> createTileWrapper(EnumFacing facing);
}
