package nedelosk.modularmachines.common.core;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import nedelosk.forestcore.library.core.Registry;
import nedelosk.forestcore.library.modules.AModuleManager;
import nedelosk.forestcore.library.plugins.APluginManager;
import nedelosk.modularmachines.api.packets.PacketHandler;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.modularmachines.common.core.manager.RecipeManager;
import nedelosk.modularmachines.common.core.manager.OreDictionaryManager;
import nedelosk.modularmachines.common.core.registry.BlockRegistry;
import nedelosk.modularmachines.common.core.registry.ItemRegistry;
import nedelosk.modularmachines.common.core.registry.ModularRegistry;
import nedelosk.modularmachines.common.events.EventHandler;
import nedelosk.modularmachines.common.modular.utils.ProducerFactory;
import nedelosk.modularmachines.common.network.packets.PacketAssembler;
import nedelosk.modularmachines.common.world.WorldGeneratorModularMachines;
import nedelosk.modularmachines.plugins.PluginManager;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;

public class MMCore extends Registry {

	public static Fluid White_Pig_Iron;
	public static Fluid Gray_Pig_Iron;
	public static Fluid Steel;
	public static Fluid Slag;
	public static Fluid Gas_Blastfurnace;
	public static Fluid Air_Hot;
	public static Fluid Air;

	@Override
	public void preInit(Object instance, FMLPreInitializationEvent event) {
		super.preInit(instance, event);
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		ProducerFactory.init();
		registerFluids();
		ModularConfig.preInit();
		BlockRegistry.preInit();
		ItemRegistry.preInit();
		ModularRegistry.preInit();
		PacketHandler.preInit();
		
		// Assembler
		PacketHandler.INSTANCE.registerMessage(PacketAssembler.class, PacketAssembler.class, PacketHandler.nextID(), Side.CLIENT);
		PacketHandler.INSTANCE.registerMessage(PacketAssembler.class, PacketAssembler.class, PacketHandler.nextID(), Side.SERVER);
	}

	@Override
	public void init(Object instance, FMLInitializationEvent event) {
		super.init(instance, event);
		OreDictionaryManager.init();
		RecipeManager.init();
	}

	@Override
	public void postInit(Object instance, FMLPostInitializationEvent event) {
		super.postInit(instance, event);
		GameRegistry.registerWorldGenerator(new WorldGeneratorModularMachines(), 0);
		ModularRegistry.postInit();
	}
	
	@Override
	public AModuleManager getModuleManager() {
		return null;
	}
	
	@Override
	public APluginManager getPluginManager() {
		return new PluginManager();
	}
	
	@Override
	public IGuiHandler getGuiHandler() {
		return ModularMachines.proxy;
	}

	public static void registerFluids() {
		White_Pig_Iron = registerFluid("white_pig_iron", 1500, Material.lava, true, false).setDensity(3000).setViscosity(6000);
		Gray_Pig_Iron = registerFluid("gray_pig_iron", 1500, Material.lava, true, false).setDensity(3000).setViscosity(6000);
		Steel = registerFluid("steel", 1500, Material.lava, true, false).setDensity(3000).setViscosity(6000);
		Slag = registerFluid("slag", 100, Material.lava, true, false).setDensity(3000).setViscosity(6000);
		Gas_Blastfurnace = registerFluid("gas_blastfurnace", 200, Material.water, true, true);
		Air_Hot = registerFluid("air_hot", 750, Material.lava, true, true);
		Air = registerFluid("air", 0, Material.water, true, true);
	}
}
