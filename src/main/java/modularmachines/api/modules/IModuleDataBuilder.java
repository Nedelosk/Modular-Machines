package modularmachines.api.modules;

import modularmachines.api.modules.positions.IModulePosition;

public interface IModuleDataBuilder {
	IModuleDataBuilder setPositions(IModulePosition... positions);
	
	IModuleDataBuilder setRegistryName(String registryName);
	
	IModuleDataBuilder setUnlocalizedName(String unlocalizedName);
	
	IModuleDataBuilder setComplexity(int complexity);
	
	IModuleDataBuilder setDefinition(IModuleDefinition definition);
	
	IModuleData build();
}
