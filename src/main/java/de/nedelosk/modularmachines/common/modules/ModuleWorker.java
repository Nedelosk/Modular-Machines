package de.nedelosk.modularmachines.common.modules;

import de.nedelosk.modularmachines.api.modules.IModuleWorker;
import de.nedelosk.modularmachines.api.modules.Module;
import de.nedelosk.modularmachines.api.modules.containers.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.IModuleProvider;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.property.PropertyBool;

public class ModuleWorker extends Module implements IModuleWorker {

	public static final PropertyBool WORKING = new PropertyBool("isWorking", false);

	public ModuleWorker(String name) {
		super(name);
	}

	@Override
	public IModuleState createState(IModuleProvider provider, IModuleContainer container) {
		return super.createState(provider, container).register(WORKING);
	}

	@Override
	public boolean isWorking(IModuleState state) {
		return state.get(WORKING);
	}

	protected void setIsWorking(IModuleState state, boolean isWorking) {
		state.set(WORKING, isWorking);
	}
}
