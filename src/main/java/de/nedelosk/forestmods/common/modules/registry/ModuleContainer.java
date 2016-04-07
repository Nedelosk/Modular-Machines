package de.nedelosk.forestmods.common.modules.registry;

import de.nedelosk.forestmods.api.utils.IModuleHandler;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraft.item.ItemStack;

public final class ModuleContainer implements IModuleHandler {

	public final ItemStack stack;
	public final ModuleStack moduleStack;
	public final boolean ignorNBT;

	public ModuleContainer(ItemStack stack, ModuleStack moduleStack, boolean ignorNBT) {
		this.stack = stack;
		this.moduleStack = moduleStack;
		this.ignorNBT = ignorNBT;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof IModuleHandler)) {
			return false;
		}
		IModuleHandler i = (IModuleHandler) obj;
		if (stack != null && i.getStack() != null && i.getStack().getItem() != null && stack.getItem() != null
				&& stack.getItemDamage() == i.getStack().getItemDamage()
				&& (ignorNBT && i.ignorNBT() || stack.getTagCompound() == null && i.getStack().getTagCompound() == null || stack.getTagCompound() != null
						&& i.getStack().getTagCompound() != null && stack.getTagCompound().equals(i.getStack().getTagCompound()))) {
		}
		return false;
	}

	@Override
	public ModuleContainer copy() {
		return new ModuleContainer(stack.copy(), moduleStack, ignorNBT);
	}

	@Override
	public ItemStack getStack() {
		return stack;
	}

	@Override
	public boolean ignorNBT() {
		return ignorNBT;
	}

	@Override
	public ModuleStack getModuleStack() {
		return moduleStack;
	}
}
