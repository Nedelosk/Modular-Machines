package nedelosk.forestcore.api.modules;

import nedelosk.forestcore.api.modules.manager.IObjectManager;

public interface IModule {

	String getName();
	
	void preInit(IModuleManager manager);
	
	void init(IModuleManager manager);
	
	void postInit(IModuleManager manager);
	
	boolean isActive();
	
	void onRegisterObject(IObjectManager manager);
	
}
