package modularmachines.common.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import modularmachines.registry.ModItems;

public class CreativeTabModularMachines extends CreativeTabs {
	
	CreativeTabModularMachines() {
		super("modularmachines");
	}
	
	@Override
	public ItemStack createIcon() {
		return new ItemStack(ModItems.itemCasings);
	}
}
