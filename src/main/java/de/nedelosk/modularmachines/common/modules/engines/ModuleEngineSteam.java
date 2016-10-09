package de.nedelosk.modularmachines.common.modules.engines;

import java.util.List;

import de.nedelosk.modularmachines.api.modules.IModulePage;
import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventory;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.core.FluidManager;
import de.nedelosk.modularmachines.common.modules.pages.SteamEnginePage;
import de.nedelosk.modularmachines.common.utils.ModuleUtil;
import net.minecraftforge.fluids.FluidStack;

public class ModuleEngineSteam extends ModuleEngine {

	public ModuleEngineSteam() {
		super("steam");
	}

	@Override
	public boolean removeMaterial(IModuleState state) {
		IModuleTank tank = state.getPage(SteamEnginePage.class).getTank();
		if(tank == null){
			return false;
		}
		FluidStack drained = tank.drainInternal(new FluidStack(FluidManager.STEAM, getMaterialPerWork(state)), false);
		if (drained != null && drained.amount >= getMaterialPerWork(state)) {
			return tank.drainInternal(new FluidStack(FluidManager.STEAM, getMaterialPerWork(state)), true).amount >= getMaterialPerWork(state);
		} else {
			return false;
		}
	}

	@Override
	public void updateServer(IModuleState state, int tickCount) {
		super.updateServer(state, tickCount);

		if(state.getModular().updateOnInterval(20)){
			IModulePage page = state.getPage(SteamEnginePage.class);
			IModuleInventory inventory = page.getInventory();
			IModuleTank tank = page.getTank();

			ModuleUtil.tryEmptyContainer(0, 1, inventory, tank.getTank(0));
		}
	}

	@Override
	public boolean canWork(IModuleState state) {
		IModuleTank tank = state.getPage(SteamEnginePage.class).getTank();
		if(tank == null){
			return false;
		}
		if (tank.getTank(0).getFluid() == null) {
			return false;
		} 
		if (tank.getTank(0).getFluid().amount > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<IModulePage> createPages(IModuleState state) {
		List<IModulePage> pages = super.createPages(state);
		pages.add(new SteamEnginePage(state));
		return pages;
	}
}