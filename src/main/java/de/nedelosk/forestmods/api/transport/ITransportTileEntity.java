package de.nedelosk.forestmods.api.transport;

import net.minecraft.tileentity.TileEntity;

public abstract class ITransportTileEntity extends TileEntity {

	public abstract ITransportPart getTransportPart();

	public abstract void detachSelf(boolean chunkUnloading);
}
