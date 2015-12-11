package nedelosk.forestcore.api.modules;

import java.util.List;

import nedelosk.forestcore.api.modules.basic.IObjectManager;

public interface IModuleManager {
	
	<O> void register(IObjectManager<O> manager, O object, Object... objects);
	
	List<IModule> getModules();
	
	IModule registerModule(IModule module);
	
	void registerModules();
	
	void preInit();
	
	void init();
	
	void postInit();

}
