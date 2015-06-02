package nedelosk.forestday.machines.fan.tile;

import nedelosk.forestday.common.config.ForestdayConfig;
import nedelosk.nedeloskcore.common.fluids.FluidTankNedelosk;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileFan extends TileEntity implements IFluidHandler {

	private FluidTankNedelosk tank = new FluidTankNedelosk(32000);
	
	private int airTimer;
	private int airTimerTotal = 45;
	
	public TileFan() {
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(airTimer < airTimerTotal)
		{
			airTimer++;
		}
		else
		{
			tank.fill(new FluidStack(FluidRegistry.getFluid("air"), ForestdayConfig.fanAirProduction), true);
			airTimer = 0;
		}
		
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return tank.drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { tank.getInfo() };
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
	      if(tank.getFluidAmount() > 0) {
		        NBTTagCompound tankRoot = new NBTTagCompound();
		        tank.writeToNBT(tankRoot);
		        nbt.setTag("tank", tankRoot);
		      }
	      
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
	    if(nbt.hasKey("tank")) {
	        NBTTagCompound tankRoot = (NBTTagCompound) nbt.getTag("tank");
	        if(tankRoot != null) {
	          tank.readFromNBT(tankRoot);
	        } else {
	        	tank.setFluid(null);
	        }
	      } else {
	    	  tank.setFluid(null);
	      }
	}

}
