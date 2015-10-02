package nedelosk.modularmachines.common.core.registry;

import nedelosk.modularmachines.api.modular.material.MaterialType;
import nedelosk.modularmachines.api.modular.module.utils.ModularManager;
import nedelosk.modularmachines.api.modular.parts.IMachinePart;
import nedelosk.modularmachines.common.core.MMItems;
import nedelosk.modularmachines.common.items.ItemCapacitor;
import nedelosk.modularmachines.common.items.ItemMachineComponent;
import nedelosk.modularmachines.common.items.ItemMachinePartBattery;
import nedelosk.modularmachines.common.items.ItemMachinePartEngine;
import nedelosk.modularmachines.common.items.ItemMachinePattern;
import nedelosk.modularmachines.common.items.ModuleItems;
import nedelosk.modularmachines.common.items.materials.ItemAlloyIngot;
import nedelosk.modularmachines.common.items.materials.ItemAlloyNugget;
import nedelosk.modularmachines.common.items.materials.ItemDusts;
import nedelosk.nedeloskcore.common.items.ItemIngot;
import nedelosk.nedeloskcore.common.items.ItemNugget;

public class ItemRegistry {
	
	public static String[] ingotsOther = new String[]{ "Niobium", "Tantalum", "Aluminum" };
	
	public static ItemMachineComponent Connection_Wires;
	public static ItemMachineComponent Rods;
	public static ItemMachineComponent Screws;
	public static ItemMachineComponent Gears;
	public static ItemMachineComponent Plates;
	public static ItemMachineComponent Energy_Crystal;
	
	public static void preInit()
	{
		
		//Blocks
		MMItems.Dusts.registerItem(new ItemDusts(ItemDusts.dusts, ""));
		MMItems.Dusts_Others.registerItem(new ItemDusts(ItemDusts.dustsOtherOres, ".other"));
		MMItems.Alloy_Ingots.registerItem(new ItemAlloyIngot());
		MMItems.Ingots_Others.registerItem(new ItemIngot(ingotsOther, "modularmachines"));
		MMItems.Alloy_Nuggets.registerItem(new ItemAlloyNugget());
		MMItems.Nuggets_Others.registerItem(new ItemNugget(ingotsOther, "modularmachines"));
		
		MMItems.Module_Item_Capacitor.registerItem(new ItemCapacitor("", new String[]{ "metal_paper_capacitor", "electrolyte_niobium_capacitor", "electrolyte_tantalum_capacitor", "double_layer_capacitor" }));
		MMItems.Module_Items.registerItem(new ModuleItems());
		
		MMItems.Component_Connection_Wires.registerItem(Connection_Wires = new ItemMachineComponent("connection_wires", MaterialType.METAL));
		MMItems.Component_Rods.registerItem(Rods = new ItemMachineComponent("rods", MaterialType.METAL, MaterialType.WOOD));
		MMItems.Component_Screws.registerItem(Screws = new ItemMachineComponent("screws", MaterialType.METAL));
		MMItems.Component_Gears.registerItem(Gears = new ItemMachineComponent("gears", MaterialType.METAL));
		MMItems.Component_Plates.registerItem(Plates = new ItemMachineComponent("plates", MaterialType.METAL, MaterialType.CUSTOM));
		MMItems.Component_Energy_Crystals.registerItem(Energy_Crystal = new ItemMachineComponent("energy_crystal", MaterialType.CRYTAL));
		
		MMItems.Part_Battery.registerItem(registerPart(new ItemMachinePartBattery("Battery")));
		MMItems.Part_Engine.registerItem(registerPart(new ItemMachinePartEngine("Engine")));
		
		int[] costs = new int[]{ 1, 1 };
		String[] components = new String[]{ "connection_wires", "screws", "energy_crystal" };
		MMItems.WoodPattern.registerItem(new ItemMachinePattern("wood", components, costs));
	}
	
	public static <M extends IMachinePart> M registerPart(M part){
		return ModularManager.registerMachinePart(part);
	}
	
}
