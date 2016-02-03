package de.nedelosk.forestmods.api.utils;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modular.material.Materials;
import de.nedelosk.forestmods.api.modular.material.Materials.Material;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.modules.IModuleSaver;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public final class ModuleStack<M extends IModule, S extends IModuleSaver> {

	private M module;
	private S saver;
	private ItemStack itemStack;
	private Material material;
	private int ID;

	public ModuleStack(ItemStack itemStack, M module, Material material) {
		this.module = module;
		this.itemStack = itemStack;
		this.material = material;
		this.saver = (S) module.createSaver(this);
	}

	public ModuleStack(M module, Material material) {
		this.module = module;
		this.saver = (S) module.createSaver(this);
		this.material = material;
		this.itemStack = null;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ModuleStack)) {
			return false;
		}
		ModuleStack stack = (ModuleStack) obj;
		if (material != null && stack.material != null && material.equals(stack.material) && module != null && stack.getModule() != null
				&& module.getUID().equals(stack.getModule().getUID())) {
			return true;
		}
		return false;
	}

	public static ModuleStack loadFromNBT(NBTTagCompound nbt, IModular modular) {
		ItemStack itemStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Item"));
		ResourceLocation UID = new ResourceLocation(nbt.getString("UID"));
		IModule module = ModuleRegistry.getModule(UID);
		Material material = Materials.getMaterial(nbt.getString("Material"));
		ModuleStack stack = new ModuleStack(itemStack, module, material);
		if (stack.saver != null) {
			stack.saver.readFromNBT(nbt.getCompoundTag("saver"), modular, stack);
		}
		return stack;
	}

	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		nbt.setString("UID", module.getUID());
		if (saver != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			saver.writeToNBT(nbtTag, modular, this);
			nbt.setTag("saver", nbtTag);
		}
		nbt.setString("Material", material.getName());
		NBTTagCompound nbtTagItem = new NBTTagCompound();
		itemStack.writeToNBT(nbtTagItem);
		nbt.setTag("Item", nbtTagItem);
	}

	public M getModule() {
		return module;
	}

	public S getSaver() {
		return saver;
	}

	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public Material getMaterial() {
		return material;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getID() {
		return ID;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public ModuleStack copy() {
		return new ModuleStack(itemStack, module, material);
	}
}
