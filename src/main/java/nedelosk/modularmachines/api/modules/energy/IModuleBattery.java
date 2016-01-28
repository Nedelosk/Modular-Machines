package nedelosk.modularmachines.api.modules.energy;

import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.basic.IModuleWithRenderer;

public interface IModuleBattery<S extends IModuleBatterySaver> extends IModule<S>, IModuleWithRenderer<S> {
}
