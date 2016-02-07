package de.nedelosk.forestmods.common.plugins.thermalexpansion;

import static de.nedelosk.forestmods.api.utils.ModuleRegistry.addModuleToItem;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import de.nedelosk.forestcore.plugins.APlugin;
import de.nedelosk.forestmods.api.modular.material.Materials;
import de.nedelosk.forestmods.api.modules.basic.IModuleCasing;
import de.nedelosk.forestmods.api.modules.storage.battery.IModuleBattery;
import de.nedelosk.forestmods.api.modules.storage.tanks.IModuleTank;
import de.nedelosk.forestmods.api.utils.ModuleRegistry;
import de.nedelosk.forestmods.common.config.Config;
import de.nedelosk.forestmods.common.modules.basic.ModuleCasing;
import de.nedelosk.forestmods.common.modules.basic.ModuleCasingType;
import de.nedelosk.forestmods.common.modules.storage.ModuleBatteryType;
import de.nedelosk.forestmods.common.modules.storage.ModuleTankType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginThermalExpansion extends APlugin {

	public static Item cell;
	public static Item frame;
	public static Item tank;
	public static Item strongBox;
	public static Item capacitor;
	public static IModuleCasing moduleFrame;
	public static IModuleTank modulePortableTank;
	public static IModuleBattery moduleEnergyCell;

	@Override
	public void init() {
		super.init();
		moduleFrame = ModuleRegistry.registerModule(new ModuleCasing("TE_Frame"));
		modulePortableTank = ModuleRegistry.registerModule(new ModulePortableTank("PortableTank"));
		moduleEnergyCell = ModuleRegistry.registerModule(new ModuleEnergyCell("EnergyCell"));
	}

	@Override
	public void postInit() {
		cell = GameRegistry.findItem(getRequiredMod(), "Cell");
		frame = GameRegistry.findItem(getRequiredMod(), "Frame");
		tank = GameRegistry.findItem(getRequiredMod(), "Tank");
		strongBox = GameRegistry.findItem(getRequiredMod(), "Strongbox");
		capacitor = GameRegistry.findItem(getRequiredMod(), "capacitor");
		addModuleToItem(new ItemStack(frame, 1, 0), moduleFrame, new ModuleCasingType(), Materials.IRON);
		addModuleToItem(new ItemStack(frame, 1, 1), moduleFrame, new ModuleCasingType(), Materials.Electrum);
		addModuleToItem(new ItemStack(frame, 1, 2), moduleFrame, new ModuleCasingType(), Materials.Signalum);
		addModuleToItem(new ItemStack(frame, 1, 3), moduleFrame, new ModuleCasingType(), Materials.Enderium);
		addModuleToItem(new ItemStack(tank, 1, 1), modulePortableTank, new ModuleTankType(8000), Materials.IRON);
		addModuleToItem(new ItemStack(tank, 1, 2), modulePortableTank, new ModuleTankType(32000), Materials.Invar);
		addModuleToItem(new ItemStack(tank, 1, 3), modulePortableTank, new ModuleTankType(128000), Materials.OBSIDIAN);
		addModuleToItem(new ItemStack(tank, 1, 4), modulePortableTank, new ModuleTankType(512000), Materials.Enderium);
		addModuleToItem(new ItemStack(cell, 1, 1), moduleEnergyCell, new ModuleBatteryType(new EnergyStorage(100000, 100, 100)), Materials.Lead, true);
		addModuleToItem(new ItemStack(cell, 1, 2), moduleEnergyCell, new ModuleBatteryType(new EnergyStorage(500000, 400, 400)), Materials.Invar, true);
		addModuleToItem(new ItemStack(cell, 1, 3), moduleEnergyCell, new ModuleBatteryType(new EnergyStorage(5000000, 4000, 4000)), Materials.Electrum, true);
		addModuleToItem(new ItemStack(cell, 1, 4), moduleEnergyCell, new ModuleBatteryType(new EnergyStorage(20000000, 16000, 16000)), Materials.Enderium,
				true);
		/*
		 * registerProducer(new ItemStack(strongBox, 1, 1), STRONGBOX, new
		 * ModuleSimpleChest("StrongBox", 18), Materials.IRON);
		 * registerProducer(new ItemStack(strongBox, 1, 2), STRONGBOX, new
		 * ModuleSimpleChest("StrongBoxHardende", 36), Materials.Invar);
		 * registerProducer(new ItemStack(strongBox, 1, 3), STRONGBOX, new
		 * ModuleSimpleChest("StrongBoxReinforced", 54), Materials.OBSIDIAN);
		 * registerProducer(new ItemStack(strongBox, 1, 4), STRONGBOX, new
		 * ModuleSimpleChest("StrongBoxResonant", 72), Materials.Enderium);
		 */
		// addModuleToItem(new ItemStack(capacitor, 1, 1), new
		// ModuleCapacitor("CapacitorWood", 7, 15), Materials.WOOD);
		// addModuleToItem(new ItemStack(capacitor, 1, 2), new
		// ModuleCapacitor("CapacitorLead", 10, 20), Materials.Lead);
		// addModuleToItem(new ItemStack(capacitor, 1, 3), new
		// ModuleCapacitor("CapacitorInvar", 15, 30), Materials.Invar);
		// addModuleToItem(new ItemStack(capacitor, 1, 4), new
		// ModuleCapacitor("CapacitorElectrum", 20, 40), Materials.Electrum);
		// addModuleToItem(new ItemStack(capacitor, 1, 5), new
		// ModuleCapacitor("CapacitorEnderium", 40, 80), Materials.Enderium);
	}

	@Override
	public String getRequiredMod() {
		return "ThermalExpansion";
	}

	@Override
	public boolean getConfigOption() {
		return Config.pluginThermalExpansion;
	}
}
