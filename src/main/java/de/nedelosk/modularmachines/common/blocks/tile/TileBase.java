package de.nedelosk.modularmachines.common.blocks.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public abstract class TileBase extends TileEntity implements ITickable {

	public boolean isWorking;
	public int burnTime;
	public int burnTimeTotal;

	@Override
	public void update() {
		if (this.worldObj.isRemote) {
			updateClient();
		} else {
			updateServer();
		}
	}

	public abstract void updateClient();

	public abstract void updateServer();

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new SPacketUpdateTileEntity(pos, 0, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		worldObj.markBlockRangeForRenderUpdate(pos, pos);
		readFromNBT(packet.getNbtCompound());
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setTag("Base", new NBTTagCompound());
		nbt.getCompoundTag("Base").setBoolean("isWork", this.isWorking);
		nbt.getCompoundTag("Base").setInteger("burnTime", this.burnTime);
		nbt.getCompoundTag("Base").setInteger("burnTimeTotal", this.burnTimeTotal);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.isWorking = nbt.getCompoundTag("Base").getBoolean("isWork");
		this.burnTime = nbt.getCompoundTag("Base").getInteger("burnTime");
		this.burnTimeTotal = nbt.getCompoundTag("Base").getInteger("burnTimeTotal");
	}

	public int getScaledProcess(int i) {
		return (burnTimeTotal == 0) ? 0 : burnTime * i / burnTimeTotal;
	}

	public int getBurnTime() {
		return burnTime;
	}

	public int getBurnTimeTotal() {
		return burnTimeTotal;
	}
}
