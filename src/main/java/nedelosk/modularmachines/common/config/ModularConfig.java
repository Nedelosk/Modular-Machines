package nedelosk.modularmachines.common.config;

import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.utils.ModularManager;
import nedelosk.modularmachines.common.ModularMachines;
import net.minecraftforge.common.config.Configuration;

public class ModularConfig {

	public static void load()
	{
		ModularMachines.config.load();
	}
	
	public static void preInit()
	{
		load();
		
		Configuration config = ModularMachines.config;
		
		generateAluminiumOre = config.get("OreGen", "Aluminium", true).getBoolean();
		generateColumbiteOre = config.get("OreGen", "Columbite", true).getBoolean();
		
		pluginTinkers = config.get("Plugins", "Tinkers Construct", true).getBoolean();
		pluginEnderIO = config.get("Plugins", "EnderIO", true).getBoolean();
		pluginThermalExpansion = config.get("Plugins", "Thermal Expansion", true).getBoolean();
		pluginWaila = config.get("Plugins", "Waila", true).getBoolean();
		pluginMineTweaker3 = config.get("Plugins", "Mine Tweaker 3", true).getBoolean();
		
		save();
	}
	
	public static void postInit()
	{
		load();
		Configuration config = ModularMachines.config;
		for(IModule module : ModularManager.getModules().values())
		{
			if(!config.get("Modules", module.getName(), true).getBoolean())
				ModularManager.getModules().remove(module.getName(), module);
		}
		save();
	}
	
	public static void save()
	{
		ModularMachines.config.save();
	}
	
	public static boolean generateColumbiteOre;
	public static boolean generateAluminiumOre;
	
	public static boolean pluginTinkers;
	public static boolean pluginEnderIO;
	public static boolean pluginThermalExpansion;
	public static boolean pluginWaila;
	public static boolean pluginMineTweaker3;
}
