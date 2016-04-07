package de.nedelosk.forestmods.common.plugins.thermalexpansion;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import de.nedelosk.forestcore.plugins.APlugin;
import de.nedelosk.forestmods.api.material.EnumMaterials;
import de.nedelosk.forestmods.api.modules.casing.IModuleCasing;
import de.nedelosk.forestmods.api.modules.storage.IModuleBattery;
import de.nedelosk.forestmods.api.utils.ModuleManager;
import de.nedelosk.forestmods.api.utils.ModuleStack;
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
	public void init() {
		moduleFrameBasic = ModuleManager.moduleRegistry.registerModule(EnumMaterials.IRON, "casings.frames", new ModuleCasing("basic", 0, 0, 0));
		moduleFrameHardened = ModuleManager.moduleRegistry.registerModule(EnumMaterials.INVAR, "casings.frames", new ModuleCasing("hardened", 0, 0, 0));
		moduleFrameReinforced = ModuleManager.moduleRegistry.registerModule(EnumMaterials.SIGNALUM, "casings.frames", new ModuleCasing("reinforced", 0, 0, 0));
		moduleFrameResonant = ModuleManager.moduleRegistry.registerModule(EnumMaterials.ENDERIUM, "casings.frames", new ModuleCasing("resonant", 0, 0, 0));
		moduleEnergyCellLeadstone = ModuleManager.moduleRegistry.registerModule(EnumMaterials.LEAD, "batterys.energycells",
				new ModuleEnergyCell("leadstone", new EnergyStorage(400000, 200)));
		moduleEnergyCellHardened = ModuleManager.moduleRegistry.registerModule(EnumMaterials.INVAR, "batterys.energycells",
				new ModuleEnergyCell("hardened", new EnergyStorage(2000000, 800)));
		moduleEnergyCellRedstone = ModuleManager.moduleRegistry.registerModule(EnumMaterials.ELECTRUM, "batterys.energycells",
				new ModuleEnergyCell("redstone", new EnergyStorage(20000000, 8000)));
		moduleEnergyCellResonant = ModuleManager.moduleRegistry.registerModule(EnumMaterials.ENDERIUM, "batterys.energycells",
				new ModuleEnergyCell("resonant", new EnergyStorage(80000000, 32000)));
	}

	@Override
	public void postInit() {
		cell = GameRegistry.findItem(getRequiredMod(), "Cell");
		frame = GameRegistry.findItem(getRequiredMod(), "Frame");
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
		ModuleManager.moduleRegistry.registerItemForModule(new ItemStack(frame, 1, 0), new ModuleStack("casings.frames", EnumMaterials.IRON, moduleFrameBasic));
		ModuleManager.moduleRegistry.registerItemForModule(new ItemStack(frame, 1, 1),
				new ModuleStack("casings.frames", EnumMaterials.INVAR, moduleFrameHardened));
		ModuleManager.moduleRegistry.registerItemForModule(new ItemStack(frame, 1, 2),
				new ModuleStack("casings.frames", EnumMaterials.SIGNALUM, moduleFrameReinforced));
		ModuleManager.moduleRegistry.registerItemForModule(new ItemStack(frame, 1, 3),
				new ModuleStack("casings.frames", EnumMaterials.ENDERIUM, moduleFrameResonant));
		ModuleManager.moduleRegistry.registerItemForModule(new ItemStack(cell, 1, 1),
				new ModuleStack("batterys.energycells", EnumMaterials.LEAD, moduleEnergyCellLeadstone), true);
		ModuleManager.moduleRegistry.registerItemForModule(new ItemStack(cell, 1, 2),
				new ModuleStack("batterys.energycells", EnumMaterials.INVAR, moduleEnergyCellHardened), true);
		ModuleManager.moduleRegistry.registerItemForModule(new ItemStack(cell, 1, 3),
				new ModuleStack("batterys.energycells", EnumMaterials.ELECTRUM, moduleEnergyCellRedstone), true);
		ModuleManager.moduleRegistry.registerItemForModule(new ItemStack(cell, 1, 4),
				new ModuleStack("batterys.energycells", EnumMaterials.ENDERIUM, moduleEnergyCellResonant), true);
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
