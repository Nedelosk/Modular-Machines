package nedelosk.modularmachines.common.core;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.modularmachines.common.core.manager.ModularRecipeManager;
import nedelosk.modularmachines.common.core.manager.OreDictManager;
import nedelosk.modularmachines.common.core.registry.BlockRegistry;
import nedelosk.modularmachines.common.core.registry.ItemRegistry;
import nedelosk.modularmachines.common.core.registry.ModularRegistry;
import nedelosk.modularmachines.common.core.registry.TechTreeRegistry;
import nedelosk.modularmachines.common.events.EventHandler;
import nedelosk.modularmachines.common.events.EventHandlerNetwork;
import nedelosk.modularmachines.common.events.KeyHandler;
import nedelosk.modularmachines.common.multiblocks.MultiblockAirHeatingPlant;
import nedelosk.modularmachines.common.multiblocks.MultiblockBlastFurnace;
import nedelosk.modularmachines.common.multiblocks.MultiblockCokeOven;
import nedelosk.modularmachines.common.multiblocks.MultiblockFermenter;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.world.WorldGeneratorModularMachines;
import nedelosk.modularmachines.plugins.PluginManager;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.MinecraftForge;

public class MMRegistry {

	PluginManager pluginManager = new PluginManager();
	
	public void preInit()
	{
    	registerFluids();
    	pluginManager.registerPlugins();
		pluginManager.preInit();
    	ModularConfig.preInit();
    	BlockRegistry.preInit();
    	ItemRegistry.preInit();
    	ModularRegistry.preInit();
    	TechTreeRegistry.preInit();
    	PacketHandler.preInit();
    	ModularRecipeManager.preInit();
    	MinecraftForge.EVENT_BUS.register(new EventHandler());
    	if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
    		FMLCommonHandler.instance().bus().register(new KeyHandler());
		FMLCommonHandler.instance().bus().register(new EventHandlerNetwork());
		GameRegistry.registerWorldGenerator(new WorldGeneratorModularMachines(), 0);
		new MultiblockAirHeatingPlant();
		new MultiblockBlastFurnace();
		new MultiblockCokeOven();
		new MultiblockFermenter();
	}
	
	public void init()
	{
		OreDictManager.init();
		pluginManager.init();
	}
	
	public void postInit()
	{
		pluginManager.postInit();
	}
	
	public static void registerFluids()
	{
		NRegistry.registerFluid("pig.iron", 1500, Material.lava, true);
		NRegistry.registerFluid("slag", 100, Material.lava, true);
		NRegistry.registerFluid("gas.blastfurnace", 200, Material.water, true);
		NRegistry.registerFluid("air.hot", 1250, Material.lava, true);
	}
}
