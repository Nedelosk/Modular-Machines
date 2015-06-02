package nedelosk.forestday.structure.base.blocks.tile;

import nedelosk.forestday.api.structure.tile.ITileStructure;
import nedelosk.nedeloskcore.common.blocks.tile.TileBase;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

public abstract class TileStructureInventory extends TileBaseInventory implements ITileStructure {

	private IIcon overayIcon;
	private String name;
	protected int maxHeat;
	protected String uid;
	
	public TileStructureInventory(int maxHeat, int slots, String uid)
	{
		super(slots);
		this.maxHeat = maxHeat;
		this.uid = uid;
	}
	
	public enum Structures
	{
		Air_Heater, Alloy_Smelter, Blast_Furnace, Macerator, Plant_Infuser, None
	}
	
	public IIcon getOverlayTexture()
	{
		return overayIcon;
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
	
	
	public int getMaxHeat()
	{
		return maxHeat;
	}
	
	public String getUid()
	{
		return uid;
	}
	
}
