package de.nedelosk.forestmods.common.plugins.enderio;

import static de.nedelosk.forestmods.api.utils.ModuleRegistry.addModuleToItem;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import de.nedelosk.forestcore.plugins.APlugin;
import de.nedelosk.forestmods.api.modular.material.Materials;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBattery;
import de.nedelosk.forestmods.api.modules.storage.tanks.IModuleTank;
import de.nedelosk.forestmods.api.utils.ModuleRegistry;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.common.config.Config;
import de.nedelosk.forestmods.common.modules.storage.ModuleBatteryType;
import de.nedelosk.forestmods.common.modules.storage.ModuleTankType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginEnderIO extends APlugin {

	public Item capacitorBank = GameRegistry.findItem(getRequiredMod(), "blockCapBank");
	public Item capacitor = GameRegistry.findItem(getRequiredMod(), "itemBasicCapacitor");
	public Item tanks = GameRegistry.findItem(getRequiredMod(), "blockTank");
	public IModuleBattery moduleCapitorBank;
	public IModuleTank moduleTankEnderIO;

	@Override
	public void init() {
		super.init();
		moduleCapitorBank = ModuleRegistry.registerModule(new ModuleCapitorBank("CapacitorBank"));
		moduleTankEnderIO = ModuleRegistry.registerModule(new ModuleTankEnderIO("TankEnderIO"));
	}

	@Override
	public void postInit() {
		addModuleToItem(new ItemStack(capacitorBank, 1, 1), moduleCapitorBank, new ModuleBatteryType(new EnergyStorage(250000, 1000, 500)), Materials.IRON,
				true);
		addModuleToItem(new ItemStack(capacitorBank, 1, 2), moduleCapitorBank, new ModuleBatteryType(new EnergyStorage(1250000, 2500, 2500)), Materials.BRONZE,
				true);
		addModuleToItem(new ItemStack(capacitorBank, 1, 3), moduleCapitorBank, new ModuleBatteryType(new EnergyStorage(6250000, 12500, 12500)), Materials.STEEL,
				true);
		// addModuleToItem(new ItemStack(capacitor, 1, 0), new
		// ModuleCapacitor("CapacitorBasic", 10, 20), Materials.IRON);
		// addModuleToItem(new ItemStack(capacitor, 1, 1), new
		// ModuleCapacitor("CapacitorDoubleLayer", 20, 40), Materials.BRONZE);
		// addModuleToItem(new ItemStack(capacitor, 1, 2), new
		// ModuleCapacitor("CapacitorVibrant", 40, 60), Materials.STEEL);
		addModuleToItem(new ItemStack(tanks), moduleTankEnderIO, new ModuleTankType(16000), Materials.IRON);
		addModuleToItem(new ItemStack(tanks, 1, 1), moduleTankEnderIO, new ModuleTankType(32000), Materials.BRONZE);ModuleStack<IModule, IModuleSaver>
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
