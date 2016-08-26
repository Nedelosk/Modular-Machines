package de.nedelosk.modularmachines.common.plugins.mekanism;

import cofh.api.energy.IEnergyContainerItem;
import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.api.material.EnumVanillaMaterials;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.storage.IModuleBatteryProperties;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.plugins.APlugin;
import de.nedelosk.modularmachines.common.plugins.cofh.ModuleRFBattery;
import de.nedelosk.modularmachines.common.plugins.cofh.ModuleRFBatteryProperties;
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
		moduleEnergyCube = new ModuleRFBattery("energycube");
		moduleEnergyCube.setRegistryName(new ResourceLocation("modularmachines:battery.energycube"));
		GameRegistry.register(moduleEnergyCube);
	}

	@Override
	public void init() {
		energyCube = ForgeRegistries.ITEMS.getValue(new ResourceLocation("mekanism", "EnergyCube"));

		moduleEnergyCubeProperties[0] = new ModuleRFBatteryProperties(2, EnumModuleSize.LARGE, 2000000, 800, (IEnergyContainerItem) energyCube);
		moduleEnergyCubeProperties[1] = new ModuleRFBatteryProperties(4, EnumModuleSize.LARGE, 8000000, 3200, (IEnergyContainerItem) energyCube);
		moduleEnergyCubeProperties[2] = new ModuleRFBatteryProperties(6, EnumModuleSize.LARGE, 32000000, 12800, (IEnergyContainerItem) energyCube);
		moduleEnergyCubeProperties[3] = new ModuleRFBatteryProperties(8, EnumModuleSize.LARGE, 128000000, 51200, (IEnergyContainerItem) energyCube);

		moduleEnergyCubeContainers[0] = GameRegistry.register(new ModuleContainerEnergyCube(moduleEnergyCube, moduleEnergyCubeProperties[0], EnumMetalMaterials.IRON, 0));
		moduleEnergyCubeContainers[1] = GameRegistry.register(new ModuleContainerEnergyCube(moduleEnergyCube, moduleEnergyCubeProperties[1], EnumMetalMaterials.OSMIUM, 1));
		moduleEnergyCubeContainers[2] = GameRegistry.register(new ModuleContainerEnergyCube(moduleEnergyCube, moduleEnergyCubeProperties[2], EnumMetalMaterials.GOLD, 2));
		moduleEnergyCubeContainers[3] = GameRegistry.register(new ModuleContainerEnergyCube(moduleEnergyCube, moduleEnergyCubeProperties[3], EnumVanillaMaterials.DIAMOND, 3));
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
