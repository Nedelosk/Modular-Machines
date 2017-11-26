package modularmachines.common.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import modularmachines.common.core.managers.ModItems;

public class CreativeTabModularMachines extends CreativeTabs {
	
	CreativeTabModularMachines() {
		super("modularmachines");
	}
	
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.itemCasings);
	}
}
