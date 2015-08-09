package nedelosk.forestday.common.registrys;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.core.TabForestday;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.WoodType;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.WoodTypeManager;
import nedelosk.forestday.common.managers.CraftingManager;
import nedelosk.forestday.common.managers.OreManager;
import nedelosk.forestday.common.network.packets.PacketHandler;
import nedelosk.forestday.common.plugins.PluginManager;
import nedelosk.nedeloskcore.common.core.registry.EntryRegistry;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class FRegistry {
    
    //Register
    
    public void registerFluids()
    {
		NRegistry.registerFluid("tar", 350, Material.lava, true);
		NRegistry.registerFluid("resin", 100, Material.water, true);
		NRegistry.registerFluid("rubber", 550, Material.lava, true);
		NRegistry.registerFluid("lubricant", 30, Material.water, true);
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
		
		for(WoodType type : WoodTypeManager.woodTypes)
		{
			ItemStack stack = new ItemStack(FItems.charcoal_kiln.item());
			NBTTagCompound nbt = new NBTTagCompound();
			type.writeToNBT(nbt);
			stack.setTagCompound(nbt);
			CraftingManager.addShapelessRecipe(stack, FBlocks.Gravel.item(), FBlocks.Gravel.item(), FBlocks.Gravel.item(), type.wood, type.wood, type.wood, type.wood, type.wood, type.wood);
		}
    	CraftingManager.removeRecipes();
    	manangerPlugin.postInit();
    }
    
}
