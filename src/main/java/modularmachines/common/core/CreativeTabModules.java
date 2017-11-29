package modularmachines.common.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModuleType;
import modularmachines.api.modules.ModuleManager;
import modularmachines.common.items.ModuleItems;

public class CreativeTabModules extends CreativeTabs {
	CreativeTabModules() {
		super("modularmachines.modules");
	}
	
	@Override
	public ItemStack getTabIconItem() {
		return ModuleItems.LARGE_TANK.get();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> itemStacks) {
		NonNullList<ItemStack> items = NonNullList.create();
		for (CreativeTabs creativeTab : CreativeTabs.CREATIVE_TAB_ARRAY) {
			if (creativeTab == CreativeTabs.HOTBAR || creativeTab == this || creativeTab == SEARCH) {
				continue;
			}
			try {
				creativeTab.displayAllRelevantItems(items);
			} catch (RuntimeException | LinkageError e) {
				e.printStackTrace();
			}
		}
		for (ItemStack itemStack : items) {
			IModuleType type = ModuleManager.registry.getTypeFromItem(itemStack);
			if (type != null) {
				itemStacks.add(itemStack);
			}
		}
	}
}
