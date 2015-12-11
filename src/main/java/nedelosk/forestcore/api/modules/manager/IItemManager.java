package nedelosk.forestcore.api.modules.manager;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IItemManager extends IObjectManager<Item> {
	
	boolean isItemEqual(ItemStack stack);

	boolean isItemEqual(Item item);
	
	Item item();
	
}
