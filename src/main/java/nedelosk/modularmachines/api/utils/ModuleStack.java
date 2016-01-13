package nedelosk.modularmachines.api.utils;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.type.Types;
import nedelosk.modularmachines.api.modular.type.Types.Type;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.IProducer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class ModuleStack<M extends IModule, P extends IProducer> {

	private ItemStack item;
	private M module;
	private Type type;
	private P producer;
	private boolean hasNbt;

	public ModuleStack(NBTTagCompound nbt, IModular modular) throws Exception {
		readFromNBT(nbt, modular);
	}

	public ModuleStack(M module, P producer, Type type, boolean hasNbt) {
		this(null, module, producer, type, hasNbt);
	}

	public ModuleStack(ItemStack item, M module, P producer, Type type, boolean hasNbt) {
		this.item = item;
		this.module = module;
		this.type = type;
		this.hasNbt = hasNbt;
		this.producer = producer;
		if (ModuleRegistry.getModule(module.getRegistryName()) == null) {
			ModuleRegistry.registerModule(module);
		}
		ModuleRegistry.addTypeModifier(module, type, producer == null ? type.getName() : producer.getName(this));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ModuleStack) {
			ModuleStack stackModule = (ModuleStack) obj;
			if (stackModule.item.getItem() == null || item.getItem() == null) {
				return false;
			}
			if (stackModule.item.getItem() == item.getItem() && stackModule.item.getItemDamage() == item.getItemDamage()) {
				if (stackModule.item.getTagCompound() != null && item.getTagCompound() != null
						&& stackModule.item.getTagCompound().equals(item.getTagCompound()) || !stackModule.hasNbt() && !hasNbt()) {
					if (stackModule.type == type && stackModule.module == module && (producer == null && stackModule.getProducer() == null
							|| producer.getName(this).equals(stackModule.getProducer().getName(stackModule)))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void readFromNBT(NBTTagCompound nbt, IModular modular) throws Exception {
		module = (M) ModuleRegistry.getModule(nbt.getString("ModuleName"));
		item = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Item"));
		type = Types.getType(nbt.getString("Type"));
		hasNbt = nbt.getBoolean("hasNbt");
		if (nbt.hasKey("Producer")) {
			NBTTagCompound nbtTag = nbt.getCompoundTag("Producer");
			producer = ModuleRegistry.producerFactory.createProducer(nbtTag.getString("Name"), nbtTag, modular, this);
		}
	}

	public void writeToNBT(NBTTagCompound nbt, IModular modular) throws Exception {
		nbt.setString("ModuleName", module.getRegistryName());
		nbt.setString("Type", type.getName());
		nbt.setBoolean("hasNbt", hasNbt);
		if (producer != null) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			producer.writeToNBT(nbtTag, modular, this);
			nbtTag.setString("Name", producer.getName(this));
			nbt.setTag("Producer", nbtTag);
		}
		NBTTagCompound itemNBT = new NBTTagCompound();
		item.writeToNBT(itemNBT);
		nbt.setTag("Item", itemNBT);
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItemStack(ItemStack item) {
		this.item = item;
	}

	public M getModule() {
		return module;
	}

	public P getProducer() {
		return producer;
	}

	public Type getType() {
		return type;
	}

	public boolean hasNbt() {
		return hasNbt;
	}

	public String getModuleName() {
		return module.getModuleName();
	}

	public ModuleStack copy() {
		return new ModuleStack(item, module, producer, type, hasNbt);
	}
}
