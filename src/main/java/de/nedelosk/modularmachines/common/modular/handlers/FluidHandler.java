package de.nedelosk.modularmachines.common.modular.handlers;

import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.modularmachines.api.modular.IModular;
import de.nedelosk.modularmachines.api.modules.handlers.IModuleContentHandler;
import de.nedelosk.modularmachines.api.modules.handlers.tank.IModuleTank;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import net.minecraft.util.EnumFacing;
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
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		for(IModuleTank tank : tanks) {
			int test = tank.fill(from, resource, false, false);
			if (test == resource.amount) {
				return tank.fill(from, resource, doFill, false);
			}
		}
		return 0;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
		for(IModuleTank tank : tanks) {
			FluidStack test = tank.drain(from, resource, false, false);
			if (test != null && test.amount == resource.amount && test.getFluid() == resource.getFluid()) {
				return tank.drain(from, resource, doDrain, false);
			}
		}
		return null;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		for(IModuleTank tank : tanks) {
			FluidStack test = tank.drain(from, maxDrain, false, false);
			if (test != null && test.amount == maxDrain) {
				return tank.drain(from, maxDrain, doDrain, false);
			}
		}
		return null;
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		for(IModuleTank tank : tanks) {
			if (tank.canFill(from, fluid)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
		for(IModuleTank tank : tanks) {
			if (tank.canDrain(from, fluid)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {
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
		for(IModuleState state : modular.getModuleStates()) {
			IModuleContentHandler handler = state.getContentHandler(FluidStack.class);
			if(handler instanceof IModuleTank){
				tanks.add((IModuleTank) handler);
			}
		}
		return tanks;
	}
}
