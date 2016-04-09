package de.nedelosk.forestmods.api.utils;

import de.nedelosk.forestmods.api.modules.IModule;

public class ModuleType {

	public final Class<? extends IModule> moduleClass;
	public final Object[] parameters;

	public ModuleType(Class<? extends IModule> moduleClass, Object[] parameters) {
		this.moduleClass = moduleClass;
		this.parameters = parameters;
	}
}
