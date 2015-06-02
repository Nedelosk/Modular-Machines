package nedelosk.forestday.structure.old;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class Tile extends TileEntity
{
	/* Where is the front direction of this TileEntity*/
	private int frontDirection;
	private int meta;
	
	public Tile(){
		super();
	}
	
	@Override
	public void updateEntity(){
		super.updateEntity();
		if (this.worldObj.isRemote)
			return;
		
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		
	}
	
	public void setFrontDirection(int frontDirection){
		this.frontDirection = frontDirection;
	}
	
	public ForgeDirection getFrontDirection(){
		return ForgeDirection.getOrientation(this.frontDirection);
	}

	public void setMeta(int meta){
		this.meta = meta;
	}
	
	public int getMeta(){
		return this.meta;
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tagCom = new NBTTagCompound();
		this.writeToNBT(tagCom);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, this.blockMetadata, tagCom);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		NBTTagCompound tagCom = pkt.func_148857_g();
		this.readFromNBT(tagCom);
	}
	

	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt); 
		nbt.setInteger("frontDirection", this.frontDirection);
		nbt.setInteger("meta", this.meta);
	}
	

	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt); 
		this.frontDirection = nbt.getInteger("frontDirection");
		this.meta = nbt.getInteger("meta");
	}
	


	
}
