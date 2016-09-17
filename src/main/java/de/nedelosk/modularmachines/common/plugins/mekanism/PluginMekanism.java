package de.nedelosk.modularmachines.common.plugins.mekanism;

import cofh.api.energy.IEnergyContainerItem;
import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.json.EnumLoaderType;
import de.nedelosk.modularmachines.api.modules.json.ModuleLoaderRegistry;
import de.nedelosk.modularmachines.api.modules.storage.energy.IModuleBatteryProperties;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.plugins.APlugin;
import de.nedelosk.modularmachines.common.plugins.cofh.ModuleRFBattery;
import de.nedelosk.modularmachines.common.plugins.cofh.ModuleRFBatteryProperties;
import de.nedelosk.modularmachines.common.plugins.cofh.ModuleRFBatteryPropertiesLoader;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PluginMekanism extends APlugin {

	public static Item energyCube;
	public static ModuleRFBattery moduleEnergyCube;
	public static IModuleBatteryProperties[] moduleEnergyCubeProperties = new IModuleBatteryProperties[4];
	public static IModuleContainer[] moduleEnergyCubeContainers = new IModuleContainer[4];

	@Override
	public void preInit() {
		if(ModuleRFBatteryPropertiesLoader.loader == null){
			ModuleLoaderRegistry.registerLoader(EnumLoaderType.PROPERTY, new ModuleRFBatteryPropertiesLoader());
		}
		moduleEnergyCube = new ModuleRFBattery("energy_cube");
		moduleEnergyCube.setRegistryName(new ResourceLocation("modularmachines:battery.energycube"));
		GameRegistry.register(moduleEnergyCube);
	}

	@Override
	public void init() {
		energyCube = ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism", "EnergyCube"));
		IEnergyContainerItem energyItem = (IEnergyContainerItem) energyCube;

		moduleEnergyCubeProperties[0] = new ModuleRFBatteryProperties(2, EnumModuleSizes.LARGE, (int) (2000000 * 0.4), 800, 1, energyItem);
		moduleEnergyCubeProperties[1] = new ModuleRFBatteryProperties(4, EnumModuleSizes.LARGE, (int) (8000000 * 0.4), 3200, 2, energyItem);
		moduleEnergyCubeProperties[2] = new ModuleRFBatteryProperties(6, EnumModuleSizes.LARGE, (int) (32000000 * 0.4), 12800, 3, energyItem);
		moduleEnergyCubeProperties[3] = new ModuleRFBatteryProperties(8, EnumModuleSizes.LARGE, (int) (128000000 * 0.4), 51200, 4, energyItem);

		moduleEnergyCubeContainers[0] = GameRegistry.register(new ModuleContainerEnergyCube(moduleEnergyCube, moduleEnergyCubeProperties[0], EnumMetalMaterials.IRON, 0));
		moduleEnergyCubeContainers[1] = GameRegistry.register(new ModuleContainerEnergyCube(moduleEnergyCube, moduleEnergyCubeProperties[1], EnumMetalMaterials.OSMIUM, 1));
		moduleEnergyCubeContainers[2] = GameRegistry.register(new ModuleContainerEnergyCube(moduleEnergyCube, moduleEnergyCubeProperties[2], EnumMetalMaterials.GOLD, 2));
		moduleEnergyCubeContainers[3] = GameRegistry.register(new ModuleContainerEnergyCube(moduleEnergyCube, moduleEnergyCubeProperties[3], EnumMetalMaterials.STEEL, 3));
	}

	@Override
	public String getRequiredMod() {
		return "Mekanism";
	}

	@Override
	public boolean isActive() {
		return Config.pluginMekanism;
	}
}
