package de.nedelosk.modularmachines.common.plugins.enderio;

import static de.nedelosk.modularmachines.api.modules.ModuleManager.register;

import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.api.modules.EnumModuleSizes;
import de.nedelosk.modularmachines.api.modules.containers.IModuleItemContainer;
import de.nedelosk.modularmachines.api.modules.containers.ModuleContainer;
import de.nedelosk.modularmachines.api.modules.containers.ModuleItemContainer;
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

public class PluginEnderIO extends APlugin {

	public static final String MOD_ID = "EnderIO";
	public static Item capacitorBank;
	public static ModuleRFBattery moduleCapacitorBank;
	public static IModuleBatteryProperties[] moduleCapacitorBankProperties = new IModuleBatteryProperties[3];
	public static IModuleItemContainer[] moduleCapacitorBankContainers = new IModuleItemContainer[3];

	@Override
	public void preInit() {
		if(ModuleRFBatteryPropertiesLoader.loader == null){
			ModuleLoaderRegistry.registerLoader(EnumLoaderType.PROPERTY, ModuleRFBatteryPropertiesLoader.loader = new ModuleRFBatteryPropertiesLoader());
		}
		moduleCapacitorBank = new ModuleRFBattery("capacitor_bank");
		register(moduleCapacitorBank, "battery.enderio.capacitorbank");
	}

	@Override
	public void init() {
		capacitorBank = ForgeRegistries.ITEMS.getValue(new ResourceLocation(getRequiredMod(), "blockCapBank"));

		moduleCapacitorBankProperties[0] = new ModuleRFBatteryProperties(2, 1000000, 1000, 2);
		moduleCapacitorBankProperties[1] = new ModuleRFBatteryProperties(4, 15000000, 5000, 3);
		moduleCapacitorBankProperties[2] = new ModuleRFBatteryProperties(6, 25000000, 25000, 4);

		moduleCapacitorBankContainers[0] = register(new ModuleItemContainer(new ItemStack(capacitorBank, 1, 1), EnumMetalMaterials.BRONZE, EnumModuleSizes.LARGE, true, new ModuleContainer(moduleCapacitorBank, moduleCapacitorBankProperties[0])), "battery.enderio.capacitorBank-basic");
		moduleCapacitorBankContainers[1] = register(new ModuleItemContainer(new ItemStack(capacitorBank, 1, 2), EnumMetalMaterials.IRON, EnumModuleSizes.LARGE, true, new ModuleContainer(moduleCapacitorBank, moduleCapacitorBankProperties[1])), "battery.enderio.capacitorBank");
		moduleCapacitorBankContainers[2] = register(new ModuleItemContainer(new ItemStack(capacitorBank, 1, 3), EnumMetalMaterials.STEEL, EnumModuleSizes.LARGE, true, new ModuleContainer(moduleCapacitorBank, moduleCapacitorBankProperties[2])), "battery.enderio.capacitorBank.vibrant");
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
