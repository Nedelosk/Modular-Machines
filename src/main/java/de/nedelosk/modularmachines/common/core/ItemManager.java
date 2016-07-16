package de.nedelosk.modularmachines.common.core;

import de.nedelosk.modularmachines.common.items.ItemComponent;
import de.nedelosk.modularmachines.common.items.ItemMetal;
import de.nedelosk.modularmachines.common.items.ItemModule;
import de.nedelosk.modularmachines.common.items.ItemModuleMeta;
import de.nedelosk.modularmachines.common.items.ItemTool.Material;
import de.nedelosk.modularmachines.common.items.ItemToolCrafting;
import net.minecraft.item.Item;

public class ItemManager {

	public static Item itemFileStone;
	public static Item itemFileIron;
	public static Item itemFileDiamond;
	public static Item itemCutter;
	public static Item itemHammer;
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
	public static Item itemDrawer;
	public static Item itemEngineRF;
	public static Item itemEngineEU;
	public static Item itemTurbineSteam;
	public static Item itemModuleCore;
	public static Item itemModules;
	private static Object[][] alloys = new Object[][] { new Object[]{ "Bronze", 0xCA9956 }, new Object[]{ "Invar", 0xA1A48C } };
	private static Object[][] default_metals = new Object[][] { new Object[]{ "Copper", 0xCC6410 }, new Object[]{ "Tin", 0xCACECF }, new Object[]{ "Silver", 0xE6FDFF }, new Object[]{ "Lead", 0x826C82 }, new Object[]{ "Nickel", 0xA9A283 }, new Object[]{ "Aluminum", -1 }, new Object[]{ "Steel", 0xA0A0A0 } };
	private static Object[][] vanilla = new Object[][] { new Object[]{ "Coal", 0x222020 }, new Object[]{ "Obsidian", 0x7E258C }, new Object[]{ "Iron", 0xDADADA }, new Object[]{ "Gold", 0xD3B95A }, new Object[]{ "Diamond", 0x68D2DA } };
	/* metal for ingot's, nugget's, block's */
	public static Object[][][] metals = new Object[][][] { default_metals, alloys};
	public static Object[][][] dusts = new Object[][][] { vanilla, default_metals, alloys};

	public static void registerItems() {
		itemFileStone = register(new ItemToolCrafting("file.stone", 50, 1, Material.Stone, 5));
		itemFileIron = register(new ItemToolCrafting("file.iron", 150, 2, Material.Iron, 2));
		itemFileDiamond = register(new ItemToolCrafting("file.diamond", 300, 3, Material.Diamond, 1));
		itemHammer = register(new ItemToolCrafting("hammer", 300, 1, Material.Iron, 15));
		itemCutter = register(new ItemToolCrafting("cutter", 250, 1, Material.Iron, 10));
		itemDusts = register(new ItemMetal("dusts", "dust", dusts));
		itemIngots = register(new ItemMetal("ingots", "ingot", metals));
		itemNuggets = register(new ItemMetal("nuggets", "nugget", metals));
		itemCompWires = register(new ItemComponent("wires"));
		itemCompRods = register(new ItemComponent("rods"));
		itemCompScrews = register(new ItemComponent("screws"));
		itemCompPlates = register(new ItemComponent("plates"));
		itemCompGears = register(new ItemComponent("gears"));
		itemCompSawBlades = register(new ItemComponent("saw_blades"));
		itemDrawer = register(new ItemModuleMeta("drawer", new String[] { "brick" }));
		itemTurbineSteam = register(new ItemModuleMeta("turbineSteam", new String[] { "iron", "bronze", "steel", "magmarium" }));
		itemModuleCore = register(new ItemModuleMeta("moduleCore", new String[] { "basic", "normal", "improved", "advanced" }));
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
		itemCompPlates.addMetaData(0xA1A48C, "invar", "Invar");
		itemCompPlates.addMetaData(0xD4E3E6, "aluminum", "Aluminum", "Aluminium");
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
