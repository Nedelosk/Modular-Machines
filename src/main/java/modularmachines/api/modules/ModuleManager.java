package modularmachines.api.modules;

import java.util.List;

import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.containers.IModuleContainer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ModuleManager {

	@CapabilityInject(IModuleLogic.class)
	public static Capability<IModuleLogic> MODULE_LOGIC;
	
	@CapabilityInject(IAssembler.class)
	public static Capability<IAssembler> ASSEMBLER;
	
	public static List<IModuleContainer> getContainers(){
		return null;
	}
}
