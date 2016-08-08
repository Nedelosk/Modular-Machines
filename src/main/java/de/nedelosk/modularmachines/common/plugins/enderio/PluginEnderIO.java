package de.nedelosk.modularmachines.common.plugins.enderio;

import cofh.api.energy.EnergyStorage;
import de.nedelosk.modularmachines.api.material.EnumMetalMaterials;
import de.nedelosk.modularmachines.api.modules.items.ModuleContainer;
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
	public static ModuleCapitorBank moduleCapacitorBankBasic;
	public static ModuleCapitorBank moduleCapacitorBank;
	public static ModuleCapitorBank moduleCapacitorBankVibrant;

	@Override
	public void preInit() {
		moduleCapacitorBankBasic = new ModuleCapitorBank(2, new EnergyStorage(1000000, 1000));
		moduleCapacitorBankBasic.setRegistryName(new ResourceLocation("modularmachines:module.battery.capacitorbank.basic"));
		GameRegistry.register(moduleCapacitorBankBasic);

		moduleCapacitorBank = new ModuleCapitorBank(4, new EnergyStorage(15000000, 5000));
		moduleCapacitorBank.setRegistryName(new ResourceLocation("modularmachines:module.battery.capacitorbank"));
		GameRegistry.register(moduleCapacitorBank);

		moduleCapacitorBankVibrant = new ModuleCapitorBank(6, new EnergyStorage(25000000, 25000));
		moduleCapacitorBankVibrant.setRegistryName(new ResourceLocation("modularmachines:module.battery.capacitorbank.vibrant"));
		GameRegistry.register(moduleCapacitorBankVibrant);

	}

	@Override
	public void init() {
		capacitorBank = ForgeRegistries.ITEMS.getValue(new ResourceLocation(getRequiredMod(), "blockCapBank"));

		GameRegistry.register(new ModuleContainer(moduleCapacitorBankBasic, new ItemStack(capacitorBank, 1, 1), EnumMetalMaterials.IRON, true));
		GameRegistry.register(new ModuleContainer(moduleCapacitorBank, new ItemStack(capacitorBank, 1, 2), EnumMetalMaterials.BRONZE, true));
		GameRegistry.register(new ModuleContainer(moduleCapacitorBankVibrant, new ItemStack(capacitorBank, 1, 3), EnumMetalMaterials.STEEL, true));
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
