package de.nedelosk.forestmods.common.plugins.enderio;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import de.nedelosk.forestmods.common.config.Config;
import de.nedelosk.forestmods.library.material.EnumMaterials;
import de.nedelosk.forestmods.library.modules.ModuleManager;
import de.nedelosk.forestmods.library.modules.ModuleUID;
import de.nedelosk.forestmods.library.plugins.APlugin;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginEnderIO extends APlugin {

	public static final String capitorBanks = "batterys.capitorbanks";
	public static final ModuleUID capacitorBankBasicUID = new ModuleUID(capitorBanks, "basic");
	public static final ModuleUID capacitorBankUID = new ModuleUID(capitorBanks, "default");
	public static final ModuleUID capacitorBankVibrantUID = new ModuleUID(capitorBanks, "vibrant");
	public Item capacitorBank = GameRegistry.findItem(getRequiredMod(), "blockCapBank");
	public Item capacitor = GameRegistry.findItem(getRequiredMod(), "itemBasicCapacitor");
	public Item tanks = GameRegistry.findItem(getRequiredMod(), "blockTank");
	// public IModuleTank moduleTankEnderIO;

	@Override
	public void init() {
		// moduleTankEnderIO = ModuleRegistry.registerModule(new
		// ModuleTankEnderIO("TankEnderIO"));
	}

	@Override
	public void postInit() {
		ModuleManager.moduleRegistry.registerModuleAndItem(EnumMaterials.IRON, capacitorBankBasicUID, new ItemStack(capacitorBank, 1, 1),
				ModuleCapitorBank.class, true, new EnergyStorage(1000000, 1000));
		ModuleManager.moduleRegistry.registerModuleAndItem(EnumMaterials.BRONZE, capacitorBankUID, new ItemStack(capacitorBank, 1, 2), ModuleCapitorBank.class,
				true, new EnergyStorage(15000000, 5000));
		ModuleManager.moduleRegistry.registerModuleAndItem(EnumMaterials.STEEL, capacitorBankVibrantUID, new ItemStack(capacitorBank, 1, 3),
				ModuleCapitorBank.class, true, new EnergyStorage(25000000, 25000));
		/*
		 * addModuleToItem(new ItemStack(capacitorBank, 1, 1),
		 * moduleCapitorBank, new ModuleBatteryType(new EnergyStorage(1000000,
		 * 1000)), Materials.IRON, true); addModuleToItem(new
		 * ItemStack(capacitorBank, 1, 2), moduleCapitorBank, new
		 * ModuleBatteryType(new EnergyStorage(5000000, 5000)),
		 * Materials.BRONZE, true); addModuleToItem(new ItemStack(capacitorBank,
		 * 1, 3), moduleCapitorBank, new ModuleBatteryType(new
		 * EnergyStorage(25000000, 25000)), Materials.STEEL, true);
		 */
		// addModuleToItem(new ItemStack(capacitor, 1, 0), new
		// ModuleCapacitor("CapacitorBasic", 10, 20), Materials.IRON);
		// addModuleToItem(new ItemStack(capacitor, 1, 1), new
		// ModuleCapacitor("CapacitorDoubleLayer", 20, 40), Materials.BRONZE);
		// addModuleToItem(new ItemStack(capacitor, 1, 2), new
		// ModuleCapacitor("CapacitorVibrant", 40, 60), Materials.STEEL);
		// addModuleToItem(new ItemStack(tanks), moduleTankEnderIO, new
		// ModuleTankType(16000), Materials.IRON);
		// addModuleToItem(new ItemStack(tanks, 1, 1), moduleTankEnderIO, new
		// ModuleTankType(32000), Materials.BRONZE);ModuleStack<IModule>
	}

	@Override
	public String getRequiredMod() {
		return "EnderIO";
	}

	@Override
	public boolean getConfigOption() {
		return Config.pluginEnderIO;
	}
}
