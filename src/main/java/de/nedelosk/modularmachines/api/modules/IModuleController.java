package de.nedelosk.modularmachines.api.modules;

public interface IModuleController extends IModule {

	int getAllowedComplexity(IModuleState state);

}
