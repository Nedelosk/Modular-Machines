package nedelosk.modularmachines.plugins.thermalexpansion;

import static nedelosk.modularmachines.api.utils.ModuleRegistry.addModuleToItem;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestcore.library.plugins.APlugin;
import nedelosk.modularmachines.api.modular.material.Materials;
import nedelosk.modularmachines.api.modules.basic.ModuleCasing;
import nedelosk.modularmachines.api.modules.energy.ModuleBattery;
import nedelosk.modularmachines.api.modules.storage.capacitors.ModuleCapacitor;
import nedelosk.modularmachines.common.config.Config;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginThermalExpansion extends APlugin {

	public static Item cell;
	public static Item frame;
	public static Item tank;
	public static Item strongBox;
	public static Item capacitor;

	@Override
	public void init() {
		cell = GameRegistry.findItem(getRequiredMod(), "Cell");
		frame = GameRegistry.findItem(getRequiredMod(), "Frame");
		tank = GameRegistry.findItem(getRequiredMod(), "Tank");
		strongBox = GameRegistry.findItem(getRequiredMod(), "Strongbox");
		capacitor = GameRegistry.findItem(getRequiredMod(), "capacitor");
		addModuleToItem(new ItemStack(frame, 1, 0), new ModuleCasing<>("TE_Iron"), Materials.IRON);
		addModuleToItem(new ItemStack(frame, 1, 1), new ModuleCasing<>("TE_Electrum"), Materials.Electrum);
		addModuleToItem(new ItemStack(frame, 1, 2), new ModuleCasing<>("TE_Signalum"), Materials.Signalum);
		addModuleToItem(new ItemStack(frame, 1, 3), new ModuleCasing<>("TE_Enderium"), Materials.Enderium);
		addModuleToItem(new ItemStack(tank, 1, 1), new ModuleTankThermalExpansion("TankIron", 8000), Materials.IRON);
		addModuleToItem(new ItemStack(tank, 1, 2), new ModuleTankThermalExpansion("TankInvar", 32000), Materials.Invar);
		addModuleToItem(new ItemStack(tank, 1, 3), new ModuleTankThermalExpansion("TankObsidian", 128000), Materials.OBSIDIAN);
		addModuleToItem(new ItemStack(tank, 1, 4), new ModuleTankThermalExpansion("TankEnderium", 512000), Materials.Enderium);
		addModuleToItem(new ItemStack(cell, 1, 1), new ModuleBattery("EnergyCellLeadstone", new EnergyStorage(100000, 100, 100)), Materials.Lead);
		addModuleToItem(new ItemStack(cell, 1, 2), new ModuleBattery("EnergyCellHardened", new EnergyStorage(500000, 400, 400)), Materials.Invar);
		addModuleToItem(new ItemStack(cell, 1, 3), new ModuleBattery("EnergyCellRedstone", new EnergyStorage(5000000, 4000, 4000)), Materials.Electrum);
		addModuleToItem(new ItemStack(cell, 1, 4), new ModuleBattery("EnergyCellResonant", new EnergyStorage(20000000, 16000, 16000)), Materials.Enderium);
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
