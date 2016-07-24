package de.nedelosk.modularmachines.common.core;

import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.common.items.ItemModule;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabModularMachines extends CreativeTabs {

	public static CreativeTabs tabModularMachines = new TabModularMachines(0, "modularmachines");
	public static CreativeTabs tabModules = new TabModularMachines(1, "modularmachines.modules");
	private final int tabIcon;

	private TabModularMachines(int tabIcon, String label) {
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
				stack = new ItemStack(ItemManager.itemCasings, 1, 0);
				break;
			case 1:
				stack = ItemModule.getItem(ModuleManager.moduleBoilerBronze.getRegistryName(), EnumMetalMaterials.BRONZE);
				break;
		}
		return stack;
	}

	@Override
	public Item getTabIconItem() {
		return null;
	}
}
