package de.nedelosk.forestmods.api.utils;

import de.nedelosk.forestmods.api.material.IMaterial;
import de.nedelosk.forestmods.api.modules.IModule;
import net.minecraft.item.ItemStack;

public interface IModuleRegistry {

	<M extends IModule> M registerModule(IMaterial material, String category, M module);

	IModule getModule(IMaterial material, ModuleUID UID);

	void registerItemForModule(ItemStack itemStack, ModuleStack moduleStack);

	void registerItemForModule(ItemStack itemStack, ModuleStack moduleStack, boolean ignorNBT);

	ModuleStack getModuleFromItem(ItemStack stack);
}
