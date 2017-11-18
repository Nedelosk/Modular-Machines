package modularmachines.api.modules;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import modularmachines.api.modules.containers.IModuleDataContainer;

public class ModuleRegistry {
	
	public static Capability MODULE_LOGIC;
	
	public static Capability ASSEMBLER;
	
	@CapabilityInject(IModuleContainer.class)
	public static Capability<IModuleContainer> MODULE_CONTAINER;
	
	private static final List<IModuleDataContainer> CONTAINERS = new ArrayList<>();
	
	public static void registerContainer(IModuleDataContainer container){
		CONTAINERS.add(container);
	}
	
	public static List<IModuleDataContainer> getContainers(){
		return CONTAINERS;
	}
}
