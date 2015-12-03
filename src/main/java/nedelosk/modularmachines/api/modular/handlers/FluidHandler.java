package nedelosk.modularmachines.api.modular.handlers;

import java.util.ArrayList;

import nedelosk.forestday.api.FluidTankBasic;
import nedelosk.forestday.api.INBTTagable;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.producers.fluids.IProducerTank;
import nedelosk.modularmachines.api.producers.managers.fluids.IProducerTankManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class FluidHandler implements INBTTagable, IFluidHandler {

	public FluidTankBasic[] tanks = new FluidTankBasic[3];
	public IModular machine;

	public FluidHandler(IModular machine) {
		this.machine = machine;
	}

	public FluidHandler(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}

	/*
	 * Add Tank to the Manager
	 */
	public void addTank(int id, IProducerTank tank) {
		if(tank == null)
			tanks[id] = null;
		else
			tanks[id] = new FluidTankBasic(tank.getCapacity());
	}

	/*
	 * Get Tank to the Manager
	 */
	public FluidTankBasic getTank(int id) {
		return tanks[id];
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagCompound nbtT = nbt.getCompoundTag("FluidHandler");
		NBTTagList list = nbtT.getTagList("Tanks", 10);
		int[] tankCapacitys = nbtT.getIntArray("TankCapacitys");
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			short position = nbtTag.getShort("Position");
			tanks[position] = new FluidTankBasic(tankCapacitys[position]);
			tanks[position].readFromNBT(nbtTag);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound nbtT = new NBTTagCompound();
		NBTTagList list = new NBTTagList();
		int[] tankCapacitys = new int[tanks.length];
		for (int i = 0; i < tanks.length; i++) {
			if (tanks[i] != null) {
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
		if (!doFill)
			return 0;
		IProducerTankManager manager = machine.getTankManeger().getProducer();
		if (manager != null) {
			ArrayList<Integer> directions = new ArrayList<Integer>();
			for (int d = 0; d < manager.getManager().getDirections().length; d++) {
				ForgeDirection direction = manager.getManager().getDirections()[d];
				if (direction != null)
					if (direction == from || from == ForgeDirection.UNKNOWN)
						directions.add(d);
			}
			if (directions.isEmpty()) {
				for (int d = 0; d < manager.getManager().getDirections().length; d++) {
					ForgeDirection direction = manager.getManager().getDirections()[d];
					if (direction != null)
						if (direction == ForgeDirection.UNKNOWN)
							directions.add(d);
				}
			}
			if (!directions.isEmpty()) {
				ArrayList<Integer> filters = new ArrayList<Integer>();
				for (int f = 0; f < manager.getManager().getFilters().length; f++) {
					Fluid filter = manager.getManager().getFilters()[f];
					for (int d : directions) {
						if (d == f) {
							if (filter == resource.getFluid())
								filters.add(f);
						}
					}
				}
				if (filters.isEmpty()) {
					for (int f = 0; f < manager.getManager().getFilters().length; f++) {
						for (int d : directions) {
							if (d == f) {
								filters.add(f);
							}
						}
					}
				}
				if (!filters.isEmpty()) {
					ArrayList<Integer> prioritys = new ArrayList<Integer>();
					for (int p = 0; p < manager.getManager().getPrioritys().length; p++) {
						int priority = manager.getManager().getPrioritys()[p];
						if (priority != 0)
							for (int f : filters) {
								if (p == f) {
									prioritys.add(p);
								}
							}
					}
					if (prioritys.isEmpty()) {
						for (int p = 0; p < manager.getManager().getPrioritys().length; p++) {
							for (int f : filters) {
								if (p == f) {
									prioritys.add(p);
								}
							}
						}
					}
					if (!prioritys.isEmpty()) {
						ArrayList<Integer> tankPrioritys = new ArrayList<>();
						tankPrioritys.add(prioritys.get(0));
						int priority = manager.getManager().getPrioritys()[0];
						for (int i = 1;i < prioritys.size();i++) {
							int p = prioritys.get(i);
							int pr = manager.getManager().getPrioritys()[p];
							if (pr > priority) {
								priority = pr;
								tankPrioritys.add(0, p);
							}else if(pr == priority){
								tankPrioritys.add(p);
							}
						}
						if (!tankPrioritys.isEmpty()) {
							for (int p : tankPrioritys) {
								if (tanks[p] != null && !tanks[p].isFull()){
									int fill = tanks[p].fill(resource, doFill);
									if(fill > 0)
										machine.getMachine().getWorldObj().markBlockForUpdate(machine.getMachine().getXCoord(), machine.getMachine().getYCoord(), machine.getMachine().getZCoord());
									return fill;
								}else
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
		if (!doDrain)
			return null;
		IProducerTankManager manager = machine.getTankManeger().getProducer();
		if (manager != null) {
			ArrayList<Integer> directions = new ArrayList<Integer>();
			for (int d = 0; d < manager.getManager().getDirections().length; d++) {
				ForgeDirection direction = manager.getManager().getDirections()[d];
				if (direction != null)
					if (direction == from || from == ForgeDirection.UNKNOWN)
						directions.add(d);
			}
			if (directions.isEmpty()) {
				for (int d = 0; d < manager.getManager().getDirections().length; d++) {
					ForgeDirection direction = manager.getManager().getDirections()[d];
					if (direction != null)
						if (direction == ForgeDirection.UNKNOWN)
							directions.add(d);
				}
			}
			if (!directions.isEmpty()) {
				ArrayList<Integer> filters = new ArrayList<Integer>();
				for (int f = 0; f < manager.getManager().getFilters().length; f++) {
					Fluid filter = manager.getManager().getFilters()[f];
					for (int d : directions) {
						if (d == f) {
							if (filter == resource.getFluid())
								filters.add(f);
						}
					}
				}
				if (filters.isEmpty()) {
					for (int f = 0; f < manager.getManager().getFilters().length; f++) {
						for (int d : directions) {
							if (d == f) {
								filters.add(f);
							}
						}
					}
				}
				if (!filters.isEmpty()) {
					ArrayList<Integer> prioritys = new ArrayList<Integer>();
					for (int p = 0; p < manager.getManager().getPrioritys().length; p++) {
						int[] prioritysA = manager.getManager().getPrioritys();
						int priority = manager.getManager().getPrioritys()[p];
						if (priority != 0)
							for (int f : filters) {
								if (p == f) {
									prioritys.add(p);
								}
							}
					}
					if (prioritys.isEmpty()) {
						for (int p = 0; p < manager.getManager().getPrioritys().length; p++) {
							for (int f : filters) {
								if (p == f) {
									prioritys.add(p);
								}
							}
						}
					}
					if (!prioritys.isEmpty()) {
						ArrayList<Integer> tankPrioritys = new ArrayList<>();
						tankPrioritys.add(prioritys.get(0));
						int priority = manager.getManager().getPrioritys()[0];
						for (int p : prioritys) {
							int pr = manager.getManager().getPrioritys()[p];
							if (pr > priority) {
								priority = pr;
								tankPrioritys.add(0, p);
							}
						}
						if (!tankPrioritys.isEmpty()) {
							for (int p : tankPrioritys) {
								if (tanks[p] != null && !tanks[p].isEmpty()){
									FluidStack drain = tanks[p].drain(resource, doDrain);
									if(drain != null)
										machine.getMachine().getWorldObj().markBlockForUpdate(machine.getMachine().getXCoord(), machine.getMachine().getYCoord(), machine.getMachine().getZCoord());
									return drain;
								}else
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
		if (!doDrain)
			return null;
		IProducerTankManager manager = machine.getTankManeger().getProducer();
		if (manager != null) {
			ArrayList<Integer> directions = new ArrayList<Integer>();
			for (int d = 0; d < manager.getManager().getDirections().length; d++) {
				ForgeDirection direction = manager.getManager().getDirections()[d];
				if (direction != null)
					if (direction == from || from == ForgeDirection.UNKNOWN)
						directions.add(d);
			}
			if (directions.isEmpty()) {
				for (int d = 0; d < manager.getManager().getDirections().length; d++) {
					ForgeDirection direction = manager.getManager().getDirections()[d];
					if (direction != null)
						if (direction == ForgeDirection.UNKNOWN)
							directions.add(d);
				}
			}
			if (!directions.isEmpty()) {
				ArrayList<Integer> prioritys = new ArrayList<Integer>();
				for (int p = 0; p < manager.getManager().getPrioritys().length; p++) {
					int priority = manager.getManager().getPrioritys()[p];
					if (priority != 0)
						for (int d : directions) {
							if (p == d) {
								prioritys.add(p);
							}
						}
				}
				if (prioritys.isEmpty()) {
					for (int p = 0; p < manager.getManager().getPrioritys().length; p++) {
						for (int d : directions) {
							if (p == d) {
								prioritys.add(p);
							}
						}
					}
				}
				if (!prioritys.isEmpty()) {
					ArrayList<Integer> tankPrioritys = new ArrayList<>();
					tankPrioritys.add(prioritys.get(0));
					int priority = manager.getManager().getPrioritys()[0];
					for (int p : prioritys) {
						int pr = manager.getManager().getPrioritys()[p];
						if (pr > priority) {
							priority = pr;
							tankPrioritys.add(0, p);
						}
					}
					if (!tankPrioritys.isEmpty()) {
						for (int p : tankPrioritys) {
							if (tanks[p] != null && !tanks[p].isEmpty()){
								FluidStack drain = tanks[p].drain(maxDrain, doDrain);
								if(drain != null)
									machine.getMachine().getWorldObj().markBlockForUpdate(machine.getMachine().getXCoord(), machine.getMachine().getYCoord(), machine.getMachine().getZCoord());
								return drain;
							}else
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
		for (int i = 0; i < tanks.length; i++) {
			if (tanks[i] != null)
				infos.add(tanks[i].getInfo());
		}
		return infos.toArray(new FluidTankInfo[infos.size()]);
	}

	public void setMachine(IModular machine) {
		this.machine = machine;
	}

}
