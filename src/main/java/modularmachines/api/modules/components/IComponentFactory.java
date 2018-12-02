package modularmachines.api.modules.components;

import modularmachines.api.modules.IModule;

public interface IComponentFactory<C extends IModuleComponent> {
	
	C create(IModule module);
}
