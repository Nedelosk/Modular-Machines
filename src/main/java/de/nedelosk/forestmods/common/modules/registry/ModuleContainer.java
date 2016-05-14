package de.nedelosk.forestmods.common.modules.registry;

import de.nedelosk.forestmods.library.material.IMaterial;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.ModuleUID;
import net.minecraft.item.ItemStack;

public class ModuleContainer implements IModuleContainer {

	public ItemStack stack;
	public final ModuleUID UID;
	public final IMaterial material;
	public final boolean ignorNBT;
	public final Class<? extends IModule> moduleClass;

	public ModuleContainer(ItemStack stack, ModuleUID UID, IMaterial material, Class<? extends IModule> moduleClass, boolean ignorNBT) {
		this.stack = stack;
		this.UID = UID;
		this.material = material;
		this.moduleClass = moduleClass;
		this.ignorNBT = ignorNBT;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof IModuleContainer)) {
			return false;
		}
		IModuleContainer i = (IModuleContainer) obj;
		if (stack != null && i.getItemStack() != null && i.getItemStack().getItem() != null && stack.getItem() != null
				&& stack.getItemDamage() == i.getItemStack().getItemDamage()
				&& (ignorNBT && i.ignorNBT() || stack.getTagCompound() == null && i.getItemStack().getTagCompound() == null || stack.getTagCompound() != null
				&& i.getItemStack().getTagCompound() != null && stack.getTagCompound().equals(i.getItemStack().getTagCompound()))) {
		}
		return false;
	}

	@Override
	public ItemStack getItemStack() {
		return stack;
	}

	@Override
	public void setItemStack(ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public boolean ignorNBT() {
		return ignorNBT;
	}

	@Override
	public IMaterial getMaterial() {
		return material;
	}

	@Override
	public ModuleUID getUID() {
		return UID;
	}

	@Override
	public Class<? extends IModule> getModuleClass() {
		return moduleClass;
	}
}
