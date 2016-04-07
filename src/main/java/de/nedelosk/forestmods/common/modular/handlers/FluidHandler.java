package de.nedelosk.forestmods.common.modular.handlers;

import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class FluidHandler implements IFluidHandler {

	public IModular modular;
	protected final List<IModuleTank> tanks;

	public FluidHandler(IModular machine) {
		this.modular = machine;
		tanks = getTanks();
	}

	public void setMachine(IModular machine) {
		this.modular = machine;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		for(IModuleTank tank : tanks) {
			int test = tank.fill(from, resource, false, false);
			if (test == resource.amount) {
				return tank.fill(from, resource, doFill, false);
			}
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		for(IModuleTank tank : tanks) {
			FluidStack test = tank.drain(from, resource, false, false);
			if (test != null && test.amount == resource.amount && test.getFluid() == resource.getFluid()) {
				return tank.drain(from, resource, doDrain, false);
			}
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		for(IModuleTank tank : tanks) {
			FluidStack test = tank.drain(from, maxDrain, false, false);
			if (test != null && test.amount == maxDrain) {
				return tank.drain(from, maxDrain, doDrain, false);
			}
		}
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		for(IModuleTank tank : tanks) {
			if (tank.canFill(from, fluid)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		for(IModuleTank tank : tanks) {
			if (tank.canDrain(from, fluid)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		List<FluidTankInfo> infoList = Lists.newArrayList();
		for(IModuleTank tank : tanks) {
			FluidTankInfo[] infos = tank.getTankInfo(from);
			if (infos != null && infos.length > 0) {
				for(FluidTankInfo info : infos) {
					infoList.add(info);
				}
			}
		}
		return infoList.toArray(new FluidTankInfo[infoList.size()]);
	}

	protected List<IModuleTank> getTanks() {
		List<IModuleTank> tanks = Lists.newArrayList();
		for(ModuleStack stack : modular.getModuleStacks()) {
			tanks.add(stack.getModule().getTank());
		}
		return tanks;
	}
}
