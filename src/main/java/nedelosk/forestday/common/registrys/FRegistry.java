package nedelosk.forestday.common.registrys;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.core.EventHandler;
import nedelosk.forestday.common.core.TabForestday;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.MultiblockCharcoalKiln;
import nedelosk.forestday.common.managers.CraftingManager;
import nedelosk.forestday.common.managers.OreManager;
import nedelosk.forestday.common.network.packets.PacketHandler;
import nedelosk.forestday.common.plugins.PluginManager;
import nedelosk.nedeloskcore.common.core.registry.EntryRegistry;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;

public class FRegistry {
    
    //Register
    
    public void registerFluids()
    {
		NRegistry.registerFluid("tar", 350, Material.lava, true, false);
		NRegistry.registerFluid("resin", 100, Material.water, true, false);
		NRegistry.registerFluid("rubber", 550, Material.lava, true, false);
		NRegistry.registerFluid("lubricant", 30, Material.water, true, false);
    }
    
    PluginManager manangerPlugin = new PluginManager();
    
    public void preInit()
    {
    	CreativeTabs tabBlocks = Tabs.tabForestdayBlocks = TabForestday.tabForestdayBlocks;
    	CreativeTabs tabItemss = Tabs.tabForestdayItems = TabForestday.tabForestdayItems;
    	
    	registerFluids();
    	PacketHandler.preInit();
    	EntryRegistry.preInit();
    	BlockRegistry.preInit();
    	ItemRegistry.preInit();
    	
    	MinecraftForge.EVENT_BUS.register(new EventHandler());
    	
    	new MultiblockCharcoalKiln();
    	
    	manangerPlugin.preInit();
    }
    
    public void init()
    {
    	OreManager.preInit();
    	CraftingManager.registerRecipes();
    	manangerPlugin.init();
    }
    
    public void postInit()
    {
		
		/*for(WoodType type : WoodTypeManager.woodTypes)
		{
			ItemStack stack = new ItemStack(FItems.charcoal_kiln.item());
			NBTTagCompound nbt = new NBTTagCompound();
			type.writeToNBT(nbt);
			stack.setTagCompound(nbt);
			CraftingManager.addShapelessRecipe(stack, FBlocks.Gravel.item(), FBlocks.Gravel.item(), FBlocks.Gravel.item(), type.wood, type.wood, type.wood, type.wood, type.wood, type.wood);
		}*/
    	CraftingManager.removeRecipes();
    	manangerPlugin.postInit();
    }
    
}
