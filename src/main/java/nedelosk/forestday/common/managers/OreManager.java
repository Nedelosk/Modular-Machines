package nedelosk.forestday.common.managers;

import nedelosk.forestday.common.registrys.FItems;
import net.minecraft.init.Blocks;
import net.minecraftforge.oredict.OreDictionary;

public class OreManager {

	public static void preInit()
	{
		OreDictionary.registerOre("obsidian", Blocks.obsidian);
		
		//Items
		
		
		//Tools
		OreDictionary.registerOre("tool_file", FItems.file_stone.item());
		OreDictionary.registerOre("tool_file", FItems.file_iron.item());
		OreDictionary.registerOre("tool_file", FItems.file_diamond.item());
	}
	
}
