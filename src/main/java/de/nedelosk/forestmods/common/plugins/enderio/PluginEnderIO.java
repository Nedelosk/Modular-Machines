package de.nedelosk.forestmods.common.plugins.enderio;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import de.nedelosk.forestcore.plugins.APlugin;
import de.nedelosk.forestmods.api.material.EnumMaterials;
import de.nedelosk.forestmods.api.modules.storage.IModuleBattery;
import de.nedelosk.forestmods.api.utils.IModuleRegistry;
import de.nedelosk.forestmods.api.utils.ModuleManager;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.config.Config;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginEnderIO extends APlugin {

	private IModuleRegistry moduleRegistry = de.nedelosk.forestmods.api.utils.ModuleManager.moduleRegistry;
	public Item capacitorBank = GameRegistry.findItem(getRequiredMod(), "blockCapBank");
	public Item capacitor = GameRegistry.findItem(getRequiredMod(), "itemBasicCapacitor");
	public Item tanks = GameRegistry.findItem(getRequiredMod(), "blockTank");
	public IModuleBattery moduleCapitorBankBasic;
	public IModuleBattery moduleCapitorBank;
	public IModuleBattery moduleCapitorBankVibrant;
	// public IModuleTank moduleTankEnderIO;

	@Override
	public void init() {
		super.init();
		moduleCapitorBankBasic = ModuleManager.moduleRegistry.registerModule(EnumMaterials.IRON, "batterys.capitorbanks",
				new ModuleCapitorBank("basic", new EnergyStorage(1000000, 1000)));
		moduleCapitorBank = ModuleManager.moduleRegistry.registerModule(EnumMaterials.BRONZE, "batterys.capitorbanks",
				new ModuleCapitorBank("default", new EnergyStorage(15000000, 5000)));
		moduleCapitorBankVibrant = ModuleManager.moduleRegistry.registerModule(EnumMaterials.STEEL, "batterys.capitorbanks",
				new ModuleCapitorBank("vibrant", new EnergyStorage(25000000, 25000)));
		// moduleTankEnderIO = ModuleRegistry.registerModule(new
		// ModuleTankEnderIO("TankEnderIO"));
	}

	@Override
	public void postInit() {
		ModuleManager.moduleRegistry.registerItemForModule(new ItemStack(capacitorBank, 1, 1),
				new ModuleStack("batterys.capitorbanks", EnumMaterials.IRON, moduleCapitorBankBasic), true);
		ModuleManager.moduleRegistry.registerItemForModule(new ItemStack(capacitorBank, 1, 2),
				new ModuleStack("batterys.capitorbanks", EnumMaterials.BRONZE, moduleCapitorBank), true);
		ModuleManager.moduleRegistry.registerItemForModule(new ItemStack(capacitorBank, 1, 3),
				new ModuleStack("batterys.capitorbanks", EnumMaterials.STEEL, moduleCapitorBankVibrant), true);
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
