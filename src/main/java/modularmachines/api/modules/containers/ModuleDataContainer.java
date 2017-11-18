package modularmachines.api.modules.containers;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.ModuleData;

public class ModuleDataContainer implements IModuleDataContainer {
	
	protected final ItemStack parent;
	protected final ModuleData data;
	
	public ModuleDataContainer(ItemStack parent, ModuleData data) {
		this.parent = parent;
		this.data = data;
	}
	
	@Override
	public ItemStack getParent() {
		return parent;
	}
	
	@Override
	public ModuleData getData() {
		return data;
	}
	
	@Override
	public boolean matches(ItemStack stack) {
		if (stack.isEmpty()) {
			return false;
		}
		return stack.getItem() == parent.getItem();
	}
}
