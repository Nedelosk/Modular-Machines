package de.nedelosk.modularmachines.common.modules.handlers;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class ItemFluidFilter implements IContentFilter<ItemStack, IModule> {

	@Override
	public boolean isValid(int index, ItemStack content, IModuleState<IModule> module) {
		if(content == null) {
			return false;
		}
		return content.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
	}
}
