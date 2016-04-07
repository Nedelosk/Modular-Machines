package de.nedelosk.forestmods.common.core;

import de.nedelosk.forestmods.api.internal.IInternalMethodHandler;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.items.ItemModule;
import net.minecraft.item.ItemStack;

public class InternalMethodHandler implements IInternalMethodHandler {

	@Override
	public <M extends IModule> ItemStack addModuleToModuelItem(ModuleStack<M> moduleStack) {
		return ItemModule.addModule(moduleStack);
	}
}
