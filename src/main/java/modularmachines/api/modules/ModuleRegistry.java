package modularmachines.api.modules;

import java.util.ArrayList;
import java.util.List;

import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.logic.IModuleLogic;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ModuleRegistry {

	@CapabilityInject(IModuleLogic.class)
	public static Capability<IModuleLogic> MODULE_LOGIC;
	
	@CapabilityInject(IAssembler.class)
	public static Capability<IAssembler> ASSEMBLER;
	
	private static final List<IModuleContainer> CONTAINERS = new ArrayList<>();
	
	public static void registerContainer(IModuleContainer container){
		CONTAINERS.add(container);
	}
	
	public static List<IModuleContainer> getContainers(){
		return CONTAINERS;
	}
}
