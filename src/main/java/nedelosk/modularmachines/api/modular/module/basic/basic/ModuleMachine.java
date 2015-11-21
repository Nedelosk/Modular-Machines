package nedelosk.modularmachines.api.modular.module.basic.basic;

import nedelosk.modularmachines.api.modular.utils.ModuleStack;

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
