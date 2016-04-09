package de.nedelosk.forestmods.common.plugins.thermalexpansion;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import de.nedelosk.forestcore.plugins.APlugin;
import de.nedelosk.forestmods.api.material.EnumMaterials;
import de.nedelosk.forestmods.api.modules.casing.IModuleCasing;
import de.nedelosk.forestmods.api.modules.storage.IModuleBattery;
import de.nedelosk.forestmods.api.utils.ModuleManager;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import de.nedelosk.forestmods.common.config.Config;
import de.nedelosk.forestmods.common.modules.basic.ModuleCasing;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginThermalExpansion extends APlugin {

	public static Item cell;
	public static Item frame;
	// public static Item tank;
	// public static Item strongBox;
	// public static Item capacitor;
	public static final String energyCells = "batterys.energycells";
	public static final String frames = "casings.frames";
	public static final ModuleUID energyCellLeadstone = new ModuleUID(energyCells, "basic");
	public static final ModuleUID energyCellHardened = new ModuleUID(energyCells, "hardened");
	public static final ModuleUID energyCellReinforced = new ModuleUID(energyCells, "reinforced");
	public static final ModuleUID energyCellResonant = new ModuleUID(energyCells, "resonant");
	public static final ModuleUID frameBasic = new ModuleUID(frames, "leadstone");
	public static final ModuleUID frameHardened = new ModuleUID(frames, "hardened");
	public static final ModuleUID frameReinforced = new ModuleUID(frames, "redstone");
	public static final ModuleUID frameResonant = new ModuleUID(frames, "resonant");
	public static IModuleBattery moduleEnergyCellLeadstone;
	public static IModuleBattery moduleEnergyCellHardened;
	public static IModuleBattery moduleEnergyCellRedstone;
	public static IModuleBattery moduleEnergyCellResonant;
	public static IModuleCasing moduleFrameBasic;
	public static IModuleCasing moduleFrameHardened;
	public static IModuleCasing moduleFrameReinforced;
	public static IModuleCasing moduleFrameResonant;
	// public static IModuleTank modulePortableTank;

	@Override
	public void postInit() {
		cell = GameRegistry.findItem(getRequiredMod(), "Cell");
		frame = GameRegistry.findItem(getRequiredMod(), "Frame");
		ModuleManager.moduleRegistry.registerModuleAndItem(EnumMaterials.IRON, frameBasic, new ItemStack(frame, 1, 0), ModuleCasing.class, false, 0, 0, 0);
		ModuleManager.moduleRegistry.registerModuleAndItem(EnumMaterials.INVAR, frameHardened, new ItemStack(frame, 1, 1), ModuleCasing.class, false, 0, 0, 0);
		ModuleManager.moduleRegistry.registerModuleAndItem(EnumMaterials.SIGNALUM, frameReinforced, new ItemStack(frame, 1, 2), ModuleCasing.class, false, 0, 0,
				0);
		ModuleManager.moduleRegistry.registerModuleAndItem(EnumMaterials.ENDERIUM, frameResonant, new ItemStack(frame, 1, 3), ModuleCasing.class, false, 0, 0,
				0);
		ModuleManager.moduleRegistry.registerModuleAndItem(EnumMaterials.LEAD, energyCellLeadstone, new ItemStack(cell, 1, 1), ModuleEnergyCell.class, true,
				new EnergyStorage(400000, 200));
		ModuleManager.moduleRegistry.registerModuleAndItem(EnumMaterials.INVAR, energyCellHardened, new ItemStack(cell, 1, 2), ModuleEnergyCell.class, true,
				new EnergyStorage(2000000, 800));
		ModuleManager.moduleRegistry.registerModuleAndItem(EnumMaterials.ELECTRUM, energyCellReinforced, new ItemStack(cell, 1, 3), ModuleEnergyCell.class,
				true, new EnergyStorage(20000000, 8000));
		ModuleManager.moduleRegistry.registerModuleAndItem(EnumMaterials.ENDERIUM, energyCellResonant, new ItemStack(cell, 1, 4), ModuleEnergyCell.class, true,
				new EnergyStorage(80000000, 32000));
		// tank = GameRegistry.findItem(getRequiredMod(), "Tank");
		// strongBox = GameRegistry.findItem(getRequiredMod(), "Strongbox");
		// capacitor = GameRegistry.findItem(getRequiredMod(), "capacitor");
		/*
		 * registerItemForModule(new ItemStack(tank, 1, 1), modulePortableTank,
		 * new ModuleTankType(8000), Materials.IRON); registerItemForModule(new
		 * ItemStack(tank, 1, 2), modulePortableTank, new ModuleTankType(32000),
		 * Materials.Invar); registerItemForModule(new ItemStack(tank, 1, 3),
		 * modulePortableTank, new ModuleTankType(128000), Materials.OBSIDIAN);
		 * registerItemForModule(new ItemStack(tank, 1, 4), modulePortableTank,
		 * new ModuleTankType(512000), Materials.Enderium);
		 */
		// registerProducer(new ItemStack(strongBox, 1, 1), STRONGBOX, new
		// ModuleSimpleChest("StrongBox", 18), Materials.IRON);
		// registerProducer(new ItemStack(strongBox, 1, 2), STRONGBOX, new
		// ModuleSimpleChest("StrongBoxHardende", 36), Materials.Invar);
		// registerProducer(new ItemStack(strongBox, 1, 3), STRONGBOX, new
		// ModuleSimpleChest("StrongBoxReinforced", 54), Materials.OBSIDIAN);
		// registerProducer(new ItemStack(strongBox, 1, 4), STRONGBOX, new
		// ModuleSimpleChest("StrongBoxResonant", 72), Materials.Enderium);
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
