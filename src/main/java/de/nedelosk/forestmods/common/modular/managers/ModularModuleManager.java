package de.nedelosk.forestmods.common.modular.managers;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.forestmods.api.modular.managers.DefaultModularManager;
import de.nedelosk.forestmods.api.modular.managers.IModularModuleManager;
import de.nedelosk.forestmods.api.producers.IModule;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import net.minecraft.item.ItemStack;

public class ModularModuleManager extends DefaultModularManager implements IModularModuleManager {

	private final List<ModuleStack> moduleStacks = new ArrayList();
	private final List<ItemStack> itemStacks = new ArrayList();

	public ModularModuleManager() {
	}

	@Override
	public boolean addModule(ItemStack itemStack, ModuleStack stack) {
		if (stack == null) {
			return false;
		}
		IModule module = stack.getModule();
		if (module == null) {
			return false;
		}
		moduleStacks.add(stack.copy());
		itemStacks.add(itemStack.copy());
		return false;
	}

	@Override
	public List<ModuleStack> getModuleStacks() {
		return moduleStacks;
	}

	@Override
	public ModuleStack getModuleStack(ModuleUID UID) {
		for ( ModuleStack stack : moduleStacks ) {
			if (stack.getUID().equals(UID)) {
				return stack;
			}
		}
		return null;
	}

	@Override
	public ItemStack getItemStack(ModuleUID UID) {
		ModuleStack stack = getModuleStack(UID);
		return itemStacks.get(moduleStacks.indexOf(stack));
	}

	@Override
	public List getModuleSatcks(Class moduleClass) {
		if (moduleClass == null) {
			return null;
		}
		List<ModuleStack> stackList = Lists.newArrayList();
		for ( ModuleStack stack : moduleStacks ) {
			if (moduleClass.isAssignableFrom(stack.getModule().getClass())) {
				stackList.add(stack);
			}
		}
		return stackList;
	}
}
