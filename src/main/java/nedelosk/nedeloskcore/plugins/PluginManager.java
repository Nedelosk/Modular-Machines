package nedelosk.nedeloskcore.plugins;

import java.util.ArrayList;

import nedelosk.nedeloskcore.common.core.Log;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.Loader;

public class PluginManager {

	public static ArrayList<Plugin> plugins = new ArrayList<Plugin>();
	
	public static void registerPlugin(Plugin plugin)
	{
		plugins.add(plugin);
		Log.logPluginManager(Level.INFO, "Register Plugin: " + plugin.getRequiredMod());
	}
	
	public void preInitPlugins()
	{
		for(Plugin plugin : plugins)
		{
			if(Loader.isModLoaded(plugin.getRequiredMod()) || plugin.getRequiredMod() == null)
			{
				plugin.preInit();
			}
		}
	}
	
	public void postInitPlugins()
	{
		for(Plugin plugin : plugins)
		{
			if(Loader.isModLoaded(plugin.getRequiredMod()) || plugin.getRequiredMod() == null)
			{
				plugin.postInit();
			}
		}
	}
	
	public void initPlugins()
	{
		for(Plugin plugin : plugins)
		{
			if(Loader.isModLoaded(plugin.getRequiredMod()) || plugin.getRequiredMod() == null)
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
