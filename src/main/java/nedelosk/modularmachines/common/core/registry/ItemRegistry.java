package nedelosk.modularmachines.common.core.registry;

import nedelosk.forestday.common.items.materials.ItemIngot;
import nedelosk.forestday.common.items.materials.ItemNugget;
import nedelosk.modularmachines.common.core.manager.MMItemManager;
import nedelosk.modularmachines.common.items.ItemCapacitor;
import nedelosk.modularmachines.common.items.ItemMachineComponent;
import nedelosk.modularmachines.common.items.ItemProducers;
import nedelosk.modularmachines.common.items.ModularMetaItem;
import nedelosk.modularmachines.common.items.materials.ItemAlloyIngot;
import nedelosk.modularmachines.common.items.materials.ItemAlloyNugget;
import nedelosk.modularmachines.common.items.materials.ItemDusts;

public class ItemRegistry {
	
	public static String[] ingotsOther = new String[]{ "Niobium", "Tantalum", "Aluminum", "Steel", "White_Steel", "Gray_Steel" };
	
	public static ItemProducers Modules;
	
	public static void preInit()
	{
		
		//Blocks
		MMItemManager.Dusts.registerItem(new ItemDusts(ItemDusts.dusts, ""));
		MMItemManager.Dusts_Others.registerItem(new ItemDusts(ItemDusts.dustsOtherOres, ".other"));
		MMItemManager.Alloy_Ingots.registerItem(new ItemAlloyIngot());
		MMItemManager.Ingots_Others.registerItem(new ItemIngot(ingotsOther, "modularmachines"));
		MMItemManager.Alloy_Nuggets.registerItem(new ItemAlloyNugget());
		MMItemManager.Nuggets_Others.registerItem(new ItemNugget(ingotsOther, "modularmachines"));
		
		MMItemManager.Module_Item_Capacitor.registerItem(new ItemCapacitor("", new String[]{ "metal_paper_capacitor", "electrolyte_niobium_capacitor", "electrolyte_tantalum_capacitor", "double_layer_capacitor" }));
		MMItemManager.Module_Item_Engine.registerItem(new ModularMetaItem("engine", new String[]{ "iron_engine", "bronze_engine", "steel_engine", "magmarium_engine"}));
		
		MMItemManager.Component_Connection_Wires.registerItem(new ItemMachineComponent("connection_wires"));
		MMItemManager.Component_Rods.registerItem(new ItemMachineComponent("rods"));
		MMItemManager.Component_Screws.registerItem(new ItemMachineComponent("screws"));
		MMItemManager.Component_Gears.registerItem(new ItemMachineComponent("gears"));
		MMItemManager.Component_Plates.registerItem(new ItemMachineComponent("plates"));
		MMItemManager.Component_Energy_Crystals.registerItem(new ItemMachineComponent("energy_crystal"));
		MMItemManager.Component_Saw_Blades.registerItem(new ItemMachineComponent("saw_blades"));
		MMItemManager.Producers.registerItem(new ItemProducers());
		
		MMItemManager.Component_Connection_Wires.addMetaData(0xDADADA, "iron", "Iron");
		MMItemManager.Component_Connection_Wires.addMetaData(0xCACECF, "tin", "Tin");
		MMItemManager.Component_Connection_Wires.addMetaData(0xCC6410, "copper", "Copper");
		MMItemManager.Component_Connection_Wires.addMetaData(0xCA9956, "bronze", "Bronze");
		MMItemManager.Component_Connection_Wires.addMetaData(0xA0A0A0, "steel", "Steel");
		
		MMItemManager.Component_Rods.addMetaData(0x7F7F7F, "stone", "Stone");
		MMItemManager.Component_Rods.addMetaData(0xDADADA, "iron", "Iron");
		MMItemManager.Component_Rods.addMetaData(0xCACECF, "tin", "Tin");
		MMItemManager.Component_Rods.addMetaData(0xCC6410, "copper", "Copper");
		MMItemManager.Component_Rods.addMetaData(0xCA9956, "bronze", "Bronze");
		MMItemManager.Component_Rods.addMetaData(0xA0A0A0, "steel", "Steel");
		MMItemManager.Component_Rods.addMetaData(0xD4E3E6, "plastic", "Plastic");
		
		MMItemManager.Component_Screws.addMetaData(0xDADADA, "iron", "Iron");
		MMItemManager.Component_Screws.addMetaData(0xCACECF, "tin", "Tin");
		MMItemManager.Component_Screws.addMetaData(0xCC6410, "copper", "Copper");
		MMItemManager.Component_Screws.addMetaData(0xCA9956, "bronze", "Bronze");
		MMItemManager.Component_Screws.addMetaData(0xA0A0A0, "steel", "Steel");
		
		MMItemManager.Component_Gears.addMetaData(0x7F7F7F, "stone", "Stone");
		MMItemManager.Component_Gears.addMetaData(0xDADADA, "iron", "Iron");
		MMItemManager.Component_Gears.addMetaData(0xCACECF, "tin", "Tin");
		MMItemManager.Component_Gears.addMetaData(0xCC6410, "copper", "Copper");
		MMItemManager.Component_Gears.addMetaData(0xCA9956, "bronze", "Bronze");
		MMItemManager.Component_Gears.addMetaData(0xA0A0A0, "steel", "Steel");
		
		MMItemManager.Component_Plates.addMetaData(0x7F7F7F, "stone", "Stone");
		MMItemManager.Component_Plates.addMetaData(0xDADADA, "iron", "Iron");
		MMItemManager.Component_Plates.addMetaData(0xCACECF, "tin", "Tin");
		MMItemManager.Component_Plates.addMetaData(0xCC6410, "copper", "Copper");
		MMItemManager.Component_Plates.addMetaData(0xCA9956, "bronze", "Bronze");
		MMItemManager.Component_Plates.addMetaData(0xA0A0A0, "steel", "Steel");
		MMItemManager.Component_Plates.addMetaData(0xD4E3E6, "plastic", "Plastic");
		
		MMItemManager.Component_Saw_Blades.addMetaData(0x7F7F7F, "stone", "Stone");
		MMItemManager.Component_Saw_Blades.addMetaData(0xDADADA, "iron", "Iron");
		MMItemManager.Component_Saw_Blades.addMetaData(0xCA9956, "bronze", "Bronze");
		MMItemManager.Component_Saw_Blades.addMetaData(0xA0A0A0, "steel", "Steel");
	}
	
}
