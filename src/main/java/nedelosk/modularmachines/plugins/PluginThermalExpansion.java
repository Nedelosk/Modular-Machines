package nedelosk.modularmachines.plugins;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.modularmachines.common.modular.module.basic.ModuleCasing;
import nedelosk.modularmachines.common.modular.module.tool.producer.energy.ProducerBattery;
import nedelosk.modularmachines.common.modular.module.tool.producer.energy.ProducerCapacitor;
import nedelosk.modularmachines.common.modular.module.tool.producer.fluids.ProducerTank;
import nedelosk.modularmachines.common.modular.module.tool.producer.storage.ProducerChest;
import nedelosk.nedeloskcore.plugins.basic.Plugin;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginThermalExpansion extends Plugin {

	public Item cell = GameRegistry.findItem(getRequiredMod(), "Cell");
	public Item frame = GameRegistry.findItem(getRequiredMod(), "Frame");
	public Item tank = GameRegistry.findItem(getRequiredMod(), "Tank");
	public Item strongBox = GameRegistry.findItem(getRequiredMod(), "Strongbox");
	public Item capacitor = GameRegistry.findItem(getRequiredMod(), "capacitor");
	
	@Override
	public void preInit() {
		ModuleRegistry.addModuleItem(new ItemStack(frame, 1, 0), new ModuleCasing(), 1);
		ModuleRegistry.addModuleItem(new ItemStack(frame, 1, 1), new ModuleCasing(), 2);
		ModuleRegistry.addModuleItem(new ItemStack(frame, 1, 2), new ModuleCasing(), 2);
		ModuleRegistry.addModuleItem(new ItemStack(frame, 1, 3), new ModuleCasing(), 3);
		ModuleRegistry.addModuleItem(new ItemStack(tank, 1, 1), new ProducerTank(8000), 1);
		ModuleRegistry.addModuleItem(new ItemStack(tank, 1, 2), new ProducerTank(32000), 2);
		ModuleRegistry.addModuleItem(new ItemStack(tank, 1, 3), new ProducerTank(128000), 2);
		ModuleRegistry.addModuleItem(new ItemStack(tank, 1, 4), new ProducerTank(512000), 3);
		ModuleRegistry.addModuleItem(new ItemStack(cell, 1, 1), new ProducerBattery("energyCellLeadstone", 400000, 200, 200), 1);
		ModuleRegistry.addModuleItem(new ItemStack(cell, 1, 2), new ProducerBattery("energyCellHardened", 20000000, 800, 800), 2);
		ModuleRegistry.addModuleItem(new ItemStack(cell, 1, 3), new ProducerBattery("energyCellRedstone", 200000000, 8000, 8000), 2);
		ModuleRegistry.addModuleItem(new ItemStack(cell, 1, 4), new ProducerBattery("energyCellResonant", 800000000, 32000, 32000), 3);
		ModuleRegistry.addModuleItem(new ItemStack(strongBox, 1, 1), new ProducerChest("strongBox",  18), 1);
		ModuleRegistry.addModuleItem(new ItemStack(strongBox, 1, 2), new ProducerChest("strongBoxHardende",  36), 2);
		ModuleRegistry.addModuleItem(new ItemStack(strongBox, 1, 3), new ProducerChest("strongBoxReinforced",  54), 2);
		ModuleRegistry.addModuleItem(new ItemStack(strongBox, 1, 4), new ProducerChest("strongBoxHResonant",  72), 3);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 1), new ProducerCapacitor(7, 15), 1);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 2), new ProducerCapacitor(10, 20), 1);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 3), new ProducerCapacitor(15, 30), 2);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 4), new ProducerCapacitor(20, 40), 2);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 5), new ProducerCapacitor(40, 80), 3);
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
