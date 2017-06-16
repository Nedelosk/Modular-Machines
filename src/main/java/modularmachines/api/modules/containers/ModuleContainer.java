package modularmachines.api.modules.containers;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.ModuleData;

public class ModuleContainer implements IModuleContainer {

	protected final ItemStack parent;
	protected final ModuleData data;

	public ModuleContainer(ItemStack parent, ModuleData data) {
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
		if(stack == null) {
			return false;
		}
		return stack.getItem() == parent.getItem();
	}
}
