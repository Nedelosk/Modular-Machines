package de.nedelosk.modularmachines.common.core;

import static net.minecraftforge.oredict.OreDictionary.registerOre;

import de.nedelosk.modularmachines.common.blocks.BlockMetalBlock.ComponentTypes;
import de.nedelosk.modularmachines.common.items.ItemComponent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryManager {

	public static void registerOres() {
		registerOre("toolFile", new ItemStack(ItemManager.itemFileStone, 1, OreDictionary.WILDCARD_VALUE));
		registerOre("toolFile", new ItemStack(ItemManager.itemFileIron, 1, OreDictionary.WILDCARD_VALUE));
		registerOre("toolHammer", new ItemStack(ItemManager.itemHammer, 1, OreDictionary.WILDCARD_VALUE));
		registerOre("toolFile", new ItemStack(ItemManager.itemFileDiamond, 1, OreDictionary.WILDCARD_VALUE));
		registerOre("toolCutter", new ItemStack(ItemManager.itemCutter, 1, OreDictionary.WILDCARD_VALUE));
		for(int m = 0; m < ItemManager.metals.length; m++) {
			Object[][] metal = ItemManager.metals[m];
			for(int i = 0; i < metal.length; ++i) {
				registerOre("ingot" + metal[i][0], new ItemStack(ItemManager.itemIngots, 1, m * 10 + i));
				registerOre("nugget" + metal[i][0], new ItemStack(ItemManager.itemNuggets, 1, m * 10 + i));
			}
		}
		for(int d = 0; d < ItemManager.dusts.length; d++) {
			Object[][] dust = ItemManager.dusts[d];
			for(int i = 0; i < dust.length; ++i) {
				registerOre("dust" + dust[i][0], new ItemStack(ItemManager.itemDusts, 1, d * 10 + i));
			}
		}
		registerOre("ingotAluminium", new ItemStack(ItemManager.itemIngots, 1, 5));
		registerOre("nuggetAluminium", new ItemStack(ItemManager.itemNuggets, 1, 5));
		registerOre("dustAluminium", new ItemStack(ItemManager.itemDusts, 1, 15));
		registerOre("oreCopper", new ItemStack(BlockManager.blockOres, 1, 0));
		registerOre("oreTin", new ItemStack(BlockManager.blockOres, 1, 1));
		registerOre("oreSilver", new ItemStack(BlockManager.blockOres, 1, 2));
		registerOre("oreLead", new ItemStack(BlockManager.blockOres, 1, 3));
		registerOre("oreNickel", new ItemStack(BlockManager.blockOres, 1, 4));
		registerOre("oreAluminium", new ItemStack(BlockManager.blockOres, 1, 5));
		registerOre("oreAluminum", new ItemStack(BlockManager.blockOres, 1, 5));
		registerOre("blockObsidian", Blocks.OBSIDIAN);
		registerOre("plateStone", new ItemStack(ItemManager.itemCompPlates));
		registerOre("sawBladeStone", new ItemStack(ItemManager.itemCompSawBlades));
		registerOre("gearStone", new ItemStack(ItemManager.itemCompGears));
		registerOre("rodStone", new ItemStack(ItemManager.itemCompRods));
		registerOre("itemCoal", new ItemStack(Items.COAL));
		for(int i = 0; i < ItemManager.itemCompPlates.metas.size(); i++) {
			ItemStack stack = new ItemStack(ItemManager.itemCompPlates, 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			for(String oreDict : (String[]) component.metas.get(i).get(2)) {
				registerOre("plate" + oreDict, stack);
			}
		}
		for(int i = 0; i < ItemManager.itemCompRods.metas.size(); i++) {
			ItemStack stack = new ItemStack(ItemManager.itemCompRods, 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.metas.get(i).get(2) != null) {
				for(String oreDict : (String[]) component.metas.get(i).get(2)) {
					registerOre("rod" + oreDict, stack);
				}
			}
		}
		for(int i = 0; i < ItemManager.itemCompScrews.metas.size(); i++) {
			ItemStack stack = new ItemStack(ItemManager.itemCompScrews, 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.metas.get(i).get(2) != null) {
				for(String oreDict : (String[]) component.metas.get(i).get(2)) {
					registerOre("screw" + oreDict, stack);
				}
			}
		}
		for(int i = 0; i < ItemManager.itemCompGears.metas.size(); i++) {
			ItemStack stack = new ItemStack(ItemManager.itemCompGears, 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.metas.get(i).get(2) != null) {
				for(String oreDict : (String[]) component.metas.get(i).get(2)) {
					registerOre("gear" + oreDict, stack);
				}
			}
		}
		for(int i = 0; i < ItemManager.itemCompWires.metas.size(); i++) {
			ItemStack stack = new ItemStack(ItemManager.itemCompWires, 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.metas.get(i).get(2) != null) {
				for(String oreDict : (String[]) component.metas.get(i).get(2)) {
					registerOre("wire" + oreDict, stack);
				}
			}
		}
		for(int i = 0; i < ItemManager.itemCompSawBlades.metas.size(); i++) {
			ItemStack stack = new ItemStack(ItemManager.itemCompSawBlades, 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.metas.get(i).get(2) != null) {
				for(String oreDict : (String[]) component.metas.get(i).get(2)) {
					registerOre("sawBlade" + oreDict, stack);
				}
			}
		}
		for(ComponentTypes type : ComponentTypes.values()) {
			ItemStack stack = new ItemStack(BlockManager.blockMetalBlocks, 1, type.ordinal());
			for(String oreDict : type.oreDict) {
				registerOre("block" + oreDict, stack);
			}
		}
	}
}
