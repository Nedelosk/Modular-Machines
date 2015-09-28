package nedelosk.modularmachines.api.basic.machine.module;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.basic.machine.ModularManager;
import nedelosk.modularmachines.api.basic.machine.modular.IModular;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class ModuleStack<M extends IModule> {

	private ItemStack item;
	private M module;
	private int tier;
	private boolean hasNbt;
	
	private ModuleStack() {
	}
	
	public ModuleStack(ItemStack item, M module, int tier, boolean hasNbt) {
		this.item = item;
		this.module = module;
		this.tier = tier;
		this.hasNbt = hasNbt;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ModuleStack)
		{
			ModuleStack stackModule = (ModuleStack) obj;
			if(stackModule.hasNbt == hasNbt && stackModule.tier == tier && item.getItem() == stackModule.item.getItem() && (hasNbt ? getItem().stackTagCompound.equals(stackModule.getItem().stackTagCompound) : true) && stackModule.module.getName().equals(module.getName()))
				return true;
		}
		return false;
	}
	
	public void readFromNBT(NBTTagCompound nbt, IModular modular) {
		module = ModularManager.buildModule(nbt.getString("ModuleName"), nbt.getCompoundTag("Module"), modular);
		item = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Item"));
		tier = nbt.getInteger("Tier");
		hasNbt = nbt.getBoolean("hasNbt");
	}

	public void writeToNBT(NBTTagCompound nbt, IModular modular) {
		nbt.setString("ModuleName", module.getModuleName());
		nbt.setInteger("Tier", tier);
		nbt.setBoolean("hasNbt", hasNbt);
		NBTTagCompound nbtTag = new NBTTagCompound();
		module.writeToNBT(nbtTag);
		nbt.setTag("Module", nbtTag);
		NBTTagCompound itemNBT = new NBTTagCompound();
		item.writeToNBT(itemNBT);
		nbt.setTag("Item", itemNBT);
	}
	
	public static ModuleStack loadStackFromNBT(NBTTagCompound nbt, IModular modular)
	{
		ModuleStack stack = new ModuleStack();
		stack.readFromNBT(nbt, modular);
		return stack;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public M getModule() {
		return module;
	}
	
	public String getModuleName() {
		return module.getModuleName();
	}
	
	public int getTier() {
		return tier;
	}
	
	public boolean hasNbt() {
		return hasNbt;
	}
	
	public ModuleStack copy(){
		return new ModuleStack(item, module, tier, hasNbt);
	}
	
}
