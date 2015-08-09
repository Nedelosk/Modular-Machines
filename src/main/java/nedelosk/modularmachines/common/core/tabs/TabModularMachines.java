package nedelosk.modularmachines.common.core.tabs;

import nedelosk.modularmachines.common.core.MMBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class TabModularMachines extends CreativeTabs {

	public TabModularMachines() {
		super("modularmachines");
	}
	
	public static TabModularMachines instance = new TabModularMachines();

	@Override
	public Item getTabIconItem() {
		return MMBlocks.Modular_Assembler.item();
	}

}
