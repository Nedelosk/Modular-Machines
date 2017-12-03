package modularmachines.common.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTUtil {
	
	public static NBTTagCompound getCompound(ItemStack itemStack) {
		NBTTagCompound compound = itemStack.getTagCompound();
		if (itemStack.getTagCompound() == null) {
			itemStack.setTagCompound(compound = new NBTTagCompound());
		}
		return compound;
	}
	
	public static int getInteger(ItemStack itemStack, String key) {
		NBTTagCompound tagCompound = getCompound(itemStack);
		return tagCompound.getInteger(key);
	}
	
	public static byte getByte(ItemStack itemStack, String key) {
		NBTTagCompound tagCompound = getCompound(itemStack);
		return tagCompound.getByte(key);
	}
	
	public static NBTTagCompound getTag(ItemStack itemStack, String key) {
		NBTTagCompound tagCompound = getCompound(itemStack);
		return tagCompound.getCompoundTag(key);
	}
	
	
	public static ItemStack setInteger(ItemStack itemStack, String key, int value) {
		NBTTagCompound tagCompound = getCompound(itemStack);
		tagCompound.setInteger(key, value);
		return itemStack;
	}
	
	public static ItemStack setTag(ItemStack itemStack, String key, NBTTagCompound value) {
		NBTTagCompound tagCompound = getCompound(itemStack);
		tagCompound.setTag(key, value);
		return itemStack;
	}
	
	public static ItemStack setByte(ItemStack itemStack, String key, byte value) {
		NBTTagCompound tagCompound = getCompound(itemStack);
		tagCompound.setByte(key, value);
		return itemStack;
	}
}
