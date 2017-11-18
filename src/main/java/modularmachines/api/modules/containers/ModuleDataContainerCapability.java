package modularmachines.api.modules.containers;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.ModuleData;

public class ModuleDataContainerCapability extends ModuleDataContainerNBT {
	
	public ModuleDataContainerCapability(ItemStack parent, ModuleData data) {
		super(parent, data);
	}
	
	@Override
	public boolean matches(ItemStack stack) {
		return super.matches(stack) && stack.areCapsCompatible(parent);
	}
}
