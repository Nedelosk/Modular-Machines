package modularmachines.common.modules.turbines;

import modularmachines.common.core.managers.FluidManager;
import modularmachines.common.modules.pages.MainPage;

public class PageSteamTurbine extends MainPage<IModuleTurbine> {

	public PageSteamTurbine(IModuleState<IModuleTurbine> module) {
		super("turbine.steam", module);
	}

	@Override
	public void createInventory(IModuleInventoryBuilder invBuilder) {
		invBuilder.addInventorySlot(true, 15, 28, "liquid", ItemFilterFluid.get(FluidManager.STEAM));
		invBuilder.addInventorySlot(false, 15, 48, "container", OutputFilter.INSTANCE);
	}

	@Override
	public void createTank(IModuleTankBuilder tankBuilder) {
		tankBuilder.addFluidTank(16000, true, 80, 18, FluidFilter.get(FluidManager.STEAM));
	}
}
