package modularmachines.api.modules;

import modularmachines.api.modules.positions.IModulePosition;

/**
 * A builder to create a module data.
 */
public interface IModuleDataBuilder {
	IModuleDataBuilder setPositions(IModulePosition... positions);
	
	IModuleDataBuilder setRegistryName(String registryName);
	
	IModuleDataBuilder setTranslationKey(String unlocalizedName);
	
	IModuleDataBuilder setComplexity(int complexity);
	
	IModuleDataBuilder setDefinition(IModuleDefinition definition);
	
	IModuleData build();
}
