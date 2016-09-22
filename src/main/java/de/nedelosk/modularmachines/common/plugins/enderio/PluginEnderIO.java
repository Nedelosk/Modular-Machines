package de.nedelosk.modularmachines.common.plugins.enderio;

import cofh.api.energy.IEnergyContainerItem;
import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.items.ModuleContainer;
import de.nedelosk.modularmachines.api.modules.json.EnumLoaderType;
import de.nedelosk.modularmachines.api.modules.json.ModuleLoaderRegistry;
import de.nedelosk.modularmachines.api.modules.storage.energy.IModuleBatteryProperties;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.plugins.APlugin;
import de.nedelosk.modularmachines.common.plugins.cofh.ModuleRFBattery;
import de.nedelosk.modularmachines.common.plugins.cofh.ModuleRFBatteryProperties;
import de.nedelosk.modularmachines.common.plugins.cofh.ModuleRFBatteryPropertiesLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PluginEnderIO extends APlugin {

	public static final String MOD_ID = "EnderIO";
	public static Item capacitorBank;
	public static ModuleRFBattery moduleCapacitorBank;
	public static IModuleBatteryProperties[] moduleCapacitorBankProperties = new IModuleBatteryProperties[3];
	public static IModuleContainer[] moduleCapacitorBankContainers = new IModuleContainer[3];

	@Override
	public void preInit() {
		if(ModuleRFBatteryPropertiesLoader.loader == null){
			ModuleLoaderRegistry.registerLoader(EnumLoaderType.PROPERTY, new ModuleRFBatteryPropertiesLoader());
		}
		moduleCapacitorBank = new ModuleRFBattery("capacitor_bank");
		moduleCapacitorBank.setRegistryName(new ResourceLocation("modularmachines:battery.capacitorbank"));
		GameRegistry.register(moduleCapacitorBank);
	}

	@Override
	public void init() {
		capacitorBank = ForgeRegistries.ITEMS.getValue(new ResourceLocation(getRequiredMod(), "blockCapBank"));

		moduleCapacitorBankProperties[0] = new ModuleRFBatteryProperties(2, EnumModuleSizes.LARGE, 1000000, 1000, 2);
		moduleCapacitorBankProperties[1] = new ModuleRFBatteryProperties(4, EnumModuleSizes.LARGE, 15000000, 5000, 3);
		moduleCapacitorBankProperties[2] = new ModuleRFBatteryProperties(6, EnumModuleSizes.LARGE, 25000000, 25000, 4);

		moduleCapacitorBankContainers[0] = GameRegistry.register(new ModuleContainer(moduleCapacitorBank, moduleCapacitorBankProperties[0], new ItemStack(capacitorBank, 1, 1), EnumMetalMaterials.BRONZE, true));
		moduleCapacitorBankContainers[1] = GameRegistry.register(new ModuleContainer(moduleCapacitorBank, moduleCapacitorBankProperties[1], new ItemStack(capacitorBank, 1, 2), EnumMetalMaterials.IRON, true));
		moduleCapacitorBankContainers[2] = GameRegistry.register(new ModuleContainer(moduleCapacitorBank, moduleCapacitorBankProperties[2], new ItemStack(capacitorBank, 1, 3), EnumMetalMaterials.STEEL, true));
	}

	@Override
	public String getRequiredMod() {
		return MOD_ID;
	}

	@Override
	public boolean isActive() {
		return Config.pluginEnderIO;
	}
}
