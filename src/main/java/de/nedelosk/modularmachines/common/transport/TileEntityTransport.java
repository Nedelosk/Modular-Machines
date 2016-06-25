package de.nedelosk.modularmachines.common.transport;

import de.nedelosk.modularmachines.api.transport.ITransportPart;
import de.nedelosk.modularmachines.api.transport.ITransportTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;

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
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new SPacketUpdateTileEntity(getPos(), 0, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		worldObj.markBlockRangeForRenderUpdate(getPos(), getPos());
		readFromNBT(packet.getNbtCompound());
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
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagCompound nbt = new NBTTagCompound();
		getPart().writeToNBT(nbt);
		compound.setTag("Part", nbt);
		return compound;
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
