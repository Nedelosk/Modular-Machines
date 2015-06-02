package nedelosk.forestday.common.registrys;

import java.util.HashMap;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.core.TabForestday;
import nedelosk.forestday.common.machines.iron.plan.PlanEnum;
import nedelosk.forestday.common.managers.CraftingManager;
import nedelosk.forestday.common.managers.OreDictManager;
import nedelosk.forestday.common.network.packets.PacketHandler;
import nedelosk.forestday.common.plugins.PluginManager;
import nedelosk.forestday.common.world.WorldGeneratorForestday;
import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.api.plan.IPlanEnum;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class ForestdayRegistry {
	
    //Items
    
    public static HashMap<String, Item> itemDirectory = new HashMap<String, Item>();
    
    public static void addItemToDirectory(String name, Item item)
    {
    	itemDirectory.put(name, item);
    	//Defaults.log(Level.INFO, "Forestday  Register Item: {0}", name);
    }
    
    public static void getItemFromDirectory(String name)
    {
    	itemDirectory.get(name);
    }
    
    public static Item registerItem(Item item, String name)
    {
    	
    	String itemName = setUnlocalizedItemName(name);
    	GameRegistry.registerItem(item, itemName);
    	addItemToDirectory(itemName, item);
		return item;
    }
    
    public static String setUnlocalizedItemName(String name)
    {
    	return "fd.item." + name;
    }
    
    //Blocks
    public static HashMap<String, Block> blockDirectory = new HashMap<String, Block>();
	
    public static void addBlockToDirectory(String name, Block item)
    {
    	blockDirectory.put(name, item);
    }
    
    public static String setUnlocalizedBlockName(String name)
    {
    	return "fd.tile." + name;
    }
    
    //Register
    
    public void registerFluids()
    {
		NRegistry.registerFluid("tar", 350, Material.lava, true);
		NRegistry.registerFluid("resin", 100, Material.water, true);
		NRegistry.registerFluid("rubber", 550, Material.lava, true);
		NRegistry.registerFluid("pig_iron", 910, Material.lava, true);
		NRegistry.registerFluid("molten_steel", 700, Material.lava, true);
		NRegistry.registerFluid("steam", 400, Material.lava, true);
		NRegistry.registerFluid("air_hot", 550, Material.water, false);
		NRegistry.registerFluid("gas_blast", 550, Material.water, false);
		NRegistry.registerFluid("slag", 700, Material.lava, true);
		NRegistry.registerFluid("air", 0, Material.water, false);
		NRegistry.registerFluid("lubricant", 30, Material.water, true);
    }
    
    PluginManager manangerPlugin = new PluginManager();
    
    public void preInit()
    {
    	CreativeTabs tabBlocks = Tabs.tabForestdayBlocks = TabForestday.tabForestdayBlocks;
    	CreativeTabs tabItemss = Tabs.tabForestdayItems = TabForestday.tabForestdayItems;
    	CreativeTabs tabMultiblocks = Tabs.tabForestdayMultiBlocks = TabForestday.tabForestdayMultiBlocks;
    	
    	registerFluids();
    	PacketHandler.preInit();
    	ForestdayEntryRegistry.preInit();
    	ForestdayBlockRegistry.preInit();
    	ForestdayItemRegistry.preInit();
    	
		for(IPlanEnum planI : PlanEnum.values())
		{
		NCoreApi.registerPlan(planI);
		}
    	
    	manangerPlugin.preInit();
    }
    
    public void init()
    {
    	CraftingManager.registerRecipes();
    	OreDictManager.registerOreDict();
    	manangerPlugin.init();
    }
    
    public void postInit()
    {
    	CraftingManager.removeRecipes();
    	manangerPlugin.postInit();
    	GameRegistry.registerWorldGenerator(new WorldGeneratorForestday(), 0);
    }
    
}
