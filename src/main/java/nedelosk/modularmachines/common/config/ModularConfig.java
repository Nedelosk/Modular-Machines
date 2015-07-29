package nedelosk.modularmachines.common.config;

import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.module.IModule;
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
		
		energyManagerCapacitorTiers = config.get("Capacitor Tiers", "Capacitor", new int[]{ 1, 2, 5, 8}).getIntList();
		
		save();
	}
	
	public static void postInit()
	{
		load();
		Configuration config = ModularMachines.config;
		for(IModule module : ModularMachinesApi.getModules().values())
		{
			if(!config.get("Modules", module.getName(), true).getBoolean())
				ModularMachinesApi.getModules().remove(module.getName(), module);
		}
		save();
	}
	
	public static void save()
	{
		ModularMachines.config.save();
	}
	
	public static int[] energyManagerCapacitorTiers;
	
}
