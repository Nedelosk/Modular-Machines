package nedelosk.forestday.common.multiblocks;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public abstract class AbstractMultiblockFluid extends AbstractMultiblock {

	public AbstractMultiblockFluid() {
		super();
	}

	public abstract int fill(TileMultiblockBase tile, ForgeDirection from, FluidStack resource, boolean doFill);

	public abstract FluidStack drain(TileMultiblockBase tile, ForgeDirection from, FluidStack resource,
			boolean doDrain);

	public abstract FluidStack drain(TileMultiblockBase tile, ForgeDirection from, int maxDrain, boolean doDrain);

	public abstract boolean canFill(TileMultiblockBase tile, ForgeDirection from, Fluid fluid);

	public abstract boolean canDrain(TileMultiblockBase tile, ForgeDirection from, Fluid fluid);

	public abstract FluidTankInfo[] getTankInfo(TileMultiblockBase tile, ForgeDirection from);

}
