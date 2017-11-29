package modularmachines.common.modules.components;

import modularmachines.common.core.Fluids;
import modularmachines.common.modules.components.process.ProcessComponent;
import modularmachines.common.modules.components.process.criteria.ProcessLogic;

public class SteamConsumerComponent extends ProcessComponent {
	public static int processLength = 2;
	public static int energyPerSteam = 15;
	
	@Override
	protected void addCriteria(ProcessLogic logic) {
		logic.addFluidDrain(Fluids.STEAM.get(10));
		logic.addInjectEnergy(energyPerSteam);
	}
	
	@Override
	public int getProcessLength() {
		return processLength;
	}
}
