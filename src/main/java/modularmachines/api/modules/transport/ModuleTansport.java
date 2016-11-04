package modularmachines.api.modules.transport;

import java.util.ArrayList;
import java.util.List;

import modularmachines.api.modular.IModular;
import modularmachines.api.modules.controller.IModuleControl;
import modularmachines.api.modules.controller.ModuleControlled;
import modularmachines.api.modules.state.IModuleState;

public abstract class ModuleTansport<H> extends ModuleControlled {

	protected Class<H> handlerClass;

	public ModuleTansport(String name, Class<H> handlerClass) {
		super(name);
		this.handlerClass = handlerClass;
	}

	public List<ITransportCycle> getCycles(IModuleState moduleState) {
		IModular modular = moduleState.getModular();
		IModuleControl control = getModuleControl(moduleState);
		for(IModuleState otherState : modular.getModules()) {
			if (!control.hasPermission(moduleState)) {
				continue;
			}
		}
		return null;
	}

	public List<H> getHandlers(IModuleState moduleState) {
		List<H> handlers = new ArrayList<>();
		IModular modular = moduleState.getModular();
		for(IModuleState otherState : modular.getModules()) {
			H handler = otherState.getContentHandler(handlerClass);
			if (handler != null) {
				handlers.add(handler);
				break;
			}
		}
		return handlers;
	}

	@Override
	public List<IModuleState> getUsedModules(IModuleState moduleState) {
		List<IModuleState> usedModules = new ArrayList<>();
		IModular modular = moduleState.getModular();
		for(IModuleState otherState : modular.getModules()) {
			if (otherState.getContentHandler(handlerClass) != null) {
				usedModules.add(otherState);
				break;
			}
		}
		return usedModules;
	}
}
