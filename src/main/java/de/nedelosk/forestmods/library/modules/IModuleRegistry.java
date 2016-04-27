package de.nedelosk.forestmods.library.modules;

import java.util.List;
import java.util.Map;

import de.nedelosk.forestmods.library.material.IMaterial;
import de.nedelosk.forestmods.library.modular.IModular;
import net.minecraft.item.ItemStack;

public interface IModuleRegistry {

	<M extends IModule> boolean registerModule(IMaterial material, ModuleUID UID, Class<? extends IModule> moduleClass, Object... objects);

	Class<? extends IModule> getModule(IMaterial material, ModuleUID UID);

	<M extends IModule> M createModule(IModular modular, IModuleContainer container);

	/**
	 * For the module item rendering or other  module item thinks.
	 */
	<M extends IModule> M createFakeModule(IModuleContainer container);

	void registerItemForModule(ItemStack itemStack, IModuleContainer moduleContainer);

	void registerItemForModule(ItemStack itemStack, IModuleContainer moduleContainer, boolean ignorNBT);

	<M extends IModule> boolean registerModuleAndItem(IMaterial material, ModuleUID UID, ItemStack itemStack, Class<? extends M> moduleClass, boolean ignorNBT,
			Object... objects);

	ItemStack createDefaultModuleItem(IModuleContainer moduleContainer);

	IModuleContainer getModuleFromItem(ItemStack stack);

	Map<IMaterial, Map<ModuleUID, ModuleType>> getModules();

	List<IModuleContainer> getModuleContainers();
}
