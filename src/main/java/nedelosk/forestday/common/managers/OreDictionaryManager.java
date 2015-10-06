package nedelosk.forestday.common.managers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryManager {

	public static void preInit()
	{
		//Tools
		OreDictionary.registerOre("toolFile", FItemManager.file_stone.item());
		OreDictionary.registerOre("toolFile", FItemManager.file_iron.item());
		OreDictionary.registerOre("toolHammer", FItemManager.hammer.item());
		OreDictionary.registerOre("toolFile", FItemManager.file_diamond.item());
		OreDictionary.registerOre("toolCutter", FItemManager.cutter.item());
		
		OreDictionary.registerOre("hardenedStarch", new ItemStack(FItemManager.nature.item(), 1, 11));
	}
	
}
