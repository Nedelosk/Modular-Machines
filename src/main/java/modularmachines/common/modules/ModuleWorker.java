package modularmachines.common.modules;

import modularmachines.api.modules.IModuleWorker;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.containers.IModuleProvider;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.property.PropertyBool;

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
		return state.getValue(WORKING);
	}

	protected void setIsWorking(IModuleState state, boolean isWorking) {
		state.setValue(WORKING, isWorking);
	}
}
