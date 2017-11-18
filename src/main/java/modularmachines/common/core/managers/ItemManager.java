package modularmachines.common.core.managers;

import net.minecraft.item.Item;

import modularmachines.common.core.Registry;
import modularmachines.common.items.ItemBattery;
import modularmachines.common.items.ItemCasing;
import modularmachines.common.items.ItemComponent;
import modularmachines.common.items.ItemMetal;
import modularmachines.common.items.ItemModuleMeta;
import modularmachines.common.items.ItemToolCrafting;
import modularmachines.common.items.ItemWrench;
import modularmachines.common.materials.EnumMaterial;
import modularmachines.common.materials.MaterialList;

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
	public static ItemBattery itemBattery;
	public static MaterialList alloys = new MaterialList(EnumMaterial.BRONZE, EnumMaterial.INVAR);
	public static MaterialList default_metals = new MaterialList(EnumMaterial.COPPER, EnumMaterial.TIN, EnumMaterial.SILVER, EnumMaterial.LEAD, EnumMaterial.NICKEL, EnumMaterial.ALUMINIUM,
			EnumMaterial.STEEL);
	/* metal for ingot's, nugget's, block's */
	public static MaterialList[] metals = new MaterialList[]{default_metals, alloys};
	public static MaterialList[] dusts = new MaterialList[]{default_metals, alloys};
	
	public static void registerItems() {
		itemCasings = register(new ItemCasing());
		//itemChassis = Registry.register(new ItemBlockChassis(BlockManager.blockMachine));
		itemFileIron = register(new ItemToolCrafting("file.iron", 150, 2));
		itemFileDiamond = register(new ItemToolCrafting("file.diamond", 300, 1));
		itemHammer = register(new ItemToolCrafting("hammer", 300, 15));
		itemCutter = register(new ItemToolCrafting("cutter", 250, 10));
		itemDusts = register(new ItemMetal("dusts", dusts));
		itemIngots = register(new ItemMetal("ingots", metals));
		itemNuggets = register(new ItemMetal("nuggets", metals));
		itemCompWires = register(new ItemComponent("wires", EnumMaterial.IRON, EnumMaterial.TIN, EnumMaterial.COPPER, EnumMaterial.BRONZE, EnumMaterial.STEEL));
		itemCompRods = register(new ItemComponent("rods", EnumMaterial.IRON, EnumMaterial.TIN, EnumMaterial.COPPER, EnumMaterial.BRONZE, EnumMaterial.STEEL));
		itemCompScrews = register(new ItemComponent("screws", EnumMaterial.IRON, EnumMaterial.BRONZE, EnumMaterial.STEEL));
		itemCompPlates = register(new ItemComponent("plates", EnumMaterial.IRON, EnumMaterial.TIN, EnumMaterial.COPPER, EnumMaterial.BRONZE, EnumMaterial.STEEL, EnumMaterial.ALUMINIUM, EnumMaterial.INVAR));
		itemCompGears = register(new ItemComponent("gears", EnumMaterial.IRON, EnumMaterial.BRONZE, EnumMaterial.STEEL));
		itemModuleStorageLarge = register(new ItemModuleMeta("module_storage_large", new String[]{"wood", "brick", "bronze", "iron", "steel", "magmarium"}));
		itemModuleStorageSmall = register(new ItemModuleMeta("module_storage_small", new String[]{"wood", "brick", "bronze", "iron", "steel", "magmarium"}));
		itemEngineSteam = register(new ItemModuleMeta("engine_steam", new String[]{"bronze", "iron", "steel", "magmarium"}));
		itemEngineElectric = register(new ItemModuleMeta("engine_electric", new String[]{"bronze", "iron", "steel", "magmarium"}));
		itemTurbineSteam = register(new ItemModuleMeta("turbine_steam", new String[]{"bronze", "iron", "steel", "magmarium"}));
		itemModuleCore = register(new ItemModuleMeta("module_core", new String[]{"basic", "normal", "improved", "advanced"}));
		itemWrench = register(new ItemWrench());
		itemPhotovoltaic = register(new ItemModuleMeta("photovoltaic", new String[]{"basic", "default", "improved", "advanced"}));
		itemSolarBoiler = register(new ItemModuleMeta("solar_boiler", new String[]{"basic", "default", "improved", "advanced"}));
		itemBattery = register(new ItemBattery());
		/*ModuleManager.defaultModuleItem = itemModules = register(new ItemModule());
		ModuleManager.defaultModuleHolderItem = itemModuleHolder = register(new ItemModuleHolder());
		ModuleManager.defaultModuleItemContainer = itemModuleContainer = register(new ItemModuleContainer());*/
	}
	
	public static <I extends Item> I register(I item) {
		Registry.register(item, item.getUnlocalizedName().replace("forest.item.", "").replace("item.", ""));
		return item;
	}
}
