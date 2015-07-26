package nedelosk.nedeloskcore.modules;

import java.util.ArrayList;

import nedelosk.nedeloskcore.common.core.Log;

import org.apache.logging.log4j.Level;

public class ModuleManager {

	public static ArrayList<Module> modules = new ArrayList<Module>();
	
	public static void registerModules(Module module)
	{
		modules.add(module);
		Log.logPluginManager(Level.INFO, "Register Module: " + module.getModuleName() + ", " + module.getModuleVersion());
	}
	
	public void preInitModules()
	{
		for(Module module : modules)
		{
			if(module.getRequiredBoolean())
			{
				module.preInit();
			}
		}
	}
	
	public void postInitModules()
	{
		for(Module module : modules)
		{
			if(module.getRequiredBoolean())
			{
				module.postInit();
			}
		}
	}
	
	public void initModules()
	{
		for(Module module : modules)
		{
			if(module.getRequiredBoolean())
			{
				module.init();
				module.registerRecipes();
			}
		}
	}
	
	public void preInit()
	{
		preInitModules();
	}
	
	public void init()
	{
		initModules();
	}
	
	public void postInit()
	{
		postInitModules();
	}
	
}
