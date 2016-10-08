package de.nedelosk.modularmachines.common.core;

import java.io.File;
import java.util.Map;

import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemContainer;
import de.nedelosk.modularmachines.common.modules.json.ModuleLoadManager;
import de.nedelosk.modularmachines.common.recipse.RecipeJsonManager;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry.AddCallback;
import net.minecraftforge.fml.common.registry.RegistryBuilder;

@Mod(modid = Constants.MODID, name = Constants.NAME, version = Constants.VERSION, dependencies = Constants.DEPENDENCIES, guiFactory = "de.nedelosk.modularmachines.common.config.ConfigFactory")
public class ModularMachines {

	public ModularMachines() {
		FluidRegistry.enableUniversalBucket();

		iModuleRegistry = new RegistryBuilder().setIDRange(0, 4095).setName(new ResourceLocation("modularmachines:modules")).setType(IModule.class).create();
		iModuleContainerRegistry = new RegistryBuilder().setIDRange(0, 4095).setName(new ResourceLocation("modularmachines:modulecontainers")).setType(IModuleItemContainer.class).create();
	}

	private class AddModuleContainerCallback implements AddCallback<IModuleItemContainer>{

		@Override
		public void onAdd(IModuleItemContainer obj, int id, Map<ResourceLocation, ?> slaveset) {
			Item item = obj.getItemStack().getItem();
			if(ModuleManager.moduleItems.equals(item)){
				ModuleManager.moduleItems.add(obj.getItemStack().getItem());
			}

		}

	}

	public static File configFolder;
	public static File configFile;

	public static IForgeRegistry<IModule> iModuleRegistry;
	public static IForgeRegistry<IModuleItemContainer> iModuleContainerRegistry;

	@Instance(Constants.MODID)
	public static ModularMachines instance;

	@SidedProxy(clientSide = "de.nedelosk.modularmachines.client.core.ClientProxy", serverSide = "de.nedelosk.modularmachines.common.core.CommonProxy")

	public static CommonProxy proxy;
	public static ModRegistry registry;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit();
		registry = new ModRegistry();
		new ModuleLoadManager();
		configFolder = new File(event.getModConfigurationDirectory(), "modularmachines");
		configFile = new File(configFolder, "Modular-Machines.cfg");
		registry.preInit(instance, event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		registry.init(instance, event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ModuleLoadManager.loadModules();
		ModuleLoadManager.loadModuleContainers();
		registry.postInit(instance, event);
		RecipeJsonManager.checkRecipes();
	}
}
