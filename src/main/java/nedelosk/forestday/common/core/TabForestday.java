package nedelosk.forestday.common.core;

import nedelosk.forestday.api.Tabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabForestday extends CreativeTabs {
	
	public static CreativeTabs tabForestdayBlocks = Tabs.tabForestdayBlocks = new TabForestday(0, "forestday.blocks");

	public static CreativeTabs tabForestdayItems = Tabs.tabForestdayItems = new TabForestday(1, "forestday.items");

	
	private final int tabIcon;
	public TabForestday(int tabIcon, String label) {
		super(label);
		this.tabIcon = tabIcon;
	}
	
	@Override
	public ItemStack getIconItemStack() {
		Item iconItem;
		switch (tabIcon) {
		case 1:
			iconItem = Item.getItemFromBlock(Blocks.brick_block);
			break;
		default:
			iconItem = Item.getItemFromBlock(Blocks.brick_block);
			break;
		}
		return new ItemStack(iconItem);
	}

	@Override
	public Item getTabIconItem() {
		return null;
	}

}
