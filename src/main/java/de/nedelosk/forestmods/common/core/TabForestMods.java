package de.nedelosk.forestmods.common.core;

import de.nedelosk.forestmods.api.Tabs;
import de.nedelosk.forestmods.api.utils.ModuleCategoryUIDs;
import de.nedelosk.forestmods.common.core.modules.ModuleCore.ItemManager;
import de.nedelosk.forestmods.common.core.modules.ModuleForestDay.BlockManager;
import de.nedelosk.forestmods.common.items.ItemModule;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabForestMods extends CreativeTabs {

	public static CreativeTabs tabForestMods = Tabs.tabForestMods = new TabForestMods(0, "forestmods");
	public static CreativeTabs tabComponents = Tabs.tabComponents = new TabForestMods(1, "forestmods.components");
	public static CreativeTabs tabModules = Tabs.tabModule = new TabForestMods(2, "forestmods.modules");
	private final int tabIcon;

	private TabForestMods(int tabIcon, String label) {
		super(label);
		this.tabIcon = tabIcon;
	}

	@Override
	public ItemStack getIconItemStack() {
		Item iconItem;
		int iconMeta;
		ItemStack stack = null;
		switch (tabIcon) {
			case 0:
				stack = new ItemStack(BlockManager.Machine.item(), 1, 2);
				break;
			case 1:
				stack = new ItemStack(ItemManager.Component_Gears.item(), 1, 5);
				break;
			case 2:
				stack = ItemModule.getItem(ModuleCategoryUIDs.MACHINE_ALLOY_SMELTER);
				break;
		}
		return stack;
	}

	@Override
	public Item getTabIconItem() {
		return null;
	}
}
