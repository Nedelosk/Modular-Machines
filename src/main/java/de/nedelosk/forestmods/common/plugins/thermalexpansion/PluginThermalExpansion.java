package de.nedelosk.forestmods.common.plugins.thermalexpansion;

import static de.nedelosk.forestmods.api.utils.ModuleRegistry.addModuleToItem;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import de.nedelosk.forestcore.plugins.APlugin;
import de.nedelosk.forestmods.api.modular.material.Materials;
import de.nedelosk.forestmods.common.config.Config;
import de.nedelosk.forestmods.common.modules.basic.ModuleCasing;
import de.nedelosk.forestmods.common.modules.storage.ModuleCapacitor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginThermalExpansion extends APlugin {

	public static Item cell;
	public static Item frame;
	public static Item tank;
	public static Item strongBox;
	public static Item capacitor;

	@Override
	public void postInit() {
		cell = GameRegistry.findItem(getRequiredMod(), "Cell");
		frame = GameRegistry.findItem(getRequiredMod(), "Frame");
		tank = GameRegistry.findItem(getRequiredMod(), "Tank");
		strongBox = GameRegistry.findItem(getRequiredMod(), "Strongbox");
		capacitor = GameRegistry.findItem(getRequiredMod(), "capacitor");
		addModuleToItem(new ItemStack(frame, 1, 0), new ModuleCasing("TE_Iron"), Materials.IRON);
		addModuleToItem(new ItemStack(frame, 1, 1), new ModuleCasing("TE_Electrum"), Materials.Electrum);
		addModuleToItem(new ItemStack(frame, 1, 2), new ModuleCasing("TE_Signalum"), Materials.Signalum);
		addModuleToItem(new ItemStack(frame, 1, 3), new ModuleCasing("TE_Enderium"), Materials.Enderium);
		addModuleToItem(new ItemStack(tank, 1, 1), new ModulePortableTank("TankIron", 8000), Materials.IRON);
		addModuleToItem(new ItemStack(tank, 1, 2), new ModulePortableTank("TankInvar", 32000), Materials.Invar);
		addModuleToItem(new ItemStack(tank, 1, 3), new ModulePortableTank("TankObsidian", 128000), Materials.OBSIDIAN);
		addModuleToItem(new ItemStack(tank, 1, 4), new ModulePortableTank("TankEnderium", 512000), Materials.Enderium);
		addModuleToItem(new ItemStack(cell, 1, 1), new ModuleEnergyCell("EnergyCellLeadstone", new EnergyStorage(100000, 100, 100)), Materials.Lead, true);
		addModuleToItem(new ItemStack(cell, 1, 2), new ModuleEnergyCell("EnergyCellHardened", new EnergyStorage(500000, 400, 400)), Materials.Invar, true);
		addModuleToItem(new ItemStack(cell, 1, 3), new ModuleEnergyCell("EnergyCellRedstone", new EnergyStorage(5000000, 4000, 4000)), Materials.Electrum,
				true);
		addModuleToItem(new ItemStack(cell, 1, 4), new ModuleEnergyCell("EnergyCellResonant", new EnergyStorage(20000000, 16000, 16000)), Materials.Enderium,
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
		addModuleToItem(new ItemStack(capacitor, 1, 1), new ModuleCapacitor("CapacitorWood", 7, 15), Materials.WOOD);
		addModuleToItem(new ItemStack(capacitor, 1, 2), new ModuleCapacitor("CapacitorLead", 10, 20), Materials.Lead);
		addModuleToItem(new ItemStack(capacitor, 1, 3), new ModuleCapacitor("CapacitorInvar", 15, 30), Materials.Invar);
		addModuleToItem(new ItemStack(capacitor, 1, 4), new ModuleCapacitor("CapacitorElectrum", 20, 40), Materials.Electrum);
		addModuleToItem(new ItemStack(capacitor, 1, 5), new ModuleCapacitor("CapacitorEnderium", 40, 80), Materials.Enderium);
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
