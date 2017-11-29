package modularmachines.common.modules.data;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.IModuleData;
import modularmachines.api.modules.IModuleType;
import modularmachines.common.utils.ItemUtil;

public class ModuleType implements IModuleType {
	
	protected final ItemStack parent;
	protected final IModuleData data;
	
	public ModuleType(ItemStack parent, IModuleData data) {
		this.parent = parent;
		this.data = data;
	}
	
	@Override
	public ItemStack getItem() {
		return parent;
	}
	
	@Override
	public IModuleData getData() {
		return data;
	}
	
	@Override
	public boolean matches(ItemStack stack) {
		return ItemUtil.isCraftingEquivalent(parent, stack);
	}
}
