package nedelosk.forestday.common.managers;

import nedelosk.forestday.common.registrys.ForestdayBlockRegistry;
import nedelosk.forestday.common.registrys.ForestdayItemRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictManager {

	public static void registerOreDict()
	{
		OreDictionary.registerOre("dustBark", new ItemStack(ForestdayItemRegistry.nature, 1, 0));
		OreDictionary.registerOre("pulpWood", new ItemStack(ForestdayItemRegistry.nature, 1, 1));
		OreDictionary.registerOre("dustWood", new ItemStack(ForestdayItemRegistry.nature, 1, 1));
		OreDictionary.registerOre("itemRubber", new ItemStack(ForestdayItemRegistry.nature, 1, 2));
		OreDictionary.registerOre("itemResin", new ItemStack(ForestdayItemRegistry.nature, 1, 2));
		OreDictionary.registerOre("brickPeat", new ItemStack(ForestdayItemRegistry.nature, 1, 3));
		
		OreDictionary.registerOre("blockCopper", new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 0));
		OreDictionary.registerOre("blockTin", new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 1));
		OreDictionary.registerOre("blockBronze", new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 2));
		OreDictionary.registerOre("blockAlloyRed", new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 3));
		OreDictionary.registerOre("blockAlloyBlue", new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 4));
		OreDictionary.registerOre("blockAlloyBlueDark", new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 5));
		OreDictionary.registerOre("blockAlloyYellow", new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 6));
		OreDictionary.registerOre("blockAlloyBrown", new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 7));
		OreDictionary.registerOre("blockAlloyGreen", new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 8));
		OreDictionary.registerOre("blockSteel", new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 9));
		OreDictionary.registerOre("blockLightSteel", new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 10));
		OreDictionary.registerOre("blockDarkSteel", new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 11));
		OreDictionary.registerOre("blockObsidian", new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 12));
		OreDictionary.registerOre("blockEnderium", new ItemStack(ForestdayBlockRegistry.metalBlock, 1, 13));
		
		OreDictionary.registerOre("ingotCopper", new ItemStack(ForestdayItemRegistry.ingots, 1, 0));
		OreDictionary.registerOre("ingotTin", new ItemStack(ForestdayItemRegistry.ingots, 1, 1));
		OreDictionary.registerOre("ingotBronze", new ItemStack(ForestdayItemRegistry.ingots, 1, 2));
		OreDictionary.registerOre("ingotAlloyRed", new ItemStack(ForestdayItemRegistry.ingots, 1, 3));
		OreDictionary.registerOre("ingotAlloyBlue", new ItemStack(ForestdayItemRegistry.ingots, 1, 4));
		OreDictionary.registerOre("ingotAlloyBlueDark", new ItemStack(ForestdayItemRegistry.ingots, 1, 5));
		OreDictionary.registerOre("ingotAlloyYellow", new ItemStack(ForestdayItemRegistry.ingots, 1, 6));
		OreDictionary.registerOre("ingotAlloyBrown", new ItemStack(ForestdayItemRegistry.ingots, 1, 7));
		OreDictionary.registerOre("ingotAlloyGreen", new ItemStack(ForestdayItemRegistry.ingots, 1, 8));
		OreDictionary.registerOre("ingotSteel", new ItemStack(ForestdayItemRegistry.ingots, 1, 9));
		OreDictionary.registerOre("ingotLightSteel", new ItemStack(ForestdayItemRegistry.ingots, 1, 10));
		OreDictionary.registerOre("ingotDarkSteel", new ItemStack(ForestdayItemRegistry.ingots, 1, 11));
		OreDictionary.registerOre("ingotObsidian", new ItemStack(ForestdayItemRegistry.ingots, 1, 12));
		OreDictionary.registerOre("ingotEnderium", new ItemStack(ForestdayItemRegistry.ingots, 1, 13));
		
		OreDictionary.registerOre("nuggetCopper", new ItemStack(ForestdayItemRegistry.nuggets, 1, 0));
		OreDictionary.registerOre("nuggetTin", new ItemStack(ForestdayItemRegistry.nuggets, 1, 1));
		OreDictionary.registerOre("nuggetBronze", new ItemStack(ForestdayItemRegistry.nuggets, 1, 2));
		
		OreDictionary.registerOre("oreCopper", new ItemStack(ForestdayBlockRegistry.oreBlock, 1, 0));
		OreDictionary.registerOre("oreTin", new ItemStack(ForestdayBlockRegistry.oreBlock, 1, 1));
		OreDictionary.registerOre("limestone", new ItemStack(ForestdayBlockRegistry.oreBlock, 1, 2));
		
		OreDictionary.registerOre("gearWood", new ItemStack(ForestdayItemRegistry.gearWood, 1, 1));
		
		OreDictionary.registerOre("dustCopper", new ItemStack(ForestdayItemRegistry.dust, 1, 0));
		OreDictionary.registerOre("dustTin", new ItemStack(ForestdayItemRegistry.dust, 1, 1));
		OreDictionary.registerOre("dustIron", new ItemStack(ForestdayItemRegistry.dust, 1, 2));
		OreDictionary.registerOre("dustGold", new ItemStack(ForestdayItemRegistry.dust, 1, 3));
		OreDictionary.registerOre("dustStone", new ItemStack(ForestdayItemRegistry.dust, 1, 4));
		OreDictionary.registerOre("dustLapis", new ItemStack(ForestdayItemRegistry.dust, 1, 5));
		OreDictionary.registerOre("dustDiamond", new ItemStack(ForestdayItemRegistry.dust, 1, 6));
		OreDictionary.registerOre("dustEmerald", new ItemStack(ForestdayItemRegistry.dust, 1, 7));
		OreDictionary.registerOre("dustCoal", new ItemStack(ForestdayItemRegistry.dust, 1, 8));
		
		OreDictionary.registerOre("plateSteel", new ItemStack(ForestdayItemRegistry.buildPlates, 1, 0));
		OreDictionary.registerOre("plateLightSteel", new ItemStack(ForestdayItemRegistry.buildPlates, 1, 1));
		OreDictionary.registerOre("plateDarkSteel", new ItemStack(ForestdayItemRegistry.buildPlates, 1, 2));
		OreDictionary.registerOre("plateObsidian", new ItemStack(ForestdayItemRegistry.buildPlates, 1, 3));
		OreDictionary.registerOre("plateEnderium", new ItemStack(ForestdayItemRegistry.buildPlates, 1, 4));
		
		//Tools Parts
		
		OreDictionary.registerOre("toolpartFileHandleWood", new ItemStack(ForestdayItemRegistry.toolparts, 1, 0));
		OreDictionary.registerOre("toolpartFileHandleImproved", new ItemStack(ForestdayItemRegistry.toolparts, 1, 1));
		OreDictionary.registerOre("toolpartFileHeadStone", new ItemStack(ForestdayItemRegistry.toolparts, 1, 2));
		OreDictionary.registerOre("toolpartFileHeadIron", new ItemStack(ForestdayItemRegistry.toolparts, 1, 3));
		OreDictionary.registerOre("toolpartFileHeadDiamond", new ItemStack(ForestdayItemRegistry.toolparts, 1, 4));
		
		//Tools
		OreDictionary.registerOre("toolFile", new ItemStack(ForestdayItemRegistry.file, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("toolFile", new ItemStack(ForestdayItemRegistry.fileDiamond, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("toolFile", new ItemStack(ForestdayItemRegistry.fileIron, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("toolKnife", new ItemStack(ForestdayItemRegistry.toolKnife, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("toolAdze", new ItemStack(ForestdayItemRegistry.adze, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("toolAdzeLong", new ItemStack(ForestdayItemRegistry.adzeLong, 1, OreDictionary.WILDCARD_VALUE));
		
		OreDictionary.registerOre("rodeSteel", new ItemStack(ForestdayItemRegistry.rods, 1, 2));
		OreDictionary.registerOre("rodeIron", new ItemStack(ForestdayItemRegistry.rods, 1, 1));
		OreDictionary.registerOre("rodeBronze", new ItemStack(ForestdayItemRegistry.rods, 1, 0));
		
	}
	
}
