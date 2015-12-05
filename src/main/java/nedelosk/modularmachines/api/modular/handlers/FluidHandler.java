package nedelosk.modularmachines.api.modular.handlers;

import java.util.ArrayList;

import nedelosk.forestday.api.FluidTankBasic;
import nedelosk.forestday.api.INBTTagable;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.producers.fluids.IProducerTank;
import nedelosk.modularmachines.api.producers.fluids.ITankManager;
import nedelosk.modularmachines.api.producers.fluids.ITankManager.TankMode;
import nedelosk.modularmachines.api.producers.managers.fluids.IProducerTankManager;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

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
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return null;
	}

}
