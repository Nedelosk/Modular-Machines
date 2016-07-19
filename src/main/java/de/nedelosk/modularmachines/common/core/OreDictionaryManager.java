package de.nedelosk.modularmachines.common.core;

import static net.minecraftforge.oredict.OreDictionary.registerOre;

import java.util.List;

import de.nedelosk.modularmachines.api.material.IMetalMaterial;
import de.nedelosk.modularmachines.common.blocks.BlockMetalBlock.ComponentTypes;
import de.nedelosk.modularmachines.common.items.ItemComponent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryManager {

	public static void registerOres() {
		registerOre("toolFile", new ItemStack(ItemManager.itemFileIron, 1, OreDictionary.WILDCARD_VALUE));
		registerOre("toolFile", new ItemStack(ItemManager.itemFileDiamond, 1, OreDictionary.WILDCARD_VALUE));
		registerOre("toolHammer", new ItemStack(ItemManager.itemHammer, 1, OreDictionary.WILDCARD_VALUE));
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
		registerComponentOres("plate", ItemManager.itemCompPlates);
		registerComponentOres("rod", ItemManager.itemCompRods);
		registerComponentOres("screw", ItemManager.itemCompScrews);
		registerComponentOres("gear", ItemManager.itemCompGears);
		registerComponentOres("wire", ItemManager.itemCompWires);
		registerComponentOres("sawBlade", ItemManager.itemCompSawBlades);
		for(ComponentTypes type : ComponentTypes.values()) {
			ItemStack stack = new ItemStack(BlockManager.blockMetalBlocks, 1, type.ordinal());
			for(String oreDict : type.oreDict) {
				registerOre("block" + oreDict, stack);
			}
		}
	}

	private static void registerComponentOres(String preFix, ItemComponent component){
		List<IMetalMaterial> materials = component.materials;
		for(int i = 0; i < materials.size(); i++) {
			ItemStack stack = new ItemStack(component, 1, i);
			String[] oreDicts = materials.get(i).getOreDicts();
			if (oreDicts != null) {
				for(String oreDict : oreDicts) {
					registerOre(preFix + oreDict, stack);
				}
			}
		}
	}
}
