package de.nedelosk.modularmachines.common.modules.handlers;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleState;
import de.nedelosk.modularmachines.api.modules.handlers.IContentFilter;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class ItemFluidFilter implements IContentFilter<ItemStack, IModule> {

	private boolean requireFluid;

	public ItemFluidFilter(boolean requireFluid) {
		this.requireFluid = requireFluid;
	}

	@Override
	public boolean isValid(int index, ItemStack content, IModuleState<IModule> module) {
		if(content == null) {
			return false;
		}
		if(content.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)){
			if(!requireFluid){
				return true;
			}
			IFluidHandler handler = content.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
			boolean hasFluid = false;
			for(IFluidTankProperties property : handler.getTankProperties()){
				if(property != null && property.getContents() != null && property.getContents().amount > 0){
					return true;
				}
			}
		}
		return false;
	}
}
