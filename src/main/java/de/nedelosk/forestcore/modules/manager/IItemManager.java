package de.nedelosk.forestcore.modules.manager;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IItemManager extends IObjectManager<Item> {

	void register(Item object, Object... objects);

	boolean isItemEqual(ItemStack stack);

	boolean isItemEqual(Item item);

	Item item();
}
