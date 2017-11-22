package modularmachines.api.modules.components;

import net.minecraft.item.ItemStack;

public interface IItemCreationListener extends IModuleComponent {
	
	default ItemStack createItem(ItemStack itemStack) {
		return itemStack;
	}
}
