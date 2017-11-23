package modularmachines.api.modules.components;

import net.minecraft.item.ItemStack;

/**
 * This component can be used to modify the item that drops if the module is extracted or a player breaks the block that
 * contains the {@link modularmachines.api.modules.container.IModuleContainer}.
 */
public interface IItemCreationListener extends IModuleComponent {
	
	default ItemStack createItem(ItemStack itemStack) {
		return itemStack;
	}
}
