package nedelosk.modularmachines.plugins;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.modularmachines.api.modular.module.Modules;
import nedelosk.modularmachines.api.modular.type.Types;
import nedelosk.modularmachines.api.modular.utils.ModuleRegistry;
import nedelosk.modularmachines.common.config.ModularConfig;
import nedelosk.modularmachines.common.modular.module.tool.producer.energy.ProducerBattery;
import nedelosk.modularmachines.common.modular.module.tool.producer.energy.ProducerCapacitor;
import nedelosk.modularmachines.common.modular.module.tool.producer.fluids.ProducerTank;
import nedelosk.nedeloskcore.plugins.basic.Plugin;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginEnderIO extends Plugin {
	
	public ItemStack chassis = GameRegistry.findItemStack(getRequiredMod(), "itemMachinePart", 1);
	public Item capacitorBank = GameRegistry.findItem(getRequiredMod(), "blockCapBank");
	public Item capacitor = GameRegistry.findItem(getRequiredMod(), "itemBasicCapacitor");
	public Item tanks = GameRegistry.findItem(getRequiredMod(), "blockTank");
	
	@Override
	public void preInit() {
		ModuleRegistry.addModuleItem(chassis, Modules.CASING, Types.IRON);
		ModuleRegistry.addModuleItem(new ItemStack(capacitorBank, 1, 1), Modules.BATTERY, new ProducerBattery("CapacitorBankBasic", new EnergyStorage(1000000, 1000, 1000)), Types.IRON);
		ModuleRegistry.addModuleItem(new ItemStack(capacitorBank, 1, 2), Modules.BATTERY, new ProducerBattery("CapacitorBank", new EnergyStorage(5000000, 5000, 5000)), Types.BRONZE);
		ModuleRegistry.addModuleItem(new ItemStack(capacitorBank, 1, 3), Modules.BATTERY, new ProducerBattery("CapacitorBankVibrant", new EnergyStorage(25000000, 25000, 25000)), Types.STEEL);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 0), Modules.CAPACITOR, new ProducerCapacitor("CapacitorBasic", 10, 20), Types.IRON);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 1), Modules.CAPACITOR, new ProducerCapacitor("CapacitorDoubleLayer", 20, 40), Types.BRONZE);
		ModuleRegistry.addModuleItem(new ItemStack(capacitor, 1, 2), Modules.CAPACITOR, new ProducerCapacitor("CapacitorVibrant", 40, 60), Types.STEEL);
		ModuleRegistry.addModuleItem(new ItemStack(tanks), Modules.TANK, new ProducerTank("TankFluid", 16000), Types.IRON);
		ModuleRegistry.addModuleItem(new ItemStack(tanks, 1, 1), Modules.TANK, new ProducerTank("TankFluidPressurized", 32000), Types.BRONZE);
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
