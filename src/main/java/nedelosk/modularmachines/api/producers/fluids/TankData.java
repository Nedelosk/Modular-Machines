package nedelosk.modularmachines.api.producers.fluids;

import nedelosk.forestcore.library.FluidTankBasic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public final class TankData implements ITankData {

	private FluidTankBasic tank;
	private int producer;
	private ForgeDirection direction;
	private TankMode mode;
	
	public TankData(FluidTankBasic tank, int producer, ForgeDirection direction, TankMode mode) {
		this.tank = tank;
		this.producer = producer;
		this.direction = direction;
		this.mode = mode;
	}
	
	public TankData() {
		this.tank = null;
		this.producer = 0;
		this.direction = ForgeDirection.UNKNOWN;
		this.mode = TankMode.NONE;
	}
	
	public TankData(FluidTankBasic tank) {
		this.tank = tank;
		this.producer = 0;
		this.direction = ForgeDirection.UNKNOWN;
		this.mode = TankMode.NONE;
	}
	
	@Override
	public void setTank(FluidTankBasic tank) {
		this.tank = tank;
	}
	
	@Override
	public FluidTankBasic getTank() {
		return tank;
	}
	
	@Override
	public void setProducer(int producer) {
		this.producer = producer;
	}
	
	@Override
	public int getProducer() {
		return producer;
	}
	
	@Override
	public void setDirection(ForgeDirection direction) {
		this.direction = direction;
	}
	
	@Override
	public ForgeDirection getDirection() {
		return direction;
	}
	
	@Override
	public void setMode(TankMode mode) {
		this.mode = mode;
	}
	
	@Override
	public TankMode getMode() {
		return mode;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		if(producer != -1){
			nbt.setInteger("Producer", producer);
		}
		if(direction != null){
			nbt.setInteger("Direction", direction.ordinal());
		}
		if(mode != null){
			nbt.setInteger("Mode", mode.ordinal());
		}
		
		if (tank != null) {
			NBTTagCompound nbtTank = new NBTTagCompound();
			tank.writeToNBT(nbtTank);
			nbtTank.setInteger("Capacity", tank.getCapacity());
			nbt.setTag("Tank", nbtTank);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("Producer")){
			producer = nbt.getInteger("Producer");
		}
		if(nbt.hasKey("Direction")){
			direction = ForgeDirection.values()[nbt.getInteger("Direction")];
		}
		if(nbt.hasKey("Mode")){
			mode = TankMode.values()[nbt.getInteger("Mode")];
		}
		
		if(nbt.hasKey("Tank")){
			NBTTagCompound nbtTank = nbt.getCompoundTag("Tank");
			
			tank = new FluidTankBasic(nbtTank.getInteger("Capacity"));
			tank.readFromNBT(nbtTank);
		}
		
	}
	
}
