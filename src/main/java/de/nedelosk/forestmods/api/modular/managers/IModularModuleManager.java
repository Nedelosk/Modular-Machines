package de.nedelosk.forestmods.api.modular.managers;

import java.util.List;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.producers.IModule;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import net.minecraft.item.ItemStack;

public interface IModularModuleManager<M extends IModular> extends IModularManager<M> {

	boolean addModule(ItemStack itemStack, ModuleStack stack);

	List<ModuleStack> getModuleSatcks(Class<? extends IModule> moduleClass);

	ModuleStack getModuleStack(ModuleUID moduleUID);

	ItemStack getItemStack(ModuleUID UID);

	/**
	 * @return All modules as ModuleStack
	 */
	List<ModuleStack> getModuleStacks();
}
