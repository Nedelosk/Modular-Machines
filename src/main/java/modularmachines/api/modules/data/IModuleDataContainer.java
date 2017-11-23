package modularmachines.api.modules.data;

import net.minecraft.item.ItemStack;

import modularmachines.common.modules.data.ModuleData;

/**
 * An {@link IModuleDataContainer}
 */
public interface IModuleDataContainer {
	
	/**
	 * @return The stack that is used to display this container in a gui.
	 */
	ItemStack getParent();
	
	/**
	 * @return The {@link ModuleData} that this container contains.
	 */
	IModuleData getData();
	
	boolean matches(ItemStack stack);
}
