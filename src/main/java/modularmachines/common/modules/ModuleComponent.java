package modularmachines.common.modules;

import modularmachines.api.modules.IModuleComponent;
import modularmachines.api.modules.Module;
import modularmachines.common.utils.components.Component;

public class ModuleComponent extends Component implements IModuleComponent {
	protected final Module module;
	
	public ModuleComponent(Module module) {
		this.module = module;
	}
	
	public Module getModule() {
		return module;
	}
}
