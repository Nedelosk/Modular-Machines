package nedelosk.forestday.common.core;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.modules.ModuleCore.BlockManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabForestDay extends CreativeTabs {

	public static CreativeTabs tabForestday = Tabs.tabForestday = new TabForestDay(0, "forestday");
	private final int tabIcon;

	public TabForestDay(int tabIcon, String label) {
		super(label);
		this.tabIcon = tabIcon;
	}

	@Override
	public ItemStack getIconItemStack() {
		Item iconItem;
		int iconMeta;
		switch (tabIcon) {
			default:
				iconItem = BlockManager.Machine.item();
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
