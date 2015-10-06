package nedelosk.modularmachines.common.core;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.modularmachines.common.core.manager.RecipeManager;
import nedelosk.modularmachines.common.core.manager.OreDictManager;
import nedelosk.modularmachines.common.core.registry.BlockRegistry;
import nedelosk.modularmachines.common.core.registry.ItemRegistry;
import nedelosk.modularmachines.common.core.registry.ModularRegistry;
import nedelosk.modularmachines.common.events.EventHandler;
import nedelosk.modularmachines.common.modular.utils.ModuleFactory;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.world.WorldGeneratorModularMachines;
import nedelosk.modularmachines.plugins.PluginManager;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;

public class MMCore {

	PluginManager pluginManager = new PluginManager();
	
	public static Fluid White_Pig_Iron;
	public static Fluid Gray_Pig_Iron;
	public static Fluid Niobium;
	public static Fluid Tantalum;
	public static Fluid Slag;
	public static Fluid Gas_Blastfurnace;
	public static Fluid Air_Hot;
	public static Fluid Air;
	
	public void preInit()
	{
    	MinecraftForge.EVENT_BUS.register(new EventHandler());
		ModuleFactory.init();
    	registerFluids();
       	ModularConfig.preInit();
    	pluginManager.registerPlugins();
		pluginManager.preInit();
    	BlockRegistry.preInit();
    	ItemRegistry.preInit();
    	ModularRegistry.preInit();
    	PacketHandler.preInit();
	}
	
	public void init()
	{
		OreDictManager.init();
		RecipeManager.init();
		pluginManager.init();
	}
	
	public void postInit()
	{
		pluginManager.postInit();
		GameRegistry.registerWorldGenerator(new WorldGeneratorModularMachines(), 0);
	}
	
	public static void registerFluids()
	{
		White_Pig_Iron = NRegistry.registerFluid("white.pig.iron", 1500, Material.lava, true, false);
		Gray_Pig_Iron = NRegistry.registerFluid("gray.pig.iron", 1500, Material.lava, true, false);
		Niobium = NRegistry.registerFluid("niobium", 1000, Material.lava, true, false);
		Tantalum = NRegistry.registerFluid("tantalum", 1000, Material.lava, true, false);
		Slag = NRegistry.registerFluid("slag", 100, Material.lava, true, false);
		Gas_Blastfurnace = NRegistry.registerFluid("gas.blastfurnace", 200, Material.water, true, true);
		Air_Hot = NRegistry.registerFluid("air.hot", 750, Material.lava, true, true);
		Air = NRegistry.registerFluid("air", 0, Material.water, true, true);
	}
}
