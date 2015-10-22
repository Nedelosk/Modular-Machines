package nedelosk.nedeloskcore.common.blocks.tile;

import net.minecraft.nbt.NBTTagCompound;

public abstract class TileMachineBase extends TileBaseInventory {

	protected int tier;
	public short facing;
	protected int timer;
	protected int timerMax = 50;
	
	public TileMachineBase(int slots) {
		super(slots);
	}
	
	public TileMachineBase() {
		super();
	}
	
	public abstract String getMachineName();

	@Override
	public String getMachineTileName() {
		return getMachineName();
	}
	
	public int getTier() {
		return tier;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("facing", facing);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		facing = nbt.getShort("facing");
		if(worldObj != null)
		{
		tier = getBlockMetadata() + 1;
		}
	}
	
	public short getFacing() {
		return facing;
	}

}
