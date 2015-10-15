package nedelosk.modularmachines.common.core.manager;

import nedelosk.modularmachines.common.items.ItemMachineComponent;
import nedelosk.nedeloskcore.common.core.registry.NCItemManager;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import static net.minecraftforge.oredict.OreDictionary.registerOre;

public class OreDictionaryManager {

	public static void init()
	{
		registerOre("dustCoal", new ItemStack(MMItemManager.Dusts.item(), 1, 0));
		registerOre("dustObsidian", new ItemStack(MMItemManager.Dusts.item(), 1, 1));
		registerOre("dustIron", new ItemStack(MMItemManager.Dusts.item(), 1, 2));
		registerOre("dustGold", new ItemStack(MMItemManager.Dusts.item(), 1, 3));
		registerOre("dustDiamond", new ItemStack(MMItemManager.Dusts.item(), 1, 4));
		registerOre("dustCopper", new ItemStack(MMItemManager.Dusts.item(), 1, 5));
		registerOre("dustTin", new ItemStack(MMItemManager.Dusts.item(), 1, 6));
		registerOre("dustSilver", new ItemStack(MMItemManager.Dusts.item(), 1, 7));
		registerOre("dustLead", new ItemStack(MMItemManager.Dusts.item(), 1, 8));
		registerOre("dustNickel", new ItemStack(MMItemManager.Dusts.item(), 1, 9));
		registerOre("dustBronze", new ItemStack(MMItemManager.Dusts.item(), 1, 10));
		registerOre("dustInvar", new ItemStack(MMItemManager.Dusts.item(), 1, 11));
		registerOre("dustRuby", new ItemStack(MMItemManager.Dusts.item(), 1, 12));
		registerOre("gearWood", new ItemStack(NCItemManager.Gears_Wood.item(), 1, 1));
		registerOre("ingotBronze", new ItemStack(MMItemManager.Alloy_Ingots.item(), 1, 0));
		registerOre("ingotInvar", new ItemStack(MMItemManager.Alloy_Ingots.item(), 1, 1));
		registerOre("nuggetBronze", new ItemStack(MMItemManager.Alloy_Nuggets.item(), 1, 0));
		registerOre("nuggetInvar", new ItemStack(MMItemManager.Alloy_Nuggets.item(), 1, 1));
		registerOre("oreColumbite", new ItemStack(MMBlockManager.Ore_Others.item(), 1, 0));
		registerOre("oreAluminium", new ItemStack(MMBlockManager.Ore_Others.item(), 1, 1));
		registerOre("oreAluminum", new ItemStack(MMBlockManager.Ore_Others.item(), 1, 1));
		registerOre("ingotNiobium", new ItemStack(MMItemManager.Ingots_Others.item(), 1, 0));
		registerOre("nuggetNiobium", new ItemStack(MMItemManager.Nuggets_Others.item(), 1, 0));
		registerOre("ingotTantalum", new ItemStack(MMItemManager.Ingots_Others.item(), 1, 1));
		registerOre("nuggetTantalum", new ItemStack(MMItemManager.Nuggets_Others.item(), 1, 1));
		registerOre("ingotAluminium", new ItemStack(MMItemManager.Ingots_Others.item(), 1, 2));
		registerOre("nuggetAluminium", new ItemStack(MMItemManager.Nuggets_Others.item(), 1, 2));
		registerOre("ingotAluminum", new ItemStack(MMItemManager.Ingots_Others.item(), 1, 2));
		registerOre("nuggetAluminum", new ItemStack(MMItemManager.Nuggets_Others.item(), 1, 2));
		registerOre("dustColumbite", new ItemStack(MMItemManager.Dusts_Others.item(), 1, 0));
		registerOre("dustNiobium", new ItemStack(MMItemManager.Dusts_Others.item(), 1, 1));
		registerOre("dustTantalum", new ItemStack(MMItemManager.Dusts_Others.item(), 1, 2));
		registerOre("dustAluminium", new ItemStack(MMItemManager.Dusts_Others.item(), 1, 3));
		registerOre("dustAlumium", new ItemStack(MMItemManager.Dusts_Others.item(), 1, 3));
		registerOre("blockObsidian", Blocks.obsidian);
		
        for(int i = 0;i < ((ItemMachineComponent)MMItemManager.Component_Plates.item()).metas.size();i++) {
            ItemStack stack = new ItemStack(MMItemManager.Component_Plates.item(), 1, i);
            ItemMachineComponent component = (ItemMachineComponent) stack.getItem();
            registerOre("plate" + (String)component.metas.get(i).get(2), stack);
        }
        
        for(int i = 0;i < ((ItemMachineComponent)MMItemManager.Component_Rods.item()).metas.size();i++) {
            ItemStack stack = new ItemStack(MMItemManager.Component_Rods.item(), 1, i);
            ItemMachineComponent component = (ItemMachineComponent) stack.getItem();
            registerOre("rod" + (String)component.metas.get(i).get(2), stack);
        }
        
        for(int i = 0;i < ((ItemMachineComponent)MMItemManager.Component_Screws.item()).metas.size();i++) {
            ItemStack stack = new ItemStack(MMItemManager.Component_Screws.item(), 1, i);
            ItemMachineComponent component = (ItemMachineComponent) stack.getItem();
            registerOre("screw" + (String)component.metas.get(i).get(2), stack);
        }
        
        for(int i = 0;i < ((ItemMachineComponent)MMItemManager.Component_Gears.item()).metas.size();i++) {
            ItemStack stack = new ItemStack(MMItemManager.Component_Gears.item(), 1, i);
            ItemMachineComponent component = (ItemMachineComponent) stack.getItem();
            registerOre("gear" + (String)component.metas.get(i).get(2), stack);
        }
        
        for(int i = 0;i < ((ItemMachineComponent)MMItemManager.Component_Saw_Blades.item()).metas.size();i++) {
            ItemStack stack = new ItemStack(MMItemManager.Component_Saw_Blades.item(), 1, i);
            ItemMachineComponent component = (ItemMachineComponent) stack.getItem();
            registerOre("sawBlade" + (String)component.metas.get(i).get(2), stack);
        }
	}
	
}
