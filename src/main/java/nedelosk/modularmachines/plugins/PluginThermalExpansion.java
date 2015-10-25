package nedelosk.modularmachines.plugins;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestday.plugins.basic.Plugin;
import nedelosk.modularmachines.api.modular.module.Modules;
import nedelosk.modularmachines.api.modular.module.basic.IModule;
import nedelosk.modularmachines.api.modular.module.basic.basic.ModuleBasic;
import nedelosk.modularmachines.api.modular.type.Types;
import nedelosk.modularmachines.api.modular.type.Types.Type;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.modularmachines.common.modular.module.tool.producer.energy.ProducerBattery;
import nedelosk.modularmachines.common.modular.module.tool.producer.energy.ProducerCapacitor;
import nedelosk.modularmachines.common.modular.module.tool.producer.fluids.ProducerTank;
import nedelosk.modularmachines.common.modular.module.tool.producer.storage.ProducerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginThermalExpansion extends Plugin {

	public static IModule STRONGBOX = new ModuleBasic("Storage", "Storage");
	
	public static Type Lead = Types.addType(2, "Lead", "lead");
	public static Type Invar = Types.addType(3, "Invar", "invar");
	public static Type Electrum = Types.addType(5, "Electrum", "electrum");
	public static Type Signalum = Types.addType(6, "Signalum", "signalum");
	public static Type Enderium = Types.addType(7, "Enderium", "enderium");
	
	public Item cell;
	public Item frame;
	public Item tank;
	public Item strongBox;
	public Item capacitor;
	
	@Override
	public void init() {
		cell = GameRegistry.findItem(getRequiredMod(), "Cell");
		frame = GameRegistry.findItem(getRequiredMod(), "Frame");
		tank = GameRegistry.findItem(getRequiredMod(), "Tank");
		strongBox = GameRegistry.findItem(getRequiredMod(), "Strongbox");
		capacitor = GameRegistry.findItem(getRequiredMod(), "Capacitor");
		ModuleRegistry.addModuleItem(new ItemStack(frame, 1, 0), Modules.CASING, Types.IRON);
		ModuleRegistry.addModuleItem(new ItemStack(frame, 1, 1), Modules.CASING, Electrum);
		ModuleRegistry.addModuleItem(new ItemStack(frame, 1, 2), Modules.CASING, Signalum);
		ModuleRegistry.addModuleItem(new ItemStack(frame, 1, 3), Modules.CASING, Enderium);
		ModuleRegistry.addModuleItem(new ItemStack(tank, 1, 1), Modules.TANK, new ProducerTank("TankIron", 8000), Types.IRON);
		ModuleRegistry.addModuleItem(new ItemStack(tank, 1, 2), Modules.TANK, new ProducerTank("TankInvar", 32000), Invar);
		ModuleRegistry.addModuleItem(new ItemStack(tank, 1, 3), Modules.TANK, new ProducerTank("TankObsidian", 128000), Types.OBSIDIAN);
		ModuleRegistry.addModuleItem(new ItemStack(tank, 1, 4), Modules.TANK, new ProducerTank("TankEnderium", 512000), Enderium);
		ModuleRegistry.addModuleItem(new ItemStack(cell, 1, 1), Modules.BATTERY, new ProducerBattery("EnergyCellLeadstone", new EnergyStorage(400000, 200, 200)), Lead);
		ModuleRegistry.addModuleItem(new ItemStack(cell, 1, 2), Modules.BATTERY, new ProducerBattery("EnergyCellHardened", new EnergyStorage(400000, 200, 200)), Invar);
		ModuleRegistry.addModuleItem(new ItemStack(cell, 1, 3), Modules.BATTERY, new ProducerBattery("EnergyCellRedstone", new EnergyStorage(400000, 200, 200)), Electrum);
		ModuleRegistry.addModuleItem(new ItemStack(cell, 1, 4), Modules.BATTERY, new ProducerBattery("EnergyCellResonant", new EnergyStorage(400000, 200, 200)), Enderium);
		ModuleRegistry.addModuleItem(new ItemStack(strongBox, 1, 1), STRONGBOX, new ProducerChest("StrongBox", 18), Types.IRON);
		ModuleRegistry.addModuleItem(new ItemStack(strongBox, 1, 2), STRONGBOX, new ProducerChest("StrongBoxHardende", 36), Invar);
		ModuleRegistry.addModuleItem(new ItemStack(strongBox, 1, 3), STRONGBOX, new ProducerChest("StrongBoxReinforced", 54), Types.OBSIDIAN);
		ModuleRegistry.addModuleItem(new ItemStack(strongBox, 1, 4), STRONGBOX, new ProducerChest("StrongBoxResonant", 72), Enderium);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 1), Modules.CAPACITOR, new ProducerCapacitor("CapacitorWood", 7, 15), Types.WOOD);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 2), Modules.CAPACITOR, new ProducerCapacitor("CapacitorLead", 10, 20), Lead);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 3), Modules.CAPACITOR, new ProducerCapacitor("CapacitorInvar", 15, 30), Invar);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 4), Modules.CAPACITOR, new ProducerCapacitor("CapacitorElectrum", 20, 40), Electrum);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 5), Modules.CAPACITOR, new ProducerCapacitor("CapacitorEnderium", 40, 80), Enderium);
	}
	
	@Override
	public void postInit() {
		ModuleRegistry.registerModule(STRONGBOX);
	}
	
	@Override
	public String getRequiredMod() {
		return "ThermalExpansion";
	}
	
	@Override
	public boolean getConfigOption() {
		return ModularConfig.pluginThermalExpansion;
	}
	
}
