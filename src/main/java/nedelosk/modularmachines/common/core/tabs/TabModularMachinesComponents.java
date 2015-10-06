package nedelosk.modularmachines.common.core.tabs;

import nedelosk.modularmachines.common.core.MMBlocks;
import nedelosk.modularmachines.common.core.MMItems;
import nedelosk.modularmachines.common.core.MMRegistry;
import nedelosk.modularmachines.common.modular.utils.MaterialManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabModularMachinesComponents extends CreativeTabs {

	public TabModularMachinesComponents() {
		super("modularmachines.components");
	}
	
	public static TabModularMachinesComponents instance = new TabModularMachinesComponents();

	@Override
	public Item getTabIconItem() {
		return MMBlocks.Modular_Assembler.item();
	}
	
	@Override
	public ItemStack getIconItemStack() {
		ItemStack stack = new ItemStack(MMItems.Component_Gears.item());
		MaterialManager.setMaterial(stack, MMRegistry.Steel);
		return stack;
	}

}
