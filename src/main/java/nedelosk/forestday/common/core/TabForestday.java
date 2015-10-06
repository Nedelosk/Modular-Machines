package nedelosk.forestday.common.core;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.managers.BlockManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabForestday extends CreativeTabs {
	
	public static CreativeTabs tabForestdayBlocks = Tabs.tabForestday = new TabForestday(0, "forestday");

	
	private final int tabIcon;
	public TabForestday(int tabIcon, String label) {
		super(label);
		this.tabIcon = tabIcon;
	}
	
	@Override
	public ItemStack getIconItemStack() {
		Item iconItem;
		int iconMeta;
		switch (tabIcon) {
		default:
			iconItem = BlockManager.Machine_Wood_Base.item();
			iconMeta = 2;
			break;
		}
		return new ItemStack(iconItem, 1, iconMeta);
	}

	@Override
	public Item getTabIconItem() {
		return null;
	}

}
