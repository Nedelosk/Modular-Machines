package de.nedelosk.modularmachines.common.core;

import de.nedelosk.modularmachines.common.items.ItemComponent;
import de.nedelosk.modularmachines.common.items.ItemCutter;
import de.nedelosk.modularmachines.common.items.ItemFile;
import de.nedelosk.modularmachines.common.items.ItemMetal;
import de.nedelosk.modularmachines.common.items.ItemModule;
import de.nedelosk.modularmachines.common.items.ItemModuleMeta;
import de.nedelosk.modularmachines.common.items.ItemNature;
import de.nedelosk.modularmachines.common.items.ItemTool.Material;
import de.nedelosk.modularmachines.common.items.ItemToolCrafting;
import de.nedelosk.modularmachines.common.items.ItemToolParts;
import net.minecraft.item.Item;

public class ItemManager {

	public static Item itemNature;
	public static Item itemToolParts;
	public static Item itemFileStone;
	public static Item itemFileIron;
	public static Item itemFileDiamond;
	public static Item itemKnifeStone;
	public static Item itemCutter;
	public static Item itemHammer;
	public static Item itemAdze;
	public static Item itemAdzeLong;
	public static Item itemDusts;
	public static Item itemIngots;
	public static Item itemNuggets;
	public static Item itemMetallic;
	public static ItemComponent itemCompWires;
	public static ItemComponent itemCompRods;
	public static ItemComponent itemCompGears;
	public static ItemComponent itemCompPlates;
	public static ItemComponent itemCompScrews;
	public static ItemComponent itemCompSawBlades;
	public static Item itemCapacitors;
	public static Item itemEngine;
	public static Item itemHeater;
	public static Item itemModules;
	private static String[] alloys = new String[] { "Bronze", "Invar" };
	private static String[] steels = new String[] { "Steel", "White_Steel", "Gray_Steel" };
	private static String[] default_metals = new String[] { "Copper", "Tin", "Silver", "Lead", "Nickel", "Aluminum", "Niobium", "Tantalum" };
	private static String[] only_dust = new String[] { "Columbite", "Ruby" };
	private static String[] vanilla = new String[] { "Coal", "Obsidian", "Iron", "Gold", "Diamond" };
	/* metal for ingots, nuggets, blocks */
	public static String[][] metals = new String[][] { default_metals, alloys, steels };
	public static String[][] dusts = new String[][] { vanilla, only_dust, default_metals, alloys, steels };

	public static void registerItems() {
		itemNature = register(new ItemNature());
		itemToolParts = register(new ItemToolParts());
		itemFileStone = register(new ItemFile(50, 5, "file.stone", "file_stone", 1, Material.Stone));
		itemFileIron = register(new ItemFile(150, 2, "file.iron", "file", 2, Material.Iron));
		itemFileDiamond = register(new ItemFile(300, 1, "file.diamond", "file_diamond", 3, Material.Diamond));
		itemHammer = register(new ItemToolCrafting("hammer", 300, 0, Material.Iron, "hammer", 15));
		itemKnifeStone = register(new ItemToolCrafting("knife", 200, 1, Material.Iron, "knife", 5));
		itemCutter = register(new ItemCutter(250, 10, "cutter", "cutter", 1, Material.Iron));
		itemAdze = register(new ItemToolCrafting("adze", 175, 1, Material.Stone, "adze", 3));
		itemAdzeLong = register(new ItemToolCrafting("adze.long", 175, 1, Material.Stone, "adze.long", 3));
		itemDusts = register(new ItemMetal("dusts", "dust", dusts));
		itemIngots = register(new ItemMetal("ingots", "ingot", metals));
		itemNuggets = register(new ItemMetal("nuggets", "nugget", metals));
		itemCompWires = register(new ItemComponent("wires"));
		itemCompRods = register(new ItemComponent("rods"));
		itemCompScrews = register(new ItemComponent("screws"));
		itemCompPlates = register(new ItemComponent("plates"));
		itemCompGears = register(new ItemComponent("gears"));
		itemCompSawBlades = register(new ItemComponent("saw_blades"));
		itemCapacitors = register(
				new ItemModuleMeta("capacitor", new String[] { "metal_paper", "electrolyte_niobium", "electrolyte_tantalum", "double_layer" }));
		itemEngine = register(new ItemModuleMeta("engine", new String[] { "stone", "iron", "bronze", "steel", "magmarium" }));
		itemHeater = register(new ItemModuleMeta("heater", new String[] { "stone", "iron", "bronze", "steel", "magmarium" }));
		itemModules = register(new ItemModule());
		itemCompWires.addMetaData(0xDADADA, "iron", "Iron");
		itemCompWires.addMetaData(0xCACECF, "tin", "Tin");
		itemCompWires.addMetaData(0xCC6410, "copper", "Copper");
		itemCompWires.addMetaData(0xCA9956, "bronze", "Bronze");
		itemCompWires.addMetaData(0xA0A0A0, "steel", "Steel");
		itemCompRods.addMetaData(0x7F7F7F, "stone");
		itemCompRods.addMetaData(0xDADADA, "iron", "Iron");
		itemCompRods.addMetaData(0xCACECF, "tin", "Tin");
		itemCompRods.addMetaData(0xCC6410, "copper", "Copper");
		itemCompRods.addMetaData(0xCA9956, "bronze", "Bronze");
		itemCompRods.addMetaData(0xA0A0A0, "steel", "Steel");
		itemCompRods.addMetaData(0xD4E3E6, "plastic", "Plastic");
		itemCompScrews.addMetaData(0xDADADA, "iron", "Iron");
		itemCompScrews.addMetaData(0xCACECF, "tin", "Tin");
		itemCompScrews.addMetaData(0xCC6410, "copper", "Copper");
		itemCompScrews.addMetaData(0xCA9956, "bronze", "Bronze");
		itemCompScrews.addMetaData(0xA0A0A0, "steel", "Steel");
		itemCompGears.addMetaData(0x7F7F7F, "stone");
		itemCompGears.addMetaData(0xDADADA, "iron", "Iron");
		itemCompGears.addMetaData(0xCACECF, "tin", "Tin");
		itemCompGears.addMetaData(0xCC6410, "copper", "Copper");
		itemCompGears.addMetaData(0xCA9956, "bronze", "Bronze");
		itemCompGears.addMetaData(0xA0A0A0, "steel", "Steel");
		itemCompPlates.addMetaData(0x7F7F7F, "stone");
		itemCompPlates.addMetaData(0xDADADA, "iron", "Iron");
		itemCompPlates.addMetaData(0xCACECF, "tin", "Tin");
		itemCompPlates.addMetaData(0xCC6410, "copper", "Copper");
		itemCompPlates.addMetaData(0xCA9956, "bronze", "Bronze");
		itemCompPlates.addMetaData(0xA0A0A0, "steel", "Steel");
		itemCompPlates.addMetaData(0xD4E3E6, "plastic", "Plastic");
		itemCompPlates.addMetaData(0xA1A48C, "invar", "Invar");
		itemCompPlates.addMetaData(0xA1A48C, "aluminum", "Aluminum", "Aluminium");
		itemCompSawBlades.addMetaData(0x7F7F7F, "stone");
		itemCompSawBlades.addMetaData(0xDADADA, "iron", "Iron");
		itemCompSawBlades.addMetaData(0xCA9956, "bronze", "Bronze");
		itemCompSawBlades.addMetaData(0xA0A0A0, "steel", "Steel");
	}

	public static <I extends Item> I register(I item) {
		Registry.register(item, item.getUnlocalizedName().replace("forest.item.", "").replace("item.", ""));
		return item;
	}
}
