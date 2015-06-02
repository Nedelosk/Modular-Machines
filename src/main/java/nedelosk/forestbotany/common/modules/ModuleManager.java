package nedelosk.forestbotany.common.modules;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

	public static List<Module> modules = new ArrayList<Module>();
	
	public static void preInit()
	{
		registerModule(new ModuleBotanist());
		for(Module module : modules)
		{
			module.preInit();
		}
	}
	
	public static void init()
	{
		for(Module module : modules)
		{
			module.init();
		}
	}
	
	public static void postInit()
	{
		for(Module module : modules)
		{
			module.postInit();
		}
	}
	
	public static void registerModule(Module module)
	{
		modules.add(module);
	}
	
}
