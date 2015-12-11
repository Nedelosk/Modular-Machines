package nedelosk.forestcore.api.modules;

import nedelosk.forestcore.api.modules.basic.IObjectManager;

public interface IModule {

	String getName();
	
	void preInit();
	
	void init();
	
	void postInit();
	
	boolean isActive();
	
	void onRegisterObject(IObjectManager manager);
	
}
