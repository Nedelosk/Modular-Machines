package nedelosk.nedeloskcore.plugins.basic;

import java.util.ArrayList;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.Loader;
import nedelosk.nedeloskcore.api.Log;

public class PluginManager {

	public ArrayList<Plugin> plugins = new ArrayList<Plugin>();
	
	public void registerPlugin(Plugin plugin)
	{
		plugins.add(plugin);
		Log.logPluginManager(Level.INFO, "Register Plugin: " + plugin.getRequiredMod());
	}
	
	public void preInitPlugins()
	{
		for(Plugin plugin : plugins)
		{
			if((Loader.isModLoaded(plugin.getRequiredMod()) || plugin.getRequiredMod() == null) && plugin.getConfigOption())
			{
				plugin.preInit();
			}
		}
	}
	
	public void postInitPlugins()
	{
		for(Plugin plugin : plugins)
		{
			if((Loader.isModLoaded(plugin.getRequiredMod()) || plugin.getRequiredMod() == null) && plugin.getConfigOption())
			{
				plugin.postInit();
			}
		}
	}
	
	public void initPlugins()
	{
		for(Plugin plugin : plugins)
		{
			if((Loader.isModLoaded(plugin.getRequiredMod()) || plugin.getRequiredMod() == null) && plugin.getConfigOption())
			{
				plugin.init();
				plugin.registerRecipes();
			}
		}
	}
	
	public void preInit()
	{
		preInitPlugins();
	}
	
	public void init()
	{
		initPlugins();
	}
	
	public void postInit()
	{
		postInitPlugins();
	}
	
}
