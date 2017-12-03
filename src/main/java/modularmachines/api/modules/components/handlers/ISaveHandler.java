package modularmachines.api.modules.components.handlers;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.components.IModuleComponent;

/**
 * This can be used to save an load data like the content of an tank from and to the NBT-Data.
 */
public interface ISaveHandler<C extends IModuleComponent> {
	
	void writeToItem(C component, ItemStack itemStack);
	
	void readFromItem(C component, ItemStack itemStack);
}
