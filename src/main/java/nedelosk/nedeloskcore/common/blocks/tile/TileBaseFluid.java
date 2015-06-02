package nedelosk.nedeloskcore.common.blocks.tile;

import nedelosk.nedeloskcore.common.fluids.FluidTankNedelosk;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public abstract class TileBaseFluid extends TileBaseInventory implements
		IFluidHandler {

	protected FluidTankNedelosk[] tank = new FluidTankNedelosk[4];

	public TileBaseFluid(int slots) {
		super(slots);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for (int i = 0; i < tank.length; i++) {
			if (tank[i] != null) {
			if(tank[i].getFluidAmount() > 0)
			{
				NBTTagCompound tankNBT = new NBTTagCompound();
				tank[i].writeToNBT(tankNBT);
				nbt.setTag("tank" + i, tankNBT);
			}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for (int i = 0; i < tank.length; i++) {
			if (tank[i] != null) {
				if (nbt.hasKey("tank" + i)) {
					NBTTagCompound tankNBT = (NBTTagCompound) nbt.getTag("tank"
							+ i);
					if (tankNBT != null) {
						tank[i].readFromNBT(tankNBT);
					} else {
						tank[i].setFluid(null);
					}
				} else {
					tank[i].setFluid(null);
				}
			}
		}
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if (tank[0] != null && tank[1] != null && tank[2] != null
				&& tank[3] != null) {
			return new FluidTankInfo[] { tank[0].getInfo(), tank[1].getInfo(),
					tank[2].getInfo(), tank[3].getInfo() };
		} else if (tank[0] != null && tank[1] != null && tank[2] != null) {
			return new FluidTankInfo[] { tank[0].getInfo(), tank[1].getInfo(),
					tank[2].getInfo() };
		} else if (tank[0] != null && tank[1] != null) {
			return new FluidTankInfo[] { tank[0].getInfo(), tank[1].getInfo() };
		} else if (tank[0] != null) {
			return new FluidTankInfo[] { tank[0].getInfo() };
		} else {
			return null;
		}
	}
	
	public FluidTankNedelosk[] getTank() {
		return tank;
	}

}
