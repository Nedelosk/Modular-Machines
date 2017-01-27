package modularmachines.common.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import modularmachines.common.core.managers.ItemManager;
import modularmachines.common.materials.EnumMaterial;

public class TabModularMachines extends CreativeTabs {

	public static CreativeTabs tabModularMachines = new TabModularMachines(0, "modularmachines");
	public static CreativeTabs tabModules = new TabModularMachines(1, "modularmachines.modules");
	private final int tabIcon;

	private TabModularMachines(int tabIcon, String label) {
		super(label);
		this.tabIcon = tabIcon;
	}

	@Override
	public ItemStack getTabIconItem() {
		ItemStack stack = ItemStack.EMPTY;
		switch (tabIcon) {
			case 0:
				stack = ItemManager.itemCompPlates.getStack(EnumMaterial.STEEL);
				break;
			case 1:
				stack = new ItemStack(ItemManager.itemCasings, 1, 0);
				break;
		}
		return stack;
	}
}
