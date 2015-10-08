package nedelosk.modularmachines.common.core.registry;

import nedelosk.modularmachines.api.materials.MaterialType;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.api.parts.IMachinePart;
import nedelosk.modularmachines.common.core.manager.MMItemManager;
import nedelosk.modularmachines.common.items.ItemCapacitor;
import nedelosk.modularmachines.common.items.ItemMachineComponent;
import nedelosk.modularmachines.common.items.ItemMachinePattern;
import nedelosk.modularmachines.common.items.materials.ItemAlloyIngot;
import nedelosk.modularmachines.common.items.materials.ItemAlloyNugget;
import nedelosk.modularmachines.common.items.materials.ItemDusts;
import nedelosk.modularmachines.common.items.parts.ItemMachinePart;
import nedelosk.modularmachines.common.items.parts.energy.ItemMachinePartBattery;
import nedelosk.modularmachines.common.items.parts.energy.ItemMachinePartEngine;
import nedelosk.modularmachines.common.items.parts.recipes.ItemMachinePartBurningChamber;
import nedelosk.modularmachines.common.items.parts.recipes.ItemMachinePartCentrifugeChamber;
import nedelosk.modularmachines.common.items.parts.recipes.ItemMachinePartGrindingWheel;
import nedelosk.modularmachines.common.items.parts.recipes.ItemMachinePartModule;
import nedelosk.modularmachines.common.items.parts.recipes.ItemMachinePartProducer;
import nedelosk.nedeloskcore.common.items.ItemIngot;
import nedelosk.nedeloskcore.common.items.ItemNugget;

public class ItemRegistry {
	
	public static String[] ingotsOther = new String[]{ "Niobium", "Tantalum", "Aluminum", "Steel", "White_Steel", "Gray_Steel" };
	
	public static ItemMachineComponent Connection_Wires;
	public static ItemMachineComponent Rods;
	public static ItemMachineComponent Screws;
	public static ItemMachineComponent Gears;
	public static ItemMachineComponent Plates;
	public static ItemMachineComponent Energy_Crystal;
	public static ItemMachineComponent Saw_Blade;
	public static ItemMachinePart Battery;
	public static ItemMachinePart Engine;
	public static ItemMachinePart Module;
	public static ItemMachinePartBurningChamber Burning_Chamber;
	public static ItemMachinePartGrindingWheel Grinding_Wheel;
	public static ItemMachinePartCentrifugeChamber Centrifuge_Chamber;
	public static ItemMachinePartProducer Producer;
	
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
		
		MMItemManager.Component_Connection_Wires.registerItem(Connection_Wires = new ItemMachineComponent("connection_wires", MaterialType.METAL));
		MMItemManager.Component_Rods.registerItem(Rods = new ItemMachineComponent("rods", MaterialType.METAL, MaterialType.WOOD));
		MMItemManager.Component_Screws.registerItem(Screws = new ItemMachineComponent("screws", MaterialType.METAL));
		MMItemManager.Component_Gears.registerItem(Gears = new ItemMachineComponent("gears", MaterialType.METAL));
		MMItemManager.Component_Plates.registerItem(Plates = new ItemMachineComponent("plates", MaterialType.METAL, MaterialType.CUSTOM));
		MMItemManager.Component_Energy_Crystals.registerItem(Energy_Crystal = new ItemMachineComponent("energy_crystal", MaterialType.CRYTAL));
		MMItemManager.Component_Saw_Blades.registerItem(Saw_Blade = new ItemMachineComponent("saw_blades", MaterialType.METAL));
		
		MMItemManager.Part_Battery.registerItem(Battery = registerPart(new ItemMachinePartBattery("battery")));
		MMItemManager.Part_Engine.registerItem(Engine = registerPart(new ItemMachinePartEngine("engine")));
		MMItemManager.Part_Module.registerItem(Module = registerPart(new ItemMachinePartModule("module")));
		MMItemManager.Part_Producer.registerItem(Producer = registerPart(new ItemMachinePartProducer()));
		MMItemManager.Part_Burning_Chamber.registerItem(Burning_Chamber = registerPart(new ItemMachinePartBurningChamber("burning_chamber")));
		MMItemManager.Part_Grinding_Wheel.registerItem(Grinding_Wheel = registerPart(new ItemMachinePartGrindingWheel("grinding_wheel")));
		MMItemManager.Part_Centrifuge_Chamber.registerItem(Centrifuge_Chamber = registerPart(new ItemMachinePartCentrifugeChamber("centrifuge_chamber")));
		
		int[] costs = new int[]{ 1, 1, 4 };
		String[] components = new String[]{ "connection_wires", "screws", "energy_crystal" };
		MMItemManager.WoodPattern.registerItem(new ItemMachinePattern("wood", components, costs));
	}
	
	public static <M extends IMachinePart> M registerPart(M part){
		return ModuleRegistry.registerMachinePart(part);
	}
	
}
