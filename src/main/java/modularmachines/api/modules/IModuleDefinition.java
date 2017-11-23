package modularmachines.api.modules;

import modularmachines.api.modules.components.IModuleComponentFactory;

public interface IModuleDefinition {
	
	void addComponents(IModule module, IModuleComponentFactory factory);
}
