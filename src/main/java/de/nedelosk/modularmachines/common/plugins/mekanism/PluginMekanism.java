package de.nedelosk.modularmachines.common.plugins.mekanism;


import static de.nedelosk.modularmachines.api.modules.ModuleManager.register;

import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemContainer;
import de.nedelosk.modularmachines.api.modules.containers.ModuleContainer;
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
	public static IModuleItemContainer[] moduleEnergyCubeContainers = new IModuleItemContainer[4];

	@Override
	public void preInit() {
		if(ModuleRFBatteryPropertiesLoader.loader == null){
			ModuleLoaderRegistry.registerLoader(EnumLoaderType.PROPERTY, ModuleRFBatteryPropertiesLoader.loader = new ModuleRFBatteryPropertiesLoader());
		}
		moduleEnergyCube = new ModuleRFBattery("energy_cube");
		moduleEnergyCube.setRegistryName(new ResourceLocation("modularmachines:battery.energycube"));
		GameRegistry.register(moduleEnergyCube);
	}

	@Override
	public void init() {
		energyCube = ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism", "EnergyCube"));

		moduleEnergyCubeProperties[0] = new ModuleRFBatteryProperties(2, (int) (2000000 * 0.4), 800, 1);
		moduleEnergyCubeProperties[1] = new ModuleRFBatteryProperties(4, (int) (8000000 * 0.4), 3200, 2);
		moduleEnergyCubeProperties[2] = new ModuleRFBatteryProperties(6, (int) (32000000 * 0.4), 12800, 3);
		moduleEnergyCubeProperties[3] = new ModuleRFBatteryProperties(8, (int) (128000000 * 0.4), 51200, 4);

		moduleEnergyCubeContainers[0] = register(new ModuleItemContainerEnergyCube(EnumMetalMaterials.IRON, 0, new ModuleContainer(moduleEnergyCube, moduleEnergyCubeProperties[0])), "battery.mekanism.energycube.0");
		moduleEnergyCubeContainers[1] = register(new ModuleItemContainerEnergyCube(EnumMetalMaterials.OSMIUM, 1, new ModuleContainer(moduleEnergyCube, moduleEnergyCubeProperties[1])), "battery.mekanism.energycube.1");
		moduleEnergyCubeContainers[2] = register(new ModuleItemContainerEnergyCube(EnumMetalMaterials.GOLD, 2, new ModuleContainer(moduleEnergyCube, moduleEnergyCubeProperties[2])), "battery.mekanism.energycube.2");
		moduleEnergyCubeContainers[3] = register(new ModuleItemContainerEnergyCube(EnumMetalMaterials.STEEL, 3, new ModuleContainer(moduleEnergyCube, moduleEnergyCubeProperties[3])), "battery.mekanism.energycube.3");
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
