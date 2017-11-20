package modularmachines.common.modules.data;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.data.IModuleData;

public class ModuleDataContainerCapability extends ModuleDataContainerNBT {
	
	public ModuleDataContainerCapability(ItemStack parent, IModuleData data) {
		super(parent, data);
	}
	
	@Override
	public boolean matches(ItemStack stack) {
		return super.matches(stack) && stack.areCapsCompatible(parent);
	}
}
