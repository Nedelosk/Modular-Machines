package modularmachines.common.modules;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import modularmachines.api.modules.IModuleContainer;

public class ModuleCapabilities {
	@CapabilityInject(IModuleContainer.class)
	public static Capability<IModuleContainer> MODULE_CONTAINER;
}
