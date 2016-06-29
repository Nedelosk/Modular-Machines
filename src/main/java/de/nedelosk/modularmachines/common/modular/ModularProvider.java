package de.nedelosk.modularmachines.common.modular;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ModularProvider implements ICapabilityProvider {

	public IModularHandler modularHandler;

	public ModularProvider(IModularHandler modularHandler) {
		this.modularHandler = modularHandler;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == ModuleManager.MODULAR_HANDLER_CAPABILITY){
			return true;
		}
		return false;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == ModuleManager.MODULAR_HANDLER_CAPABILITY){
			return ModuleManager.MODULAR_HANDLER_CAPABILITY.cast(modularHandler);
		}
		return null;
	}

}
