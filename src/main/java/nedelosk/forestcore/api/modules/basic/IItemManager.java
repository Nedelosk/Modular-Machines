package nedelosk.forestcore.api.modules.basic;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IItemManager extends IObjectManager<Item> {
	
	boolean isItemEqual(ItemStack stack);

	boolean isItemEqual(Item item);
	
}
