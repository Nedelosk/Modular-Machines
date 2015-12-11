package nedelosk.forestcore.api.modules;

import nedelosk.forestcore.api.modules.basic.IObjectManager;

public abstract class Module implements IModule {

	public final String name;
	
	public Module(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void preInit() {
		
	}

	@Override
	public void init() {
		
	}

	@Override
	public void postInit() {
		
	}

	@Override
	public void onRegisterObject(IObjectManager manager) {
		
	}

}
