package modularmachines.common.energy.tesla;

import net.minecraftforge.fml.common.Optional;

import modularmachines.common.core.Constants;
import modularmachines.common.modules.container.components.EnergyManager;

import net.darkhax.tesla.api.ITeslaProducer;

@Optional.Interface(iface = "net.darkhax.tesla.api.ITeslaProducer", modid = Constants.TESLA_MOD_ID)
public class TeslaProducerWrapper implements ITeslaProducer {
	private final EnergyManager energyManager;
	
	public TeslaProducerWrapper(EnergyManager energyManager) {
		this.energyManager = energyManager;
	}
	
	@Optional.Method(modid = Constants.TESLA_MOD_ID)
	@Override
	public long takePower(long power, boolean simulated) {
		int intPower = (int) Math.min(power, Integer.MAX_VALUE);
		return energyManager.extractEnergy(intPower, simulated);
	}
}