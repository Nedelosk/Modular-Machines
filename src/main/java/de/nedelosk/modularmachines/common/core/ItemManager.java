package de.nedelosk.modularmachines.common.core;

import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.common.items.ItemCasing;
import de.nedelosk.modularmachines.common.items.ItemComponent;
import de.nedelosk.modularmachines.common.items.ItemMetal;
import de.nedelosk.modularmachines.common.items.ItemModule;
import de.nedelosk.modularmachines.common.items.ItemModuleMeta;
import de.nedelosk.modularmachines.common.items.ItemToolCrafting;
import de.nedelosk.modularmachines.common.items.blocks.ItemBlockChassi;
import net.minecraft.item.Item;

public class ItemManager {

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
	public static Item itemEngineSteam;
	public static Item itemEngineRF;
	public static Item itemEngineEU;
	public static Item itemTurbineSteam;
	public static Item itemModuleCore;
	public static Item itemModules;
	public static Item itemCasings;
	public static Item itemChassi;
	private static Object[][] alloys = new Object[][] { new Object[]{ "Bronze", 0xCA9956 }, new Object[]{ "Invar", 0xA1A48C } };
	private static Object[][] default_metals = new Object[][] { new Object[]{ "Copper", 0xCC6410 }, new Object[]{ "Tin", 0xCACECF }, new Object[]{ "Silver", 0xE6FDFF }, new Object[]{ "Lead", 0x826C82 }, new Object[]{ "Nickel", 0xA9A283 }, new Object[]{ "Aluminum", -1 }, new Object[]{ "Steel", 0xA0A0A0 } };
	private static Object[][] vanilla = new Object[][] { new Object[]{ "Coal", 0x222020 }, new Object[]{ "Obsidian", 0x7E258C }, new Object[]{ "Iron", 0xDADADA }, new Object[]{ "Gold", 0xD3B95A }, new Object[]{ "Diamond", 0x68D2DA } };
	/* metal for ingot's, nugget's, block's */
	public static Object[][][] metals = new Object[][][] { default_metals, alloys};
	public static Object[][][] dusts = new Object[][][] { vanilla, default_metals, alloys};

	public static void registerItems() {
		itemCasings = register(new ItemCasing());
		itemChassi = Registry.register(new ItemBlockChassi(BlockManager.blockModular));
		itemFileIron = register(new ItemToolCrafting("file.iron", 150, 2));
		itemFileDiamond = register(new ItemToolCrafting("file.diamond", 300, 1));
		itemHammer = register(new ItemToolCrafting("hammer", 300, 15));
		itemCutter = register(new ItemToolCrafting("cutter", 250, 10));
		itemDusts = register(new ItemMetal("dusts", "dust", dusts));
		itemIngots = register(new ItemMetal("ingots", "ingot", metals));
		itemNuggets = register(new ItemMetal("nuggets", "nugget", metals));
		itemCompWires = register(new ItemComponent("wires", EnumMetalMaterials.IRON, EnumMetalMaterials.TIN, EnumMetalMaterials.COPPER, EnumMetalMaterials.BRONZE, EnumMetalMaterials.STEEL));
		itemCompRods = register(new ItemComponent("rods", EnumMetalMaterials.IRON, EnumMetalMaterials.TIN, EnumMetalMaterials.COPPER, EnumMetalMaterials.BRONZE, EnumMetalMaterials.STEEL));
		itemCompScrews = register(new ItemComponent("screws", EnumMetalMaterials.IRON, EnumMetalMaterials.BRONZE, EnumMetalMaterials.STEEL));
		itemCompPlates = register(new ItemComponent("plates", EnumMetalMaterials.IRON, EnumMetalMaterials.TIN, EnumMetalMaterials.COPPER, EnumMetalMaterials.BRONZE, EnumMetalMaterials.STEEL, EnumMetalMaterials.ALUMINIUM, EnumMetalMaterials.INVAR));
		itemCompGears = register(new ItemComponent("gears", EnumMetalMaterials.IRON, EnumMetalMaterials.BRONZE, EnumMetalMaterials.STEEL));
		itemCompSawBlades = register(new ItemComponent("saw_blades", EnumMetalMaterials.IRON, EnumMetalMaterials.BRONZE, EnumMetalMaterials.STEEL));
		itemDrawer = register(new ItemModuleMeta("drawer", new String[] { "brick_large", "brick_small" }));
		itemEngineSteam = register(new ItemModuleMeta("engineSteam", new String[] { "iron", "bronze", "steel", "magmarium" }));
		itemTurbineSteam = register(new ItemModuleMeta("turbineSteam", new String[] { "iron", "bronze", "steel", "magmarium" }));
		itemModuleCore = register(new ItemModuleMeta("moduleCore", new String[] { "basic", "normal", "improved", "advanced" }));
		itemModules = register(new ItemModule());
	}

	public static <I extends Item> I register(I item) {
		Registry.register(item, item.getUnlocalizedName().replace("forest.item.", "").replace("item.", ""));
		return item;
	}
}
