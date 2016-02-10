package de.nedelosk.forestmods.common.transport;

import de.nedelosk.forestmods.api.transport.ITransportPart;
import de.nedelosk.forestmods.api.transport.ITransportTileEntity;
import de.nedelosk.forestmods.api.transport.TransportRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntityTransport extends ITransportTileEntity {

	protected ITransportPart part;

	public TileEntityTransport() {
		part = new TransportPart(this);
	}

	@Override
	public ITransportPart getTransportPart() {
		return part;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
		readFromNBT(packet.func_148857_g());
	}

	@Override
	public void invalidate() {
		super.invalidate();
		detachSelf(false);
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		detachSelf(true);
	}

	@Override
	public void validate() {
		super.validate();
		TransportRegistry.onPartAdded(this.worldObj, getTransportPart());
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagCompound nbt = new NBTTagCompound();
		part.writeToNBT(nbt);
		compound.setTag("Part", nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagCompound nbt = compound.getCompoundTag("Part");
		part.readFromNBT(nbt);
	}

	@Override
	public void detachSelf(boolean chunkUnloading) {
		if (this.getTransportPart() != null && getTransportPart().getSystem() != null) {
			getTransportPart().getSystem().detachPart(getTransportPart(), chunkUnloading);
			getTransportPart().setSystem(null);
		}
		TransportRegistry.onPartRemovedFromWorld(worldObj, getTransportPart());
	}
}
