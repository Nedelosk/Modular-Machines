package modularmachines.common.modules.data;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.data.IModuleData;
import modularmachines.api.modules.data.IModuleDataContainer;

public class ModuleDataContainer implements IModuleDataContainer {
	
	protected final ItemStack parent;
	protected final IModuleData data;
	
	public ModuleDataContainer(ItemStack parent, IModuleData data) {
		this.parent = parent;
		this.data = data;
	}
	
	@Override
	public ItemStack getParent() {
		return parent;
	}
	
	@Override
	public IModuleData getData() {
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
