package nedelosk.modularmachines.plugins.enderio;

import static nedelosk.modularmachines.api.utils.ModuleRegistry.registerProducer;

import cofh.api.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestcore.library.plugins.APlugin;
import nedelosk.modularmachines.api.modular.type.Types;
import nedelosk.modularmachines.api.modules.Modules;
import nedelosk.modularmachines.api.producers.energy.ProducerBattery;
import nedelosk.modularmachines.api.producers.energy.ProducerCapacitor;
import nedelosk.modularmachines.common.config.Config;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginEnderIO extends APlugin {

	public ItemStack chassis = GameRegistry.findItemStack(getRequiredMod(), "itemMachinePart", 1);
	public Item capacitorBank = GameRegistry.findItem(getRequiredMod(), "blockCapBank");
	public Item capacitor = GameRegistry.findItem(getRequiredMod(), "itemBasicCapacitor");
	public Item tanks = GameRegistry.findItem(getRequiredMod(), "blockTank");

	@Override
	public void init() {
		registerProducer(chassis, Modules.CASING, Types.IRON);
		registerProducer(new ItemStack(capacitorBank, 1, 1), Modules.BATTERY, new ProducerBattery("CapacitorBankBasic", new EnergyStorage(250000, 1000, 500)),
				Types.IRON);
		registerProducer(new ItemStack(capacitorBank, 1, 2), Modules.BATTERY, new ProducerBattery("CapacitorBank", new EnergyStorage(1250000, 2500, 2500)),
				Types.BRONZE);
		registerProducer(new ItemStack(capacitorBank, 1, 3), Modules.BATTERY,
				new ProducerBattery("CapacitorBankVibrant", new EnergyStorage(6250000, 12500, 12500)), Types.STEEL);
		registerProducer(new ItemStack(capacitor, 1, 0), Modules.CAPACITOR, new ProducerCapacitor("CapacitorBasic", 10, 20), Types.IRON);
		registerProducer(new ItemStack(capacitor, 1, 1), Modules.CAPACITOR, new ProducerCapacitor("CapacitorDoubleLayer", 20, 40), Types.BRONZE);
		registerProducer(new ItemStack(capacitor, 1, 2), Modules.CAPACITOR, new ProducerCapacitor("CapacitorVibrant", 40, 60), Types.STEEL);
		registerProducer(new ItemStack(tanks), Modules.TANK, new ProducerTankEnderIO("TankFluid", 16000), Types.IRON);
		registerProducer(new ItemStack(tanks, 1, 1), Modules.TANK, new ProducerTankEnderIO("TankFluidPressurized", 32000), Types.BRONZE);
	}

	@Override
	public String getRequiredMod() {
		return "EnderIO";
	}

	@Override
	public boolean getConfigOption() {
		return Config.pluginEnderIO;
	}
}
