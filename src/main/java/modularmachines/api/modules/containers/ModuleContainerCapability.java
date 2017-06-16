package modularmachines.api.modules.containers;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.ModuleData;

public class ModuleContainerCapability extends ModuleContainerNBT {

	public ModuleContainerCapability(ItemStack parent, ModuleData data) {
		super(parent, data);
	}

	@Override
	public boolean matches(ItemStack stack) {
		return super.matches(stack) && stack.areCapsCompatible(parent);
	}
}
