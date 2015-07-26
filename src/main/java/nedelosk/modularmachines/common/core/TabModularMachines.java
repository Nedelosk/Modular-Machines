package nedelosk.modularmachines.common.core;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.registrys.BlockRegistry;
import nedelosk.forestday.common.registrys.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabModularMachines extends CreativeTabs {

	public TabModularMachines() {
		super("modularmachines");
	}
	
	public static TabModularMachines instance = new TabModularMachines();

	@Override
	public Item getTabIconItem() {
		return Items.arrow;
	}

}
