package de.nedelosk.forestmods.common.transport;

import de.nedelosk.forestmods.library.transport.ITransportPart;
import de.nedelosk.forestmods.library.transport.ITransportTileEntity;
import de.nedelosk.forestmods.library.transport.TransportRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntityTransport extends ITransportTileEntity {

	protected ITransportPart part;

	public TileEntityTransport() {
		part = createPart();
	}

	protected ITransportPart createPart() {
		return new TransportPart(this);
	}

	@Override
	public ITransportPart getPart() {
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
		if (getPart().getSides() == null) {
			getPart().createSides();
		}
		TransportRegistry.onPartAdded(this.worldObj, getPart());
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagCompound nbt = new NBTTagCompound();
		getPart().writeToNBT(nbt);
		compound.setTag("Part", nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagCompound nbt = compound.getCompoundTag("Part");
		getPart().readFromNBT(nbt);
	}

	@Override
	public void detachSelf(boolean chunkUnloading) {
		if (this.getPart() != null && getPart().getSystem() != null) {
			getPart().getSystem().detachPart(getPart(), chunkUnloading);
			getPart().setSystem(null);
		}
		TransportRegistry.onPartRemovedFromWorld(worldObj, getPart());
	}
}
