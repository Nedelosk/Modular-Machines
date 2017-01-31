package modularmachines.common.modules.machine.boiler;

import modularmachines.common.modules.pages.MainPage;
import net.minecraftforge.fluids.FluidRegistry;

public class BoilerPage extends MainPage<IModuleTool> {

	public BoilerPage(IModuleState<IModuleTool> moduleState) {
		super("boiler", moduleState);
	}

	@Override
	public void createInventory(IModuleInventoryBuilder invBuilder) {
		invBuilder.addInventorySlot(true, 15, 28, "liquid", ItemFilterFluid.get(FluidRegistry.WATER));
		invBuilder.addInventorySlot(false, 15, 48, "container", OutputFilter.INSTANCE);
		invBuilder.addInventorySlot(true, 147, 28, "container", ItemFilterFluid.INSTANCE);
		invBuilder.addInventorySlot(false, 147, 48, "liquid", OutputFilter.INSTANCE);
	}

	@Override
	public void createTank(IModuleTankBuilder tankBuilder) {
		tankBuilder.addFluidTank(16000, true, 55, 15, FluidFilter.get(FluidRegistry.WATER));
		tankBuilder.addFluidTank(16000, false, 105, 15, OutputFilter.INSTANCE);
	}
}
