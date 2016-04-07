package de.nedelosk.forestmods.api.utils;

import de.nedelosk.forestmods.api.material.IMaterial;
import de.nedelosk.forestmods.api.material.MaterialRegistry;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.IModule;
import net.minecraft.nbt.NBTTagCompound;

public final class ModuleStack<M extends IModule> {

	private M module;
	private ModuleUID UID;
	private IMaterial material;

	public ModuleStack(String categoryUid, IMaterial material, M module) {
		this.UID = new ModuleUID(categoryUid, module.getName());
		if (ModuleManager.moduleRegistry.getModule(material, UID) == null) {
			ModuleManager.moduleRegistry.registerModule(material, categoryUid, module);
		} else if (ModuleManager.moduleRegistry.getModule(material, UID) != module) {
			module = (M) ModuleManager.moduleRegistry.getModule(material, UID);
		}
		if (MaterialRegistry.getMaterial(material.getName()) == null) {
			MaterialRegistry.registerMaterial(material);
		} else if (MaterialRegistry.getMaterial(material.getName()) != material) {
			material = MaterialRegistry.getMaterial(material.getName());
		}
		this.module = module;
		this.material = material;
	}

	private ModuleStack(ModuleUID UID, IMaterial material, M module) {
		this.UID = UID;
		this.module = module;
		this.material = material;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ModuleStack)) {
			return false;
		}
		ModuleStack stack = (ModuleStack) obj;
		if (material != null && stack.getMaterial() != null && material.getName().equals(stack.getMaterial().getName()) && UID != null && stack.getUID() != null
				&& UID.equals(stack.getUID()) && module.getName().equals(stack.getModule().getName())) {
			return true;
		}
		return false;
	}

	public static ModuleStack loadFromNBT(NBTTagCompound nbt, IModular modular) {
		ModuleUID UID = new ModuleUID(nbt.getString("UID"));
		IMaterial material = MaterialRegistry.getMaterial(nbt.getString("Material"));
		NBTTagCompound nbtTag = nbt.getCompoundTag("Modules");
		IModule module = ModuleManager.moduleRegistry.getModule(material, UID);
		ModuleStack moduleStack = new ModuleStack(UID, material, module);
		module.onAddInModular(modular, moduleStack);
		module.readFromNBT(nbtTag, modular);
		return moduleStack;
	}

	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		nbt.setString("UID", UID.toString());
		nbt.setString("Material", material.getName());
		NBTTagCompound nbtTag = new NBTTagCompound();
		module.writeToNBT(nbtTag, modular);
		nbt.setTag("Modules", nbtTag);
	}

	public M getModule() {
		return module;
	}

	public IMaterial getMaterial() {
		return material;
	}

	public ModuleUID getUID() {
		return UID;
	}

	public ModuleStack copy() {
		return new ModuleStack(UID, material, module);
	}

	@Override
	public String toString() {
		return material.getName() + "/" + UID.toString() + "/" + module.getUnlocalizedName(this);
	}
}
