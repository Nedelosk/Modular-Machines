package nedelosk.modularmachines.api.utils;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.material.Materials;
import nedelosk.modularmachines.api.modular.material.Materials.Material;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public final class ModuleStack<M extends IModule> {

	private M module;
	private IModuleSaver saver;
	private ItemStack itemStack;
	private Material material;

	public ModuleStack(ItemStack itemStack, M module, Material material) {
		this.module = module;
		this.itemStack = itemStack;
		this.material = material;
		this.saver = module.getSaver(this);
	}

	public ModuleStack(M module, Material material) {
		this.module = module;
		this.saver = module.getSaver(this);
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
				&& module.getName(this).equals(stack.getModule().getName(stack))) {
			return true;
		}
		return false;
	}

	public static ModuleStack loadFromNBT(NBTTagCompound nbt, IModular modular) {
		ItemStack itemStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Item"));
		ResourceLocation registry = new ResourceLocation(nbt.getString("Registry"));
		IModule module = ModuleRegistry.getModule(registry);
		Material material = Materials.getMaterial(nbt.getString("Material"));
		ModuleStack stack = new ModuleStack(itemStack, module, material);
		if (stack.saver != null) {
			stack.saver.readFromNBT(nbt.getCompoundTag("saver"), modular, stack);
		}
		return stack;
	}

	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		nbt.setString("Registry", module.getRegistry().toString());
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

	public IModuleSaver getSaver() {
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

	public void setMaterial(Material material) {
		this.material = material;
	}

	public ModuleStack copy() {
		return new ModuleStack(itemStack, module, material);
	}
}
