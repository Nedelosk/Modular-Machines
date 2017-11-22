package modularmachines.api.modules;

import modularmachines.api.components.IComponentProvider;

public interface IModuleDefinition {
	
	void addComponents(Module module, IComponentProvider<IModuleComponent> provider);
}
