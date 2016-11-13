package modularmachines.common.core;

import java.io.File;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.RegistryBuilder;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.containers.IModuleItemContainer;
import modularmachines.common.events.EventHandler;
import modularmachines.common.modules.json.ModuleLoadManager;
import modularmachines.common.recipse.RecipeJsonManager;

@Mod(modid = Constants.MOD_ID, name = Constants.NAME, version = Constants.VERSION, dependencies = Constants.DEPENDENCIES, guiFactory = "modularmachines.common.config.ConfigFactory")
public class ModularMachines {

	@Instance(Constants.MOD_ID)
	public static ModularMachines instance;
	@SidedProxy(clientSide = "modularmachines.client.core.ClientProxy", serverSide = "modularmachines.common.core.CommonProxy")
	public static CommonProxy proxy;
	public static File configFolder;
	public static File configFile;

	public ModularMachines() {
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		FluidRegistry.enableUniversalBucket();
		new RegistryBuilder().setIDRange(0, 4095).setName(new ResourceLocation("modularmachines:modules")).setType(IModule.class).create();
		new RegistryBuilder().setIDRange(0, 4095).setName(new ResourceLocation("modularmachines:modulecontainers")).setType(IModuleItemContainer.class).create();
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit();
		new ModuleLoadManager();
		configFolder = new File(event.getModConfigurationDirectory(), "modularmachines");
		configFile = new File(configFolder, "Modular-Machines.cfg");
		Registry.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		Registry.init(event);
		proxy.init();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ModuleLoadManager.loadModules();
		ModuleLoadManager.loadModuleContainers();
		Registry.postInit(event);
		RecipeJsonManager.checkRecipes();
	}
}
