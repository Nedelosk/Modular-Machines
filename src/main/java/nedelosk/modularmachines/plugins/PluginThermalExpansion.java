package nedelosk.modularmachines.plugins;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.modularmachines.api.modular.module.utils.ModularManager;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.modularmachines.common.machines.module.ModuleCasing;
import nedelosk.modularmachines.common.machines.module.ModuleTank;
import nedelosk.modularmachines.common.machines.module.energy.ModuleBattery;
import nedelosk.modularmachines.common.machines.module.energy.ModuleCapacitor;
import nedelosk.modularmachines.common.machines.module.storage.ModuleChest;
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
		ModularManager.addModuleStack(new ItemStack(frame, 1, 0), new ModuleCasing(), 1);
		ModularManager.addModuleStack(new ItemStack(frame, 1, 1), new ModuleCasing(), 2);
		ModularManager.addModuleStack(new ItemStack(frame, 1, 2), new ModuleCasing(), 2);
		ModularManager.addModuleStack(new ItemStack(frame, 1, 3), new ModuleCasing(), 3);
		ModularManager.addModuleStack(new ItemStack(tank, 1, 1), new ModuleTank(8000), 1);
		ModularManager.addModuleStack(new ItemStack(tank, 1, 2), new ModuleTank(32000), 2);
		ModularManager.addModuleStack(new ItemStack(tank, 1, 3), new ModuleTank(128000), 2);
		ModularManager.addModuleStack(new ItemStack(tank, 1, 4), new ModuleTank(512000), 3);
		ModularManager.addModuleStack(new ItemStack(cell, 1, 1), new ModuleBattery("energyCellLeadstone", 400000, 200, 200), 1);
		ModularManager.addModuleStack(new ItemStack(cell, 1, 2), new ModuleBattery("energyCellHardened", 20000000, 800, 800), 2);
		ModularManager.addModuleStack(new ItemStack(cell, 1, 3), new ModuleBattery("energyCellRedstone", 200000000, 8000, 8000), 2);
		ModularManager.addModuleStack(new ItemStack(cell, 1, 4), new ModuleBattery("energyCellResonant", 800000000, 32000, 32000), 3);
		ModularManager.addModuleStack(new ItemStack(strongBox, 1, 1), new ModuleChest("strongBox",  18), 1);
		ModularManager.addModuleStack(new ItemStack(strongBox, 1, 2), new ModuleChest("strongBoxHardende",  36), 2);
		ModularManager.addModuleStack(new ItemStack(strongBox, 1, 3), new ModuleChest("strongBoxReinforced",  54), 2);
		ModularManager.addModuleStack(new ItemStack(strongBox, 1, 4), new ModuleChest("strongBoxHResonant",  72), 3);
		ModularManager.addModuleStack(new ItemStack(capacitor, 1, 1), new ModuleCapacitor(7, 15), 1);
		ModularManager.addModuleStack(new ItemStack(capacitor, 1, 2), new ModuleCapacitor(10, 20), 1);
		ModularManager.addModuleStack(new ItemStack(capacitor, 1, 3), new ModuleCapacitor(15, 30), 2);
		ModularManager.addModuleStack(new ItemStack(capacitor, 1, 4), new ModuleCapacitor(20, 40), 2);
		ModularManager.addModuleStack(new ItemStack(capacitor, 1, 5), new ModuleCapacitor(40, 80), 3);
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
