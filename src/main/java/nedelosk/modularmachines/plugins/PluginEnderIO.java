package nedelosk.modularmachines.plugins;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.basic.machine.ModularManager;
import nedelosk.modularmachines.common.machines.module.ModuleCasing;
import nedelosk.modularmachines.common.machines.module.ModuleTank;
import nedelosk.modularmachines.common.machines.module.energy.ModuleBattery;
import nedelosk.modularmachines.common.machines.module.energy.ModuleCapacitor;
import nedelosk.nedeloskcore.plugins.basic.Plugin;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginEnderIO extends Plugin {
	
	public ItemStack casing = GameRegistry.findItemStack(getRequiredMod(), "itemMachinePart", 1);
	public Item capacitorBank = GameRegistry.findItem(getRequiredMod(), "blockCapBank");
	public Item capacitor = GameRegistry.findItem(getRequiredMod(), "itemBasicCapacitor");
	public Item tanks = GameRegistry.findItem(getRequiredMod(), "blockTank");
	
	@Override
	public void preInit() {
		ModularManager.addModuleItem(casing, new ModuleCasing(), 1);
		ModularManager.addModuleItem(new ItemStack(capacitorBank, 1, 1), new ModuleBattery("capacitorBasic", 1000000), 1);
		ModularManager.addModuleItem(new ItemStack(capacitorBank, 1, 2), new ModuleBattery("capacitor", 5000000), 2);
		ModularManager.addModuleItem(new ItemStack(capacitorBank, 1, 3), new ModuleBattery("capacitorVibrant", 25000000), 3);
		ModularManager.addModuleItem(new ItemStack(capacitor, 1, 0), new ModuleCapacitor(10, 20), 1);
		ModularManager.addModuleItem(new ItemStack(capacitor, 1, 1), new ModuleCapacitor(20, 40), 2);
		ModularManager.addModuleItem(new ItemStack(capacitor, 1, 2), new ModuleCapacitor(40, 60), 3);
		ModularManager.addModuleItem(new ItemStack(tanks), new ModuleTank(16000), 1);
		ModularManager.addModuleItem(new ItemStack(tanks, 1, 1), new ModuleTank(32000), 2);
		ModularManager.addBookmarkItem("Energy", new ItemStack(capacitorBank, 1, 1));
		ModularManager.addBookmarkItem("Energy", new ItemStack(capacitorBank, 1, 2));
		ModularManager.addBookmarkItem("Energy", new ItemStack(capacitorBank, 1, 3));
		ModularManager.addBookmarkItem("Basic", casing);
		ModularManager.addBookmarkItem("Fluid", new ItemStack(tanks));
		ModularManager.addBookmarkItem("Fluid", new ItemStack(tanks, 1, 1));
	}
	
	@Override
	public String getRequiredMod() {
		return "EnderIO";
	}
	
}
