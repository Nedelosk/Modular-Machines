package nedelosk.modularmachines.common.core;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestday.common.core.registry.FRegistry;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.modularmachines.common.core.manager.RecipeManager;
import nedelosk.modularmachines.common.core.manager.OreDictionaryManager;
import nedelosk.modularmachines.common.core.registry.BlockRegistry;
import nedelosk.modularmachines.common.core.registry.ItemRegistry;
import nedelosk.modularmachines.common.core.registry.ModularRegistry;
import nedelosk.modularmachines.common.events.EventHandler;
import nedelosk.modularmachines.common.modular.utils.ModuleFactory;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import nedelosk.modularmachines.common.world.WorldGeneratorModularMachines;
import nedelosk.modularmachines.plugins.PluginManager;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;

public class MMCore {

	PluginManager pluginManager = new PluginManager();

	public static Fluid White_Pig_Iron;
	public static Fluid Gray_Pig_Iron;
	public static Fluid Steel;
	public static Fluid Niobium;
	public static Fluid Tantalum;
	public static Fluid Slag;
	public static Fluid Gas_Blastfurnace;
	public static Fluid Air_Hot;
	public static Fluid Air;

	public void preInit() {
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

	public void init() {
		OreDictionaryManager.init();
		RecipeManager.init();
		pluginManager.init();
	}

	public void postInit() {
		pluginManager.postInit();
		GameRegistry.registerWorldGenerator(new WorldGeneratorModularMachines(), 0);
		ModularRegistry.postInit();
	}

	public static void registerFluids() {
		White_Pig_Iron = FRegistry.registerFluid("white_pig_iron", 1500, Material.lava, true, false).setDensity(3000).setViscosity(6000);
		Gray_Pig_Iron = FRegistry.registerFluid("gray_pig_iron", 1500, Material.lava, true, false).setDensity(3000).setViscosity(6000);
		Steel = FRegistry.registerFluid("steel", 1500, Material.lava, true, false).setDensity(3000).setViscosity(6000);
		Niobium = FRegistry.registerFluid("niobium", 1000, Material.lava, true, false).setDensity(3000).setViscosity(6000);
		Tantalum = FRegistry.registerFluid("tantalum", 1000, Material.lava, true, false).setDensity(3000).setViscosity(6000);
		Slag = FRegistry.registerFluid("slag", 100, Material.lava, true, false).setDensity(3000).setViscosity(6000);
		Gas_Blastfurnace = FRegistry.registerFluid("gas_blastfurnace", 200, Material.water, true, true);
		Air_Hot = FRegistry.registerFluid("air_hot", 750, Material.lava, true, true);
		Air = FRegistry.registerFluid("air", 0, Material.water, true, true);
	}
}
