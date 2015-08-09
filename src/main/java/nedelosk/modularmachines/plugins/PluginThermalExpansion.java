package nedelosk.modularmachines.plugins;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.common.modular.module.ModuleTank;
import nedelosk.modularmachines.common.modular.module.energy.ModuleBattery;
import nedelosk.modularmachines.common.modular.module.energy.ModuleCapacitor;
import nedelosk.modularmachines.common.modular.module.storage.ModuleChest;
import nedelosk.nedeloskcore.plugins.Plugin;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginThermalExpansion extends Plugin {

	public Item cell = GameRegistry.findItem(getRequiredMod(), "Cell");
	public Item tank = GameRegistry.findItem(getRequiredMod(), "Tank");
	public Item strongBox = GameRegistry.findItem(getRequiredMod(), "Strongbox");
	public Item capacitor = GameRegistry.findItem(getRequiredMod(), "capacitor");
	
	@Override
	public void preInit() {
		ModularMachinesApi.addModuleItem(new ItemStack(tank, 1, 1), new ModuleTank(8000), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(tank, 1, 2), new ModuleTank(32000), 2);
		ModularMachinesApi.addModuleItem(new ItemStack(tank, 1, 3), new ModuleTank(128000), 2);
		ModularMachinesApi.addModuleItem(new ItemStack(tank, 1, 4), new ModuleTank(512000), 3);
		ModularMachinesApi.addModuleItem(new ItemStack(cell, 1, 1), new ModuleBattery("energyCellLeadstone", 400000), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(cell, 1, 2), new ModuleBattery("energyCellHardened", 20000000), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(cell, 1, 3), new ModuleBattery("energyCellRedstone", 200000000), 2);
		ModularMachinesApi.addModuleItem(new ItemStack(cell, 1, 4), new ModuleBattery("energyCellResonant", 800000000), 3);
		ModularMachinesApi.addModuleItem(new ItemStack(strongBox, 1, 1), new ModuleChest("strongBox",  18), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(strongBox, 1, 2), new ModuleChest("strongBoxHardende",  36), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(strongBox, 1, 3), new ModuleChest("strongBoxReinforced",  54), 2);
		ModularMachinesApi.addModuleItem(new ItemStack(strongBox, 1, 4), new ModuleChest("strongBoxHResonant",  72), 3);
		ModularMachinesApi.addModuleItem(new ItemStack(capacitor, 1, 1), new ModuleCapacitor(10, 20), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(capacitor, 1, 2), new ModuleCapacitor(15, 30), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(capacitor, 1, 3), new ModuleCapacitor(20, 40), 2);
		ModularMachinesApi.addModuleItem(new ItemStack(capacitor, 1, 4), new ModuleCapacitor(40, 80), 3);
		}
	
	@Override
	public String getRequiredMod() {
		return "ThermalExpansion";
	}
	
}
