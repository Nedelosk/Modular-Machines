package de.nedelosk.modularmachines.common.plugins.ic2;

import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.IModule;
import de.nedelosk.modularmachines.api.modules.IModuleProperties;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.items.ModuleContainer;
import de.nedelosk.modularmachines.api.modules.properties.ModuleCasingProperties;
import de.nedelosk.modularmachines.api.modules.storage.energy.IModuleBatteryProperties;
import de.nedelosk.modularmachines.api.modules.storage.energy.ModuleBatteryProperties;
import de.nedelosk.modularmachines.common.core.ModuleManager;
import de.nedelosk.modularmachines.common.modules.storages.ModuleBattery;
import de.nedelosk.modularmachines.common.plugins.APlugin;
import ic2.api.item.IC2Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PluginIC2 extends APlugin {

	public static ItemStack batBox; 
	public static ItemStack cesu; 
	public static ItemStack mfe; 
	public static ItemStack mfsu; 
	public static ItemStack casing;
	public static ItemStack casingAdvanced;

	public static ModuleBattery moduleBattery;
	public static IModuleBatteryProperties[] moduleBatteryProperties = new IModuleBatteryProperties[4];
	public static IModuleContainer[] moduleBatteryContainers = new IModuleContainer[4];
	public static ModuleCasingProperties[] moduleCasingProperties = new ModuleCasingProperties[2];

	@Override
	public void preInit() {
		moduleBattery = new ModuleEUBattery();
		moduleBattery.setRegistryName(new ResourceLocation("modularmachines:battery.ic2"));
		GameRegistry.register(moduleBattery);
	}

	@Override
	public void init() {
		batBox = IC2Items.getItem("te", "batbox");
		cesu = IC2Items.getItem("te", "cesu");
		mfe = IC2Items.getItem("te", "mfe");
		mfsu = IC2Items.getItem("te", "mfsu");
		casing = IC2Items.getItem("resource", "machine");
		casingAdvanced = IC2Items.getItem("resource", "advanced_machine");

		moduleBatteryProperties[0] = new ModuleBatteryProperties(2, EnumModuleSizes.LARGE, 80000, 320, 1);
		moduleBatteryProperties[1] = new ModuleBatteryProperties(4, EnumModuleSizes.LARGE, 600000, 1280, 2);
		moduleBatteryProperties[2] = new ModuleBatteryProperties(6, EnumModuleSizes.LARGE, 8000000, 5120, 3);
		moduleBatteryProperties[3] = new ModuleBatteryProperties(8, EnumModuleSizes.LARGE, 80000000, 20480, 4);

		moduleBatteryContainers[0] = GameRegistry.register(new ModuleContainer(moduleBattery, moduleBatteryProperties[0], batBox, EnumMetalMaterials.TIN, true));
		moduleBatteryContainers[1] = GameRegistry.register(new ModuleContainer(moduleBattery, moduleBatteryProperties[1], cesu, EnumMetalMaterials.BRONZE, true));
		moduleBatteryContainers[2] = GameRegistry.register(new ModuleContainer(moduleBattery, moduleBatteryProperties[2], mfe, EnumMetalMaterials.IRON, true));
		moduleBatteryContainers[3] = GameRegistry.register(new ModuleContainer(moduleBattery, moduleBatteryProperties[3], mfsu, EnumMetalMaterials.STEEL, true));

		moduleCasingProperties[0] = new ModuleCasingProperties(2, EnumModuleSizes.LARGE, 12, 550, 7.0F, 1.5F, 1, "pickaxe");
		moduleCasingProperties[1] = new ModuleCasingProperties(4, EnumModuleSizes.LARGE, 20, 700, 9.0F, 2.54F, 1, "pickaxe");

		IModule engine = ModuleManager.moduleEngineElectric;
		IModuleProperties properties = ModuleManager.moduleEngineElectricProperties[1];
		GameRegistry.register(new ModuleContainer(engine, properties, IC2Items.getItem("crafting", "electric_motor"), EnumMetalMaterials.IRON, false));

		GameRegistry.register(new ModuleContainer(ModuleManager.moduleCasing, moduleCasingProperties[0], casing, EnumMetalMaterials.IRON));
		GameRegistry.register(new ModuleContainer(ModuleManager.moduleCasing, moduleCasingProperties[1], casingAdvanced, EnumMetalMaterials.STEEL));
	}

	@Override
	public String getRequiredMod() {
		return "IC2";
	}
}
