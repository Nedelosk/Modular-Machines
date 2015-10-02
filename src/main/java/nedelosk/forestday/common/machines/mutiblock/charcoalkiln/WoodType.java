package nedelosk.forestday.common.machines.mutiblock.charcoalkiln;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class WoodType {

	public ItemStack wood;
	public ArrayList<ItemStack> charcoalDropps = new ArrayList<ItemStack>();
	
	public WoodType(ItemStack wood, ItemStack... charcoalDropps) {
		this.wood = wood;
		for(ItemStack dropp : charcoalDropps)
		{
			this.charcoalDropps.add(dropp);
		}
	}
	
	public WoodType(ItemStack wood, ArrayList<ItemStack> charcoalDropps) {
		this.wood = wood;
		this.charcoalDropps = charcoalDropps;
	}
	
	public void writeToNBT(NBTTagCompound nbtTag)
	{
		NBTTagList tagList = new NBTTagList();
		for(ItemStack stackType : charcoalDropps)
		{
			NBTTagCompound nbt = new NBTTagCompound();
			stackType.writeToNBT(nbt);
			tagList.appendTag(nbt);
		}
		nbtTag.setTag("Dropps", tagList);
		NBTTagCompound nbt = new NBTTagCompound();
		wood.writeToNBT(nbt);
		nbtTag.setTag("Wood", nbt);
	}
	
	public void readFromNBT(NBTTagCompound nbtTag)
	{
		wood = ItemStack.loadItemStackFromNBT(nbtTag.getCompoundTag("Wood"));
		NBTTagList tagList = nbtTag.getTagList("Dropps", 10);
		for(int i = 0;i < tagList.tagCount();i++)
		{
			charcoalDropps.add(ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(i)));
		}
		
	}
	
	public static WoodType loadFromNBT(NBTTagCompound nbtTag)
	{
		ItemStack wood = ItemStack.loadItemStackFromNBT(nbtTag.getCompoundTag("Wood"));
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		NBTTagList tagList = nbtTag.getTagList("Dropps", 10);
		for(int i = 0;i < tagList.tagCount();i++)
		{
			list.add(ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(i)));
		}
		return new WoodType(ItemStack.loadItemStackFromNBT(nbtTag.getCompoundTag("Wood")), list);
	}
	
}
