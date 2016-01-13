package nedelosk.forestcore.library.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class TileBase extends TileEntity {

	public boolean isWorking;
	public int burnTime;
	public int burnTimeTotal;

	@Override
	public void updateEntity() {
		super.updateEntity();
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
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata(), nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
		readFromNBT(packet.func_148857_g());
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
