package nedelosk.modularmachines.common.modular.config;

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
	
	public static void save()
	{
		ModularMachines.config.save();
	}
	
	public static int[] energyManagerCapacitorTiers;
	
}
