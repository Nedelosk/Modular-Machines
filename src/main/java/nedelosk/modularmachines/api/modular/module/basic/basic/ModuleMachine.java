package nedelosk.modularmachines.api.modular.module.basic.basic;

public class ModuleMachine extends Module {

	public ModuleMachine() {
	}
	
	public ModuleMachine(String moduleModifier) {
		super(moduleModifier);
	}
	
	@Override
	public String getModuleName() {
		return "Producer";
	}

}
