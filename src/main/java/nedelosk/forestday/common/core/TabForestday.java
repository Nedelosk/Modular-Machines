package nedelosk.forestday.common.core;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.registrys.ForestdayBlockRegistry;
import nedelosk.forestday.common.registrys.ForestdayItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabForestday extends CreativeTabs {
	
	public static CreativeTabs tabForestdayBlocks = Tabs.tabForestdayBlocks = new TabForestday(0, "forestday.blocks");
	
	public static CreativeTabs tabForestdayMultiBlocks = Tabs.tabForestdayMultiBlocks = new TabForestday(2, "forestday.multiblocks");

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
			iconItem = ForestdayItemRegistry.file;
			break;
		case 2:
			iconItem = Item.getItemFromBlock(ForestdayBlockRegistry.bricks);
			break;
		default:
			iconItem = Item.getItemFromBlock(ForestdayBlockRegistry.trunkBig);
			break;
		}
		return new ItemStack(iconItem);
	}

	@Override
	public Item getTabIconItem() {
		return null;
	}

}
