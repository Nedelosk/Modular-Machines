package nedelosk.forestday.common.blocks.tiles;

import java.util.ArrayList;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileCharcoalAsh extends TileMachineBase {

	public TileCharcoalAsh() {
		super(0);
	}
	
	public ArrayList<ItemStack> dropps;
	
	@Override
	public String getMachineName() {
		return null;
	}
	
	public void setDropps(ArrayList<ItemStack> dropps) {
		this.dropps = dropps;
	}

	@Override
	public Container getContainer(InventoryPlayer inventory) {
		return null;
	}

	@Override
	public Object getGUIContainer(InventoryPlayer inventory) {
		return null;
	}

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if(dropps != null)
		{
		NBTTagList list = new NBTTagList();
		for(int i = 0;i < dropps.size();i++)
		{
			NBTTagCompound nbtTag = new NBTTagCompound();
			dropps.get(i).writeToNBT(nbtTag);
			list.appendTag(nbtTag);
		}
		nbt.setTag("dropps", list);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if(nbt.hasKey("dropps"))
		{
		NBTTagList list = nbt.getTagList("dropps", 10);
		dropps = new ArrayList<ItemStack>(list.tagCount());
		for(int i = 0;i < list.tagCount();i++)
		{
			dropps.add(ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i)));
		}
		}
	}

}
