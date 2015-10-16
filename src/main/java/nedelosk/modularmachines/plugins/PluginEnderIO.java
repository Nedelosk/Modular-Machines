package nedelosk.modularmachines.plugins;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.modularmachines.common.modular.module.basic.ModuleCasing;
import nedelosk.modularmachines.common.modular.module.tool.producer.energy.ProducerBattery;
import nedelosk.modularmachines.common.modular.module.tool.producer.energy.ProducerCapacitor;
import nedelosk.modularmachines.common.modular.module.tool.producer.fluids.ProducerTank;
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
		ModuleRegistry.addModuleItem(casing, new ModuleCasing("chassis"), 1);
		ModuleRegistry.addModuleItem(new ItemStack(capacitorBank, 1, 1), new ProducerBattery("capacitorBasic", 1000000, 1000, 1000), 1);
		ModuleRegistry.addModuleItem(new ItemStack(capacitorBank, 1, 2), new ProducerBattery("capacitor", 5000000, 5000, 5000), 2);
		ModuleRegistry.addModuleItem(new ItemStack(capacitorBank, 1, 3), new ProducerBattery("capacitorVibrant", 25000000, 25000, 25000), 3);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 0), new ProducerCapacitor(10, 20), 1);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 1), new ProducerCapacitor(20, 40), 2);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 2), new ProducerCapacitor(40, 60), 3);
		ModuleRegistry.addModuleItem(new ItemStack(tanks), new ProducerTank(16000), 1);
		ModuleRegistry.addModuleItem(new ItemStack(tanks, 1, 1), new ProducerTank(32000), 2);
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
