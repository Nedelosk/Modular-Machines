package modularmachines.api.modules;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.containers.IModuleDataContainer;
import modularmachines.api.modules.logic.IModuleLogic;

public class ModuleRegistry {

	@CapabilityInject(IModuleLogic.class)
	public static Capability<IModuleLogic> MODULE_LOGIC;
	
	@CapabilityInject(IAssembler.class)
	public static Capability<IAssembler> ASSEMBLER;
	
	@CapabilityInject(IModuleContainer.class)
	public static Capability<IModuleContainer> MODULE_CONTAINER;
	
	@Nullable
	public static IModuleHelper helper;
	
	private static final List<IModuleDataContainer> CONTAINERS = new ArrayList<>();
	
	public static void registerContainer(IModuleDataContainer container){
		CONTAINERS.add(container);
	}
	
	public static List<IModuleDataContainer> getContainers(){
		return CONTAINERS;
	}
}
