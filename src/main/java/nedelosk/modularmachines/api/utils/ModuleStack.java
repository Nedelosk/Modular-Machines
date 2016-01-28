package nedelosk.modularmachines.api.utils;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleSaver;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public final class ModuleStack<M extends IModule> {

	private M module;
	private IModuleSaver saver;
	private ItemStack itemStack;

	public ModuleStack(ItemStack itemStack, M module) {
		this.module = module;
		this.saver = module.getSaver(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ModuleStack) {
			ModuleStack producerStack = (ModuleStack) obj;
			if (module == null && producerStack.getModule() == null || module.getName(this).equals(producerStack.getModule().getName(producerStack))) {
				return true;
			}
		}
		return false;
	}

	public static ModuleStack loadFromNBT(NBTTagCompound nbt, IModular modular) throws Exception {
		ItemStack itemStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Item"));
		ResourceLocation registry = new ResourceLocation(nbt.getString("Registry"));
		IModule module = ModuleRegistry.getModule(registry);
		ModuleStack stack = new ModuleStack(itemStack, module);
		if (stack.saver != null) {
			stack.saver.readFromNBT(nbt.getCompoundTag("saver"), modular, stack);
		}
		return stack;
	}

	public void writeToNBT(NBTTagCompound nbt, IModular modular) throws Exception {
		nbt.setString("Registry", module.getRegistry().toString());
		if (saver != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			saver.writeToNBT(nbtTag, modular, this);
			nbt.setTag("saver", nbtTag);
		}
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

	public ItemStack getItemStack() {
		return itemStack;
	}

	public ModuleStack copy() {
		return new ModuleStack(itemStack, module);
	}
}
