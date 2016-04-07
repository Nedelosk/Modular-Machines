package de.nedelosk.forestmods.api.internal;

import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public class FakeInternalMethodHandler implements IInternalMethodHandler {

	@Override
	public <M extends IModule> ItemStack addModuleToModuelItem(ModuleStack<M> moduleStack) {
		return null;
	}
}