package de.nedelosk.modularmachines.common.modules.pages;

import de.nedelosk.modularmachines.api.modules.IModuleTurbine;
import de.nedelosk.modularmachines.api.modules.handlers.filters.FluidFilter;
import de.nedelosk.modularmachines.api.modules.handlers.filters.ItemFilterFluid;
import de.nedelosk.modularmachines.api.modules.handlers.filters.OutputFilter;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.core.FluidManager;

public class SteamTurbinePage extends MainPage<IModuleTurbine> {

	public SteamTurbinePage(IModuleState<IModuleTurbine> module) {
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
