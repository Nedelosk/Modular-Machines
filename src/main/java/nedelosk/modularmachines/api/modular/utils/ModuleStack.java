package nedelosk.modularmachines.api.modular.utils;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.tool.producer.IProducer;
import nedelosk.modularmachines.api.modular.type.Types;
import nedelosk.modularmachines.api.modular.type.Types.Type;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class ModuleStack<M extends IModule, P extends IProducer> {

	private ItemStack item;
	private M module;
	private Type type;
	private P producer;
	private boolean hasNbt;
	
	private ModuleStack() {
	}
	
	public ModuleStack(ItemStack item, M module, Type type, boolean hasNbt) {
		this.item = item;
		this.module = module;
		this.type = type;
		this.hasNbt = hasNbt;
		this.producer = null;
	}
	
	public ModuleStack(ItemStack item, M module, P producer, Type type, boolean hasNbt) {
		this.item = item;
		this.module = module;
		if(producer != null){
			if(module.getTypeModifier(this) == null)
				module.addType(type, producer.getName(this));
		}
		else{
			if(module.getTypeModifier(this) == null)
				module.addType(type, type.getName());
		}
		this.type = type;
		this.hasNbt = hasNbt;
		if(producer == null){
			producer = null;
		}
		this.producer = producer;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ModuleStack)
		{
			ModuleStack stackModule = (ModuleStack) obj;
			if(stackModule.hasNbt == hasNbt && stackModule.type == type && item.getItemDamage() == stackModule.item.getItemDamage() &&  item.getItem() == stackModule.item.getItem() && (hasNbt ? getItem().stackTagCompound.equals(stackModule.getItem().stackTagCompound) : true) && stackModule.module == module && (stackModule.producer == null && producer == null || (stackModule.producer == null ? false : true) || (producer == null ? false : true) || stackModule.producer.getName(this).equals(producer.getName(this))))
				return true;
		}
		return false;
	}
	
	public void readFromNBT(NBTTagCompound nbt, IModular modular) throws Exception{
		module = (M) ModuleRegistry.getModule(nbt.getString("ModuleName"));
		item = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Item"));
		type = Types.getType(nbt.getString("Type"));
		hasNbt = nbt.getBoolean("hasNbt");
		if(nbt.hasKey("Producer")){
			NBTTagCompound nbtTag = nbt.getCompoundTag("Producer");
			producer = ModuleRegistry.moduleFactory.createProducer(nbtTag.getString("Name"), nbtTag, modular, this);
		}
	}

	public void writeToNBT(NBTTagCompound nbt, IModular modular) throws Exception{
		nbt.setString("ModuleName", module.getRegistryName());
		nbt.setString("Type", type.getName());
		nbt.setBoolean("hasNbt", hasNbt);
		if(producer != null){
			NBTTagCompound nbtTag = new NBTTagCompound();
			producer.writeToNBT(nbtTag, modular, this);
			nbtTag.setString("Name", producer.getName(this));
			nbt.setTag("Producer", nbtTag);
		}
		NBTTagCompound itemNBT = new NBTTagCompound();
		item.writeToNBT(itemNBT);
		nbt.setTag("Item", itemNBT);
	}
	
	public static ModuleStack loadStackFromNBT(NBTTagCompound nbt, IModular modular) throws Exception{
		ModuleStack stack = new ModuleStack();
		stack.readFromNBT(nbt, modular);
		return stack;
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
	
	public ModuleStack copy(){
		return new ModuleStack(item, module, producer, type, hasNbt);
	}
	
}
