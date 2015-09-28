package nedelosk.modularmachines.common.core.registry;

import nedelosk.modularmachines.common.core.MMItems;
import nedelosk.modularmachines.common.items.ItemCapacitor;
import nedelosk.modularmachines.common.items.ItemMachinePartEnergy;
import nedelosk.modularmachines.common.items.ItemMachinePattern;
import nedelosk.modularmachines.common.items.ModuleItems;
import nedelosk.modularmachines.common.items.materials.ItemAlloyIngot;
import nedelosk.modularmachines.common.items.materials.ItemAlloyNugget;
import nedelosk.modularmachines.common.items.materials.ItemDusts;
import nedelosk.modularmachines.common.items.materials.ItemGears;
import nedelosk.modularmachines.common.items.materials.ItemPlates;
import nedelosk.modularmachines.common.items.materials.ItemScrew;
import nedelosk.nedeloskcore.common.items.ItemIngot;
import nedelosk.nedeloskcore.common.items.ItemNugget;

public class ItemRegistry {
	
	public static String[] ingotsOther = new String[]{ "Niobium", "Tantalum", "Aluminum" };
	
	public static void preInit()
	{
		
		//Blocks
		
		MMItems.Dusts.registerItem(new ItemDusts(ItemDusts.dusts, ""));
		MMItems.Dusts_Others.registerItem(new ItemDusts(ItemDusts.dustsOtherOres, ".other"));
		MMItems.Alloy_Ingots.registerItem(new ItemAlloyIngot());
		MMItems.Ingots_Others.registerItem(new ItemIngot(ingotsOther, "modularmachines"));
		MMItems.Alloy_Nuggets.registerItem(new ItemAlloyNugget());
		MMItems.Nuggets_Others.registerItem(new ItemNugget(ingotsOther, "modularmachines"));
		MMItems.Plates.registerItem(new ItemPlates(ItemPlates.plats, ""));
		MMItems.Gears.registerItem(new ItemGears());
		MMItems.Screw.registerItem(new ItemScrew());
		
		MMItems.Module_Item_Capacitor.registerItem(new ItemCapacitor("", new String[]{ "metal_paper_capacitor", "electrolyte_niobium_capacitor", "electrolyte_tantalum_capacitor", "double_layer_capacitor" }));
		MMItems.Module_Items.registerItem(new ModuleItems());
		
		MMItems.Component_Battery.registerItem(new ItemMachinePartEnergy("Battery", 1, 1000000, maxReceive, maxExtract));
		
	}
	
}
