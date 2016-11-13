package modularmachines.common.core.managers;

import net.minecraft.item.Item;

import modularmachines.api.material.EnumMetalMaterials;
import modularmachines.api.material.EnumVanillaMaterials;
import modularmachines.api.material.IMetalMaterial;
import modularmachines.api.material.MaterialList;
import modularmachines.api.modules.ModuleManager;
import modularmachines.common.core.Registry;
import modularmachines.common.items.ItemCasing;
import modularmachines.common.items.ItemComponent;
import modularmachines.common.items.ItemMetal;
import modularmachines.common.items.ItemModule;
import modularmachines.common.items.ItemModuleContainer;
import modularmachines.common.items.ItemModuleHolder;
import modularmachines.common.items.ItemModuleMeta;
import modularmachines.common.items.ItemToolCrafting;
import modularmachines.common.items.ItemWrench;
import modularmachines.common.items.blocks.ItemBlockChassis;

public class ItemManager {

	public static Item itemFileIron;
	public static Item itemFileDiamond;
	public static Item itemCutter;
	public static Item itemHammer;
	public static ItemMetal itemDusts;
	public static ItemMetal itemIngots;
	public static ItemMetal itemNuggets;
	public static ItemComponent itemCompWires;
	public static ItemComponent itemCompRods;
	public static ItemComponent itemCompGears;
	public static ItemComponent itemCompPlates;
	public static ItemComponent itemCompScrews;
	public static Item itemModuleStorageLarge;
	public static Item itemModuleStorageSmall;
	public static Item itemEngineSteam;
	public static Item itemEngineElectric;
	public static Item itemTurbineSteam;
	public static Item itemModuleCore;
	public static Item itemModules;
	public static Item itemModuleHolder;
	public static Item itemModuleContainer;
	public static Item itemCasings;
	public static Item itemChassis;
	public static Item itemWrench;
	public static Item itemPhotovoltaic;
	public static Item itemSolarBoiler;
	public static MaterialList<IMetalMaterial> alloys = new MaterialList(EnumMetalMaterials.BRONZE, EnumMetalMaterials.INVAR);
	public static MaterialList<IMetalMaterial> default_metals = new MaterialList(EnumMetalMaterials.COPPER, EnumMetalMaterials.TIN, EnumMetalMaterials.SILVER, EnumMetalMaterials.LEAD, EnumMetalMaterials.NICKEL, EnumMetalMaterials.ALUMINIUM,
			EnumMetalMaterials.STEEL);
	public static MaterialList<IMetalMaterial> vanilla = new MaterialList(EnumVanillaMaterials.COAL, EnumVanillaMaterials.OBSIDIAN, EnumMetalMaterials.IRON, EnumMetalMaterials.GOLD, EnumVanillaMaterials.DIAMOND, EnumVanillaMaterials.DIRT);
	/* metal for ingot's, nugget's, block's */
	public static MaterialList<IMetalMaterial>[] metals = new MaterialList[] { default_metals, alloys };
	public static MaterialList<IMetalMaterial>[] dusts = new MaterialList[] { vanilla, default_metals, alloys };

	public static void registerItems() {
		itemCasings = register(new ItemCasing());
		itemChassis = Registry.register(new ItemBlockChassis(BlockManager.blockModular));
		itemFileIron = register(new ItemToolCrafting("file.iron", 150, 2));
		itemFileDiamond = register(new ItemToolCrafting("file.diamond", 300, 1));
		itemHammer = register(new ItemToolCrafting("hammer", 300, 15));
		itemCutter = register(new ItemToolCrafting("cutter", 250, 10));
		itemDusts = register(new ItemMetal("dusts", dusts));
		itemIngots = register(new ItemMetal("ingots", metals));
		itemNuggets = register(new ItemMetal("nuggets", metals));
		itemCompWires = register(new ItemComponent("wires", EnumMetalMaterials.IRON, EnumMetalMaterials.TIN, EnumMetalMaterials.COPPER, EnumMetalMaterials.BRONZE, EnumMetalMaterials.STEEL));
		itemCompRods = register(new ItemComponent("rods", EnumMetalMaterials.IRON, EnumMetalMaterials.TIN, EnumMetalMaterials.COPPER, EnumMetalMaterials.BRONZE, EnumMetalMaterials.STEEL));
		itemCompScrews = register(new ItemComponent("screws", EnumMetalMaterials.IRON, EnumMetalMaterials.BRONZE, EnumMetalMaterials.STEEL));
		itemCompPlates = register(new ItemComponent("plates", EnumMetalMaterials.IRON, EnumMetalMaterials.TIN, EnumMetalMaterials.COPPER, EnumMetalMaterials.BRONZE, EnumMetalMaterials.STEEL, EnumMetalMaterials.ALUMINIUM, EnumMetalMaterials.INVAR));
		itemCompGears = register(new ItemComponent("gears", EnumMetalMaterials.IRON, EnumMetalMaterials.BRONZE, EnumMetalMaterials.STEEL));
		itemModuleStorageLarge = register(new ItemModuleMeta("moduleStorageLarge", new String[] { "wood", "brick", "bronze", "iron", "steel", "magmarium" }));
		itemModuleStorageSmall = register(new ItemModuleMeta("moduleStorageSmall", new String[] { "wood", "brick", "bronze", "iron", "steel", "magmarium" }));
		itemEngineSteam = register(new ItemModuleMeta("engineSteam", new String[] { "bronze", "iron", "steel", "magmarium" }));
		itemEngineElectric = register(new ItemModuleMeta("engineElectric", new String[] { "bronze", "iron", "steel", "magmarium" }));
		itemTurbineSteam = register(new ItemModuleMeta("turbineSteam", new String[] { "bronze", "iron", "steel", "magmarium" }));
		itemModuleCore = register(new ItemModuleMeta("moduleCore", new String[] { "basic", "normal", "improved", "advanced" }));
		itemWrench = register(new ItemWrench());
		itemPhotovoltaic = register(new ItemModuleMeta("photovoltaic", new String[] { "basic", "default", "improved", "advanced" }));
		itemSolarBoiler = register(new ItemModuleMeta("solar_boiler", new String[] { "basic", "default", "improved", "advanced" }));
		ModuleManager.defaultModuleItem = itemModules = register(new ItemModule());
		ModuleManager.defaultModuleHolderItem = itemModuleHolder = register(new ItemModuleHolder());
		ModuleManager.defaultModuleItemContainer = itemModuleContainer = register(new ItemModuleContainer());
	}

	public static <I extends Item> I register(I item) {
		Registry.register(item, item.getUnlocalizedName().replace("forest.item.", "").replace("item.", ""));
		return item;
	}
}
