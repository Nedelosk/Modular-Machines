package nedelosk.forestcore.library.modules;

import nedelosk.forestcore.library.modules.manager.IObjectManager;

public interface IModule {

	String getName();
	
	void preInit(IModuleManager manager);
	
	void init(IModuleManager manager);
	
	void postInit(IModuleManager manager);
	
	boolean isActive();
	
	void onRegisterObject(IObjectManager manager);
	
}
