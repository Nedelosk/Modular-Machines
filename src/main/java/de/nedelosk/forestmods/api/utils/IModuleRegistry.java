package de.nedelosk.forestmods.api.utils;

import java.util.Map;

import de.nedelosk.forestmods.api.material.IMaterial;
import de.nedelosk.forestmods.api.modules.IModule;
import net.minecraft.item.ItemStack;

public interface IModuleRegistry {

	<M extends IModule> M registerModule(IMaterial material, ModuleUID UID, Class<? extends M> moduleClass, Object... objects);

	Class<? extends IModule> getModule(IMaterial material, ModuleUID UID);

	<M extends IModule> M createModule(IMaterial material, ModuleUID UID);

	Map<IMaterial, Map<ModuleUID, ModuleType>> getModules();

	void registerItemForModule(ItemStack itemStack, ModuleStack moduleStack);

	void registerItemForModule(ItemStack itemStack, ModuleStack moduleStack, boolean ignorNBT);

	<M extends IModule> M registerModuleAndItem(IMaterial material, ModuleUID UID, ItemStack itemStack, Class<? extends M> moduleClass, boolean ignorNBT,
			Object... objects);

	<M extends IModule> ItemStack createDefaultModuleItem(ModuleStack<M> moduleStack);

	ModuleStack getModuleFromItem(ItemStack stack);
}
