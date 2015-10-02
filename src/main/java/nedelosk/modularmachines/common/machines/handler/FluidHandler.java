package nedelosk.modularmachines.common.machines.handler;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.machines.basic.IModularInventory;
import nedelosk.modularmachines.common.machines.module.fluids.ModuleTankManager;
import nedelosk.nedeloskcore.api.INBTTagable;
import nedelosk.nedeloskcore.common.fluids.FluidTankNedelosk;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class FluidHandler implements INBTTagable, IFluidHandler {

	public FluidTankNedelosk[] tanks;
	public IModularInventory machine;
	
	/**
	 * @param tanks max size = 3
	 */
	public FluidHandler(IModularInventory machine, FluidTankNedelosk... tanks){
		this.tanks = tanks;
		this.machine = machine;
	}
	
	public FluidHandler(NBTTagCompound nbt, IModularInventory machine){
		readFromNBT(nbt);
		this.machine = machine;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagCompound nbtT = nbt.getCompoundTag("FluidHandler");
		NBTTagList list = nbtT.getTagList("Tanks", 10);
		int[] tankCapacitys = nbtT.getIntArray("TankCapacitys");
		tanks = new FluidTankNedelosk[list.tagCount()];
		for(int i= 0;i < list.tagCount();i++)
		{
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			tanks[i] = new FluidTankNedelosk(tankCapacitys[i]);
			tanks[i].readFromNBT(nbtTag);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound nbtT = new NBTTagCompound();
		NBTTagList list = new NBTTagList();
		int[] tankCapacitys = new int[tanks.length];
		for(int i = 0;i < tanks.length;i++)
		{
			NBTTagCompound nbtTag = new NBTTagCompound();
			tanks[i].writeToNBT(nbtTag);
			tankCapacitys[i] = tanks[i].getCapacity();
			list.appendTag(nbtTag);
		}
		nbtT.setTag("Tanks", list);
		nbtT.setIntArray("TankCapacitys", tankCapacitys);
		nbt.setTag("FluidHandler", nbtT);
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(!doFill)
			return 0;
		ModuleTankManager manager = (ModuleTankManager) machine.getTankManeger().getModule();
		if(manager != null)
		{
			ArrayList<Integer> listDirection = new ArrayList<Integer>();
			for(int d = 0;d < manager.manager.directions.length;d++)
			{
				ForgeDirection direction = manager.manager.directions[d];
				if(direction != null)
					if(direction == from)
						listDirection.add(d);
			}
			if(listDirection.size() == 0)
				for(int d = 0;d < manager.manager.directions.length;d++)
				{
					ForgeDirection direction = manager.manager.directions[d];
					if(direction != null)
						if(direction == ForgeDirection.UNKNOWN)
							listDirection.add(d);
				}
			if(listDirection.size() != 0)
			{
				ArrayList<Integer> listFilters = new ArrayList<Integer>();
				for(int f = 0;f < manager.manager.filters.length;f++)
				{
					Fluid filter = manager.manager.filters[f];
					for(int d : listDirection)
					{
						if(filter != null)
							if(d == f)
							{
								if(filter == resource.fluid)
									listFilters.add(f);
							}
					}
				}
				if(listFilters.size() == 0)
				{
					for(int f = 0;f < manager.manager.filters.length;f++)
					{
						Fluid filter = manager.manager.filters[f];
						if(filter != null)
							for(int d : listDirection)
							{
								if(d == f)
								{
									if(filter == null)
										listFilters.add(f);
								}
						}
					}
				}
				if(listFilters.size() != 0)
				{
					ArrayList<Integer> listPrioritys = new ArrayList<Integer>();
					for(int p = 0;p < manager.manager.prioritys.length;p++)
					{
						int priority = manager.manager.prioritys[p];
						if(priority != 0)
							for(int f : listFilters)
							{
								if(p == f)
								{
									listPrioritys.add(p);
								}
							}
					}
					if(listPrioritys.size() != 0)
					{
					Integer[] prioritys  = new Integer[tanks.length];
					prioritys[0] = listPrioritys.get(0);
					for(int p : listPrioritys)
					{
						int priority = manager.manager.prioritys[p];
						if(priority < manager.manager.prioritys[prioritys[0]])
							prioritys[0] = p;
						else if(priority == manager.manager.prioritys[prioritys[0]])
							prioritys[1] = p;
					}
					if(prioritys.length > 0)
					{
						for(int i = 0;i < prioritys.length;i++)
						{
							if(!tanks[prioritys[i]].isFull())
								return tanks[prioritys[i]].fill(resource, doFill);
						}
					}
					}
				}
			}
		}
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
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		FluidTankInfo[] infos = new FluidTankInfo[tanks.length];
		for(int i = 0;i < tanks.length;i++)
		{
			infos[i]  = tanks[i].getInfo();
		}
		return infos;
	}

}
