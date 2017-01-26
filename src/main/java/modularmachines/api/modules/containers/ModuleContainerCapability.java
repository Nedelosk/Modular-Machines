package modularmachines.api.modules.containers;

import modularmachines.api.modules.ModuleData;
import net.minecraft.item.ItemStack;

public class ModuleContainerCapability extends ModuleContainerNBT {

	public ModuleContainerCapability(ItemStack parent, ModuleData... datas) {
		super(parent, datas);
	}

	@Override
	public boolean matches(ItemStack stack) {
		return super.matches(stack) && stack.areCapsCompatible(parent);
	}
}
