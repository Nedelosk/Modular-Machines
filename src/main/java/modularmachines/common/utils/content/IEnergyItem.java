package modularmachines.common.utils.content;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IEnergyItem {

	int getCapacity(ItemStack itemStack);

	Item getItem();
}
