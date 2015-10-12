package nedelosk.modularmachines.plugins;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.modularmachines.common.modular.module.basic.basic.ModuleCasing;
import nedelosk.modularmachines.common.modular.module.basic.energy.ModuleBattery;
import nedelosk.modularmachines.common.modular.module.basic.energy.ModuleCapacitor;
import nedelosk.modularmachines.common.modular.module.basic.fluids.ModuleTank;
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
		ModuleRegistry.addModuleStack(casing, new ModuleCasing("chassis"), 1);
		ModuleRegistry.addModuleStack(new ItemStack(capacitorBank, 1, 1), new ModuleBattery("capacitorBasic", 1000000, 1000, 1000), 1);
		ModuleRegistry.addModuleStack(new ItemStack(capacitorBank, 1, 2), new ModuleBattery("capacitor", 5000000, 5000, 5000), 2);
		ModuleRegistry.addModuleStack(new ItemStack(capacitorBank, 1, 3), new ModuleBattery("capacitorVibrant", 25000000, 25000, 25000), 3);
		ModuleRegistry.addModuleStack(new ItemStack(capacitor, 1, 0), new ModuleCapacitor(10, 20), 1);
		ModuleRegistry.addModuleStack(new ItemStack(capacitor, 1, 1), new ModuleCapacitor(20, 40), 2);
		ModuleRegistry.addModuleStack(new ItemStack(capacitor, 1, 2), new ModuleCapacitor(40, 60), 3);
		ModuleRegistry.addModuleStack(new ItemStack(tanks), new ModuleTank(16000), 1);
		ModuleRegistry.addModuleStack(new ItemStack(tanks, 1, 1), new ModuleTank(32000), 2);
	}
	
	@Override
	public String getRequiredMod() {
		return "EnderIO";
	}
	
	@Override
	public boolean getConfigOption() {
		return ModularConfig.pluginEnderIO;
	}
	
}
