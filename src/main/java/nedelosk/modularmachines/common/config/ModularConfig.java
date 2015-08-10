package nedelosk.modularmachines.common.config;

import java.util.HashMap;
import java.util.Iterator;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.module.IModule;
import nedelosk.modularmachines.common.ModularMachines;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Language;
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
		
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
        Iterator iterator = Minecraft.getMinecraft().getLanguageManager().getLanguages().iterator();

        while (iterator.hasNext())
        {
        	Language language = (Language) iterator.next();
        	if(language.getLanguageCode().equals("en_US"))
        		activeLanguages.put(language.getLanguageCode(), config.get("techtreepage languages", language.getLanguageCode(), true).getBoolean());
        	activeLanguages.put(language.getLanguageCode(), config.get("techtreepage languages", language.getLanguageCode(), false).getBoolean());
        }
		}
		
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
	
	public static HashMap<String, Boolean> activeLanguages = new HashMap<>();
	public static boolean generateColumbiteOre;
	public static boolean generateAluminiumOre;
}
