package modularmachines.common.modules.data;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.IModuleData;
import modularmachines.api.modules.IModuleType;
import modularmachines.common.utils.ItemUtil;

public class ModuleTypeNBT implements IModuleType {
	
	protected final ItemStack parent;
	protected final IModuleData data;
	
	public ModuleTypeNBT(ItemStack parent, IModuleData data) {
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
		return ItemUtil.isEquivalent(parent, stack);
	}
}
