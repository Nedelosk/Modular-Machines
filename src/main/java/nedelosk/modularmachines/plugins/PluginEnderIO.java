package nedelosk.modularmachines.plugins;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.common.modular.module.ModuleCasing;
import nedelosk.modularmachines.common.modular.module.ModuleTank;
import nedelosk.modularmachines.common.modular.module.energy.ModuleBattery;
import nedelosk.modularmachines.common.modular.module.energy.ModuleCapacitor;
import nedelosk.nedeloskcore.plugins.Plugin;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginEnderIO extends Plugin {
	
	public ItemStack casing = GameRegistry.findItemStack(getRequiredMod(), "itemMachinePart", 1);
	public Item capacitorBank = GameRegistry.findItem(getRequiredMod(), "blockCapBank");
	public Item capacitor = GameRegistry.findItem(getRequiredMod(), "itemBasicCapacitor");
	public Item tanks = GameRegistry.findItem(getRequiredMod(), "blockTank");
	
	@Override
	public void preInit() {
		ModularMachinesApi.addModuleItem(casing, new ModuleCasing(), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(capacitorBank, 1, 1), new ModuleBattery("capacitorBasic", 1000000), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(capacitorBank, 1, 2), new ModuleBattery("capacitor", 5000000), 2);
		ModularMachinesApi.addModuleItem(new ItemStack(capacitorBank, 1, 3), new ModuleBattery("capacitorVibrant", 25000000), 3);
		ModularMachinesApi.addModuleItem(new ItemStack(capacitor, 1, 0), new ModuleCapacitor(10, 20), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(capacitor, 1, 1), new ModuleCapacitor(20, 40), 2);
		ModularMachinesApi.addModuleItem(new ItemStack(capacitor, 1, 2), new ModuleCapacitor(40, 80), 3);
		ModularMachinesApi.addModuleItem(new ItemStack(tanks), new ModuleTank(16000), 1);
		ModularMachinesApi.addModuleItem(new ItemStack(tanks, 1, 1), new ModuleTank(32000), 2);
		ModularMachinesApi.addBookmarkItem("Energy", new ItemStack(capacitorBank, 1, 1));
		ModularMachinesApi.addBookmarkItem("Energy", new ItemStack(capacitorBank, 1, 2));
		ModularMachinesApi.addBookmarkItem("Energy", new ItemStack(capacitorBank, 1, 3));
		ModularMachinesApi.addBookmarkItem("Basic", casing);
		ModularMachinesApi.addBookmarkItem("Fluid", new ItemStack(tanks));
		ModularMachinesApi.addBookmarkItem("Fluid", new ItemStack(tanks, 1, 1));
	}
	
	@Override
	public String getRequiredMod() {
		return "EnderIO";
	}
	
}
