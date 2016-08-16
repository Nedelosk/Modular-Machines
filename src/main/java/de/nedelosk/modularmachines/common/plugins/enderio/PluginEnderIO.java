package de.nedelosk.modularmachines.common.plugins.enderio;

import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.api.modules.items.IModuleContainer;
import de.nedelosk.modularmachines.api.modules.items.ModuleContainer;
import de.nedelosk.modularmachines.api.modules.storaged.EnumModuleSize;
import de.nedelosk.modularmachines.api.modules.storaged.storage.IModuleBatteryProperties;
import de.nedelosk.modularmachines.api.modules.storaged.storage.ModuleBatteryProperties;
import de.nedelosk.modularmachines.common.config.Config;
import de.nedelosk.modularmachines.common.plugins.APlugin;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PluginEnderIO extends APlugin {

	public static final String MOD_ID = "EnderIO";
	public static Item capacitorBank;
	public static ModuleCapitorBank moduleCapacitorBank;
	public static IModuleBatteryProperties[] moduleCapacitorBankProperties = new IModuleBatteryProperties[3];
	public static IModuleContainer[] moduleCapacitorBankContainers = new IModuleContainer[3];

	@Override
	public void preInit() {
		moduleCapacitorBank = new ModuleCapitorBank();
		moduleCapacitorBank.setRegistryName(new ResourceLocation("modularmachines:module.battery.capacitorbank"));
		GameRegistry.register(moduleCapacitorBank);

		moduleCapacitorBankProperties[0] = new ModuleBatteryProperties(2, EnumModuleSize.LARGE, 1000000, 1000);
		moduleCapacitorBankProperties[1] = new ModuleBatteryProperties(4, EnumModuleSize.LARGE, 15000000, 5000);
		moduleCapacitorBankProperties[2] = new ModuleBatteryProperties(6, EnumModuleSize.LARGE, 25000000, 25000);

	}

	@Override
	public void init() {
		capacitorBank = ForgeRegistries.ITEMS.getValue(new ResourceLocation(getRequiredMod(), "blockCapBank"));

		moduleCapacitorBankContainers[0] = GameRegistry.register(new ModuleContainer(moduleCapacitorBank, moduleCapacitorBankProperties[0], new ItemStack(capacitorBank, 1, 1), EnumMetalMaterials.IRON, true));
		moduleCapacitorBankContainers[1] = GameRegistry.register(new ModuleContainer(moduleCapacitorBank, moduleCapacitorBankProperties[1], new ItemStack(capacitorBank, 1, 2), EnumMetalMaterials.BRONZE, true));
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
