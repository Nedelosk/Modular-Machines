package nedelosk.modularmachines.api.modular.module.basic.basic;

public class ModuleBasic extends Module {

	public String moduleName;
	
	public ModuleBasic(String moduleModifier, String moduleName){
		super(moduleModifier);
		this.moduleName = moduleName;
	}
	
	@Override
	public String getModuleName() {
		return moduleName;
	}

}
