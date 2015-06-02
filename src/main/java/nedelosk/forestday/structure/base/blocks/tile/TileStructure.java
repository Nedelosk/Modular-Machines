package nedelosk.forestday.structure.base.blocks.tile;

import nedelosk.forestday.api.structure.tile.ITileStructure;
import nedelosk.nedeloskcore.common.blocks.tile.TileBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

public abstract class TileStructure extends TileBase implements  ITileStructure {

	private IIcon overayIcon;
	protected int maxHeat;
	protected String uid;
	
	public enum Structures
	{
		Air_Heater, Alloy_Smelter, Blast_Furnace, Macerator, Plant_Infuser, None
	}
	
	public TileStructure(int maxHeat, String uid)
	{
		this.maxHeat = maxHeat;
		this.uid = uid;
	}
	
	public IIcon getOverlayTexture()
	{
		return overayIcon;
	}
	
	public int getMaxHeat()
	{
		return maxHeat;
	}
	
	public String getUid()
	{
		return uid;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("MaxHeat", this.maxHeat);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.maxHeat = nbt.getInteger("MaxHeat");
	}
	
}
