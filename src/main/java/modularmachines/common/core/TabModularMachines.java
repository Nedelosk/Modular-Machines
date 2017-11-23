package modularmachines.common.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import modularmachines.common.core.managers.ItemManager;

public class TabModularMachines extends CreativeTabs {
	
	public static CreativeTabs tabModularMachines = new TabModularMachines();
	
	private TabModularMachines() {
		super("modularmachines");
	}
	
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ItemManager.itemCasings, 1, 1);
	}
}
