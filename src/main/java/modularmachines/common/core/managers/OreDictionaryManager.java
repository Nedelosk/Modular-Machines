package modularmachines.common.core.managers;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import modularmachines.common.blocks.BlockMetalBlock.Metals;
import modularmachines.common.items.ItemComponent;
import modularmachines.common.materials.EnumMaterial;
import modularmachines.common.materials.MaterialList;

import static net.minecraftforge.oredict.OreDictionary.registerOre;

public class OreDictionaryManager {
	
	public static void registerOres() {
		for (MaterialList metals : ItemManager.metals) {
			for (EnumMaterial material : metals) {
				for (String oreDict : material.getOreDicts()) {
					registerOre("ingot" + oreDict, ItemManager.itemIngots.getStack(oreDict));
					registerOre("nugget" + oreDict, ItemManager.itemNuggets.getStack(oreDict));
				}
			}
		}
		for (MaterialList metals : ItemManager.dusts) {
			for (EnumMaterial material : metals) {
				for (String oreDict : material.getOreDicts()) {
					registerOre("dust" + oreDict, ItemManager.itemDusts.getStack(oreDict));
				}
			}
		}
		registerOre("oreCopper", new ItemStack(BlockManager.blockOres, 1, 0));
		registerOre("oreTin", new ItemStack(BlockManager.blockOres, 1, 1));
		registerOre("oreSilver", new ItemStack(BlockManager.blockOres, 1, 2));
		registerOre("oreLead", new ItemStack(BlockManager.blockOres, 1, 3));
		registerOre("oreNickel", new ItemStack(BlockManager.blockOres, 1, 4));
		registerOre("oreAluminium", new ItemStack(BlockManager.blockOres, 1, 5));
		registerOre("oreAluminum", new ItemStack(BlockManager.blockOres, 1, 5));
		registerOre("blockObsidian", Blocks.OBSIDIAN);
		registerOre("plateStone", new ItemStack(ItemManager.itemCompPlates));
		registerOre("gearStone", new ItemStack(ItemManager.itemCompGears));
		registerOre("rodStone", new ItemStack(ItemManager.itemCompRods));
		registerOre("itemCoal", new ItemStack(Items.COAL));
		registerComponentOres("plate", ItemManager.itemCompPlates);
		registerComponentOres("rod", ItemManager.itemCompRods);
		registerComponentOres("gear", ItemManager.itemCompGears);
		for (Metals type : Metals.values()) {
			ItemStack stack = new ItemStack(BlockManager.blockMetalBlocks, 1, type.ordinal());
			for (String oreDict : type.oreDict) {
				registerOre("block" + oreDict, stack);
			}
		}
	}
	
	private static void registerComponentOres(String preFix, ItemComponent component) {
		for (EnumMaterial material : component.materials) {
			ItemStack stack = component.getStack(material);
			String[] oreDicts = material.getOreDicts();
			if (oreDicts != null) {
				for (String oreDict : oreDicts) {
					registerOre(preFix + oreDict, stack);
				}
			}
		}
	}
}
