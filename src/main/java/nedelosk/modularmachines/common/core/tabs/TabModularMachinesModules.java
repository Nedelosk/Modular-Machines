package nedelosk.modularmachines.common.core.tabs;

import nedelosk.modularmachines.common.core.MMItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TabModularMachinesModules extends CreativeTabs {

	public TabModularMachinesModules() {
		super("modularmachines.modules");
	}
	
	public static TabModularMachinesModules instance = new TabModularMachinesModules();
	
	@Override
	public ItemStack getIconItemStack() {
		ItemStack stack = new ItemStack(MMItems.Module_Items.item(), 1, 0);
		NBTTagCompound nbtTag = new NBTTagCompound();
		nbtTag.setString("Name", "moduleStorageManager");
		nbtTag.setInteger("Tier", 2);
		stack.setTagCompound(nbtTag);
		return stack;
	}

	@Override
	public Item getTabIconItem() {
		return null;
	}

}
