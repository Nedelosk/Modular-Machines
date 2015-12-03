package nedelosk.modularmachines.api.modules.machines;

import nedelosk.modularmachines.api.modules.Module;
import nedelosk.modularmachines.api.utils.ModuleStack;

public class ModuleMachine extends Module {

	public ModuleMachine() {
	}

	public ModuleMachine(String moduleModifier) {
		super(moduleModifier);
	}

	@Override
	public String getName(ModuleStack stack, boolean withTypeModifier) {
		return "module" + ((getModifier(stack) != null) ? getModifier(stack) : "") + (withTypeModifier ? ((getTypeModifier(stack) != null) ? getTypeModifier(stack) : "") : "");
	}

	@Override
	public String getModuleName() {
		return "Machine";
	}

}
