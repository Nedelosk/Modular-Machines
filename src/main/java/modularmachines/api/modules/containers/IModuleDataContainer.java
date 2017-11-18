package modularmachines.api.modules.containers;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.ModuleData;

/**
 * An IModuleContainer provides information about modules.
 */
public interface IModuleDataContainer {
	
	/**
	 * @return The stack that is used to display this container in a gui.
	 */
	ItemStack getParent();
	
	/**
	 * @return The {@link ModuleData} that this container contains.
	 */
	ModuleData getData();
	
	boolean matches(ItemStack stack);
}
