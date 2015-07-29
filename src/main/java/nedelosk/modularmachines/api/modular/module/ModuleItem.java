package nedelosk.modularmachines.api.modular.module;

import net.minecraft.item.ItemStack;

public class ModuleItem {

	public ItemStack item;
	public String moduleName;
	public IModule module;
	public int tier;
	public boolean hasNbt;
	
	public ModuleItem(ItemStack item, IModule module, int tier, boolean hasNbt) {
		this.item = item;
		this.moduleName = module.getModuleName();
		this.module = module;
		this.tier = tier;
		this.hasNbt = hasNbt;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ModuleItem)
		{
			ModuleItem itemModule = (ModuleItem) obj;
			if(itemModule.hasNbt == hasNbt && itemModule.moduleName.equals(moduleName) && itemModule.tier == tier && ItemStack.areItemStackTagsEqual(item, itemModule.item) && itemModule.module.getName().equals(module.getName()))
				return true;
		}
		return false;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public IModule getModule() {
		return module;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public int getTier() {
		return tier;
	}
	
}
