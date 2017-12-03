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
	
	public static ItemStack setInteger(ItemStack itemStack, String key, int value) {
		NBTTagCompound tagCompound = getCompound(itemStack);
		tagCompound.setInteger(key, value);
		return itemStack;
	}
	
	public static ItemStack setByte(ItemStack itemStack, String key, byte value) {
		NBTTagCompound tagCompound = getCompound(itemStack);
		tagCompound.setByte(key, value);
		return itemStack;
	}
}
