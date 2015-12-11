package nedelosk.modularmachines.api.modular.handlers;

import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.producers.managers.fluids.IProducerTankManager;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class FluidHandler implements IFluidHandler {

	public IModular machine;

	public FluidHandler(IModular machine) {
		this.machine = machine;
	}

	public void setMachine(IModular machine) {
		this.machine = machine;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return machine.getTankManeger().getProducer().fill(from, resource, doFill, null, machine);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return machine.getTankManeger().getProducer().drain(from, resource, doDrain, null, machine);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return machine.getTankManeger().getProducer().drain(from, maxDrain, doDrain, null, machine);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return machine.getTankManeger().getProducer().canFill(from, fluid, null, machine);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return machine.getTankManeger().getProducer().canDrain(from, fluid, null, machine);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return machine.getTankManeger().getProducer().getTankInfo(from, null, machine);
	}

}
