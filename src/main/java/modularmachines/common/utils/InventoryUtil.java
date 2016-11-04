package modularmachines.common.utils;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryUtil {

	public static void readFromNBT(IInventory inventory, NBTTagCompound nbtTag) {
		if (!nbtTag.hasKey(inventory.getName())) {
			return;
		}
		NBTTagList nbttaglist = nbtTag.getTagList(inventory.getName(), 10);
		for(int j = 0; j < nbttaglist.tagCount(); ++j) {
			NBTTagCompound nbtTag2 = nbttaglist.getCompoundTagAt(j);
			int index = nbtTag2.getByte("Slot");
			inventory.setInventorySlotContents(index, ItemStack.loadItemStackFromNBT(nbtTag2));
		}
	}

	public static void writeToNBT(IInventory inventory, NBTTagCompound nbtTag) {
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			if (inventory.getStackInSlot(i) != null) {
				NBTTagCompound nbtTag2 = new NBTTagCompound();
				nbtTag2.setByte("Slot", (byte) i);
				inventory.getStackInSlot(i).writeToNBT(nbtTag2);
				nbttaglist.appendTag(nbtTag2);
			}
		}
		nbtTag.setTag(inventory.getName(), nbttaglist);
	}
}
