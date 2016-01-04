package nedelosk.modularmachines.common.core;

import nedelosk.modularmachines.modules.ModuleCore;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabModularMachines extends CreativeTabs {

	public static CreativeTabs core = new TabModularMachines(0, "modularmachines");
	public static CreativeTabs components = new TabModularMachines(1, "modularmachines.components");

	private final int tabIcon;

	public TabModularMachines(int tabIcon, String label) {
		super(label);
		this.tabIcon = tabIcon;
	}

	@Override
	public ItemStack getIconItemStack() {
		Item iconItem;
		int iconMeta;
		switch (tabIcon) {
		case 0:
			iconItem = ModuleCore.ItemManager.Component_Gears.item();
			iconMeta = 5;
			break;
		case 1:
			iconItem = ModuleCore.ItemManager.Component_Gears.item();
			iconMeta = 3;
			break;
		default:
			iconItem = Items.apple;
			iconMeta = 0;
		}
		return new ItemStack(iconItem, 1, iconMeta);
	}

	@Override
	public Item getTabIconItem() {
		return null;
	}

}
