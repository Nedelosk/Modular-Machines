package nedelosk.forestday.common.multiblocks;

import cofh.api.energy.IEnergyHandler;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileMultiblockValve extends TileMultiblockBase implements IFluidHandler, IEnergyHandler {

	public TileMultiblockValve() {
		super();
	}
	
	public TileMultiblockValve(int slots) {
		super(slots);
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(master != null && master.isMultiblock() && master.getMultiblock() != null && master.getMultiblock() instanceof AbstractMultiblockFluid)
			return ((AbstractMultiblockFluid)master.getMultiblock()).fill(this, from, resource, doFill);
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if(master != null && master.isMultiblock() && master.getMultiblock() != null && master.getMultiblock() instanceof AbstractMultiblockFluid)
			return ((AbstractMultiblockFluid)master.getMultiblock()).drain(this, from, resource, doDrain);
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if(master != null && master.isMultiblock() && master.getMultiblock() != null && master.getMultiblock() instanceof AbstractMultiblockFluid)
			return ((AbstractMultiblockFluid)master.getMultiblock()).drain(this, from, maxDrain, doDrain);
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if(master != null && master.isMultiblock() && master.getMultiblock() != null && master.getMultiblock() instanceof AbstractMultiblockFluid)
			return ((AbstractMultiblockFluid)master.getMultiblock()).canFill(this, from, fluid);
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if(master != null && master.isMultiblock() && master.getMultiblock() != null && master.getMultiblock() instanceof AbstractMultiblockFluid)
			return ((AbstractMultiblockFluid)master.getMultiblock()).canDrain(this, from, fluid);
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if(master != null && master.isMultiblock() && master.getMultiblock() != null && master.getMultiblock() instanceof AbstractMultiblockFluid)
			return ((AbstractMultiblockFluid)master.getMultiblock()).getTankInfo(this, from);
		return null;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		if(from == ForgeDirection.EAST)
			return true;
		return false;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return 0;
	}
	
}
