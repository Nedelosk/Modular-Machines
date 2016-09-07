package de.nedelosk.modularmachines.common.modules.pages;

import de.nedelosk.modularmachines.api.modules.handlers.inventory.IModuleInventoryBuilder;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTankBuilder;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tools.IModuleTool;
import de.nedelosk.modularmachines.common.modules.handlers.FluidFilter;
import de.nedelosk.modularmachines.common.modules.handlers.ItemFluidFilter;
import de.nedelosk.modularmachines.common.modules.handlers.OutputAllFilter;
import net.minecraftforge.fluids.FluidRegistry;

public class BoilerPage extends MainPage<IModuleTool> {

	public BoilerPage(IModuleState<IModuleTool> moduleState) {
		super("boiler", moduleState);
	}

	@Override
	public void createInventory(IModuleInventoryBuilder invBuilder) {
		invBuilder.addInventorySlot(true, 15, 28, "liquid", new ItemFluidFilter(true));
		invBuilder.addInventorySlot(false, 15, 48, "container", new OutputAllFilter());

		invBuilder.addInventorySlot(true, 147, 28, "container", new ItemFluidFilter(false));
		invBuilder.addInventorySlot(false, 147, 48, "liquid", new OutputAllFilter());
	}

	@Override
	public void createTank(IModuleTankBuilder tankBuilder) {
		tankBuilder.addFluidTank(16000, true, 55, 15, new FluidFilter(FluidRegistry.WATER));
		tankBuilder.addFluidTank(16000, false, 105, 15, new OutputAllFilter());
	}
}
