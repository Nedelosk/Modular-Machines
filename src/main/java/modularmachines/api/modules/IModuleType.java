package modularmachines.api.modules;

import net.minecraft.item.ItemStack;

/**
 * Every {@link IModuleType} contains one {@link ItemStack} that represents one {@link IModuleData}.
 */
public interface IModuleType {
	/**
	 * @return The stack that represents the data.
	 */
	ItemStack getItem();
	
	/**
	 * @return The data that the item represents.
	 */
	IModuleData getData();
	
	/**
	 * Checks if the given item is a equivalent of the item of this type.
	 */
	boolean matches(ItemStack stack);
}
