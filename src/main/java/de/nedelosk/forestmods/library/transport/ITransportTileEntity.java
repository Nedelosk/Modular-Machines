package de.nedelosk.forestmods.library.transport;

import net.minecraft.tileentity.TileEntity;

public abstract class ITransportTileEntity extends TileEntity {

	public abstract ITransportPart getPart();

	public abstract void detachSelf(boolean chunkUnloading);
}
