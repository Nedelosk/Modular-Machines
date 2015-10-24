package nedelosk.modularmachines.common.modular.machines.modular.handlers;

import java.util.ArrayList;

import nedelosk.modularmachines.api.modular.machines.basic.IModular;
import nedelosk.modularmachines.api.modular.machines.basic.IModularInventory;
import nedelosk.modularmachines.api.modular.module.tool.producer.fluids.IProducerTank;
import nedelosk.modularmachines.common.modular.module.tool.producer.fluids.ProducerTankManager;
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

	public FluidTankNedelosk[] tanks = new FluidTankNedelosk[3];
	public IModular machine;
	
	public FluidHandler(IModular machine){
		this.machine = machine;
	}
	
	public FluidHandler(NBTTagCompound nbt, IModular machine){
		readFromNBT(nbt);
		this.machine = machine;
	}
	
	public void addTanks(int id, IProducerTank tank) {
		this.tanks[id] = new FluidTankNedelosk(tank.getCapacity());
	}
	
	public FluidTankNedelosk getTank(int id) {
		return tanks[id];
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagCompound nbtT = nbt.getCompoundTag("FluidHandler");
		NBTTagList list = nbtT.getTagList("Tanks", 10);
		int[] tankCapacitys = nbtT.getIntArray("TankCapacitys");
		for(int i= 0;i < list.tagCount();i++)
		{
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			short position = nbtTag.getShort("Position");
			tanks[position] = new FluidTankNedelosk(tankCapacitys[position]);
			tanks[position].readFromNBT(nbtTag);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound nbtT = new NBTTagCompound();
		NBTTagList list = new NBTTagList();
		int[] tankCapacitys = new int[tanks.length];
		for(int i = 0;i < tanks.length;i++)
		{
			if(tanks[i] != null){
				NBTTagCompound nbtTag = new NBTTagCompound();
				tanks[i].writeToNBT(nbtTag);
				tankCapacitys[i] = tanks[i].getCapacity();
				nbtTag.setShort("Position", (short) i);
				list.appendTag(nbtTag);
			}
		}
		nbtT.setTag("Tanks", list);
		nbtT.setIntArray("TankCapacitys", tankCapacitys);
		nbt.setTag("FluidHandler", nbtT);
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(!doFill)
			return 0;
		ProducerTankManager manager = (ProducerTankManager) machine.getTankManeger().getProducer();
		if(manager != null)
		{
			ArrayList<Integer> directions = new ArrayList<Integer>();
			for(int d = 0;d < manager.manager.directions.length;d++)
			{
				ForgeDirection direction = manager.manager.directions[d];
				if(direction != null)
					if(direction == from)
						directions.add(d);
			}
			if(directions.isEmpty()){
				for(int d = 0;d < manager.manager.directions.length;d++)
				{
					ForgeDirection direction = manager.manager.directions[d];
					if(direction != null)
						if(direction == ForgeDirection.UNKNOWN)
							directions.add(d);
				}
			}
			if(!directions.isEmpty()){
				ArrayList<Integer> filters = new ArrayList<Integer>();
				for(int f = 0;f < manager.manager.filters.length;f++)
				{
					Fluid filter = manager.manager.filters[f];
					for(int d : directions)
					{
						if(d == f)
						{
							if(filter == resource.getFluid())
								filters.add(f);
						}
					}
				}
				if(filters.isEmpty()){
					for(int f = 0;f < manager.manager.filters.length;f++)
					{
						Fluid filter = manager.manager.filters[f];
						for(int d : directions)
						{
							if(d == f)
							{
								filters.add(f);
							}
						}
					}
				}
				if(!filters.isEmpty()){
					ArrayList<Integer> prioritys = new ArrayList<Integer>();
					for(int p = 0;p < manager.manager.prioritys.length;p++)
					{
						int priority = manager.manager.prioritys[p];
						if(priority != 0)
							for(int f : filters)
							{
								if(p == f)
								{
									prioritys.add(p);
								}
							}
					}
					if(prioritys.isEmpty()){
						for(int p = 0;p < manager.manager.prioritys.length;p++)
						{
							for(int f : filters)
							{
								if(p == f)
								{
									prioritys.add(p);
								}
							}
						}
					}
					if(!prioritys.isEmpty()){
						ArrayList<Integer> tankPrioritys = new ArrayList<>();
						tankPrioritys.add(prioritys.get(0));
						int priority = manager.manager.prioritys[0];
						for(int p : prioritys)
						{
							int pr = manager.manager.prioritys[p];
							if(pr > priority){
								priority = pr;
								tankPrioritys.add(0, p);
							}
						}
						if(!tankPrioritys.isEmpty())
						{
							for(int p : tankPrioritys)
							{
								if(tanks[p] != null && !tanks[p].isFull())
									return tanks[p].fill(resource, doFill);
								else
									continue;
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
		if(!doDrain)
			return null;
		ProducerTankManager manager = (ProducerTankManager) machine.getTankManeger().getProducer();
		if(manager != null)
		{
			ArrayList<Integer> directions = new ArrayList<Integer>();
			for(int d = 0;d < manager.manager.directions.length;d++)
			{
				ForgeDirection direction = manager.manager.directions[d];
				if(direction != null)
					if(direction == from)
						directions.add(d);
			}
			if(directions.isEmpty()){
				for(int d = 0;d < manager.manager.directions.length;d++)
				{
					ForgeDirection direction = manager.manager.directions[d];
					if(direction != null)
						if(direction == ForgeDirection.UNKNOWN)
							directions.add(d);
				}
			}
			if(!directions.isEmpty()){
				ArrayList<Integer> filters = new ArrayList<Integer>();
				for(int f = 0;f < manager.manager.filters.length;f++)
				{
					Fluid filter = manager.manager.filters[f];
					for(int d : directions)
					{
						if(d == f)
						{
							if(filter == resource.getFluid())
								filters.add(f);
						}
					}
				}
				if(filters.isEmpty()){
					for(int f = 0;f < manager.manager.filters.length;f++)
					{
						Fluid filter = manager.manager.filters[f];
						for(int d : directions)
						{
							if(filter == resource.getFluid())
								if(d == f)
								{
									filters.add(f);
								}
						}
					}
				}
				if(!filters.isEmpty()){
					ArrayList<Integer> prioritys = new ArrayList<Integer>();
					for(int p = 0;p < manager.manager.prioritys.length;p++)
					{
						int priority = manager.manager.prioritys[p];
						if(priority != 0)
							for(int f : filters)
							{
								if(p == f)
								{
									prioritys.add(p);
								}
							}
					}
					if(prioritys.isEmpty()){
						for(int p = 0;p < manager.manager.prioritys.length;p++)
						{
							for(int f : filters)
							{
								if(p == f)
								{
									prioritys.add(p);
								}
							}
						}
					}
					if(!prioritys.isEmpty()){
						ArrayList<Integer> tankPrioritys = new ArrayList<>();
						tankPrioritys.add(prioritys.get(0));
						int priority = manager.manager.prioritys[0];
						for(int p : prioritys)
						{
							int pr = manager.manager.prioritys[p];
							if(pr > priority){
								priority = pr;
								tankPrioritys.add(0, p);
							}
						}
						if(!tankPrioritys.isEmpty())
						{
							for(int p : tankPrioritys)
							{
								if(tanks[p] != null && !tanks[p].isEmpty())
									return tanks[p].drain(resource, doDrain);
								else
									continue;
							}
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if(!doDrain)
			return null;
		ProducerTankManager manager = (ProducerTankManager) machine.getTankManeger().getProducer();
		if(manager != null)
		{
			ArrayList<Integer> directions = new ArrayList<Integer>();
			for(int d = 0;d < manager.manager.directions.length;d++)
			{
				ForgeDirection direction = manager.manager.directions[d];
				if(direction != null)
					if(direction == from)
						directions.add(d);
			}
			if(directions.isEmpty()){
				for(int d = 0;d < manager.manager.directions.length;d++)
				{
					ForgeDirection direction = manager.manager.directions[d];
					if(direction != null)
						if(direction == ForgeDirection.UNKNOWN)
							directions.add(d);
				}
			}
			if(!directions.isEmpty()){
				ArrayList<Integer> prioritys = new ArrayList<Integer>();
				for(int p = 0;p < manager.manager.prioritys.length;p++)
				{
					int priority = manager.manager.prioritys[p];
					if(priority != 0)
						for(int d : directions)
						{
							if(p == d)
							{
								prioritys.add(p);
							}
						}
				}
				if(prioritys.isEmpty()){
					for(int p = 0;p < manager.manager.prioritys.length;p++)
					{
						for(int d : directions)
						{
							if(p == d)
							{
								prioritys.add(p);
							}
						}
					}
				}
				if(!prioritys.isEmpty()){
					ArrayList<Integer> tankPrioritys = new ArrayList<>();
					tankPrioritys.add(prioritys.get(0));
					int priority = manager.manager.prioritys[0];
					for(int p : prioritys)
					{
						int pr = manager.manager.prioritys[p];
						if(pr > priority){
							priority = pr;
							tankPrioritys.add(0, p);
						}
					}
					if(!tankPrioritys.isEmpty())
					{
						for(int p : tankPrioritys)
						{
							if(tanks[p] != null && !tanks[p].isEmpty())
								return tanks[p].drain(maxDrain, doDrain);
							else
								continue;
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		ArrayList<FluidTankInfo> infos = new ArrayList<>();
		for(int i = 0;i < tanks.length;i++)
		{
			if(tanks[i] != null)
				infos.add(tanks[i].getInfo());
		}
		return infos.toArray(new FluidTankInfo[infos.size()]);
	}

}
