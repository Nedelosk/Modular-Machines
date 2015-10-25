package nedelosk.forestday.common.core.managers;

import static net.minecraftforge.oredict.OreDictionary.registerOre;

import net.minecraft.item.ItemStack;

public class OreDictionaryManager {

	public static void preInit()
	{
		//Tools
		registerOre("toolFile", FItemManager.File_Stone.item());
		registerOre("toolFile", FItemManager.File_Iron.item());
		registerOre("toolHammer", FItemManager.Hammer.item());
		registerOre("toolFile", FItemManager.File_Diamond.item());
		registerOre("toolCutter", FItemManager.Cutter.item());
		registerOre("toolKnife", FItemManager.Knife_Stone.item());
		
		registerOre("hardenedStarch", new ItemStack(FItemManager.Nature.item(), 1, 11));
		registerOre("gearWood", new ItemStack(FItemManager.Gears_Wood.item(), 1, 1));
		
		registerOre("ingotCopper", new ItemStack(FItemManager.Ingots.item(), 1, 0));
		registerOre("ingotTin", new ItemStack(FItemManager.Ingots.item(), 1, 1));
		registerOre("ingotSilver", new ItemStack(FItemManager.Ingots.item(), 1, 2));
		registerOre("ingotLead", new ItemStack(FItemManager.Ingots.item(), 1, 3));
		registerOre("ingotNickel", new ItemStack(FItemManager.Ingots.item(), 1, 4));
		registerOre("nuggetCopper", new ItemStack(FItemManager.Nuggets.item(), 1, 0));
		registerOre("nuggetTin", new ItemStack(FItemManager.Nuggets.item(), 1, 1));
		registerOre("nuggetSilver", new ItemStack(FItemManager.Nuggets.item(), 1, 2));
		registerOre("nuggetLead", new ItemStack(FItemManager.Nuggets.item(), 1, 3));
		registerOre("nuggetNickel", new ItemStack(FItemManager.Nuggets.item(), 1, 4));
		registerOre("nuggetIron", new ItemStack(FItemManager.Nuggets.item(), 1, 5));
		registerOre("oreCopper", new ItemStack(FBlockManager.Ore.item(), 1, 0));
		registerOre("oreTin", new ItemStack(FBlockManager.Ore.item(), 1, 1));
		registerOre("oreSilver", new ItemStack(FBlockManager.Ore.item(), 1, 2));
		registerOre("oreLead", new ItemStack(FBlockManager.Ore.item(), 1, 3));
		registerOre("oreNickel", new ItemStack(FBlockManager.Ore.item(), 1, 4));
		registerOre("oreRuby", new ItemStack(FBlockManager.Ore.item(), 1, 5));
		registerOre("gemRuby", new ItemStack(FItemManager.Gems.item(), 1, 0));
	}
	
}
