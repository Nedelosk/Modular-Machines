package nedelosk.forestday.common.managers;

import nedelosk.forestday.common.registrys.FItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreManager {

	public static void preInit()
	{
		//Tools
		OreDictionary.registerOre("toolFile", FItems.file_stone.item());
		OreDictionary.registerOre("toolFile", FItems.file_iron.item());
		OreDictionary.registerOre("toolHammer", FItems.hammer.item());
		OreDictionary.registerOre("toolFile", FItems.file_diamond.item());
		OreDictionary.registerOre("toolCutter", FItems.cutter.item());
		
		OreDictionary.registerOre("hardenedStarch", new ItemStack(FItems.nature.item(), 1, 11));
	}
	
}
