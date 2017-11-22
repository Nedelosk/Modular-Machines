package modularmachines.api.modules;

import modularmachines.api.components.IComponent;
import modularmachines.api.components.IComponentProvider;

public interface IModuleComponent<P extends IComponentProvider> extends IComponent<P> {
	Module getModule();
}
