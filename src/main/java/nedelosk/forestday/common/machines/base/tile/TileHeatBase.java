package nedelosk.forestday.common.machines.base.tile;

import nedelosk.nedeloskcore.common.blocks.tile.TileMachineBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileHeatBase extends TileMachineBase {

	protected TileHeatGenerator generator;
	protected int heat;
	
	public TileHeatBase(int slots) {
		super(slots);
	}
	
	public int getHeat()
	{
		return heat;
	}
	
	public TileHeatGenerator getGenerator()
	{
		return generator;
	}
	
	@Override
	public void updateServer() {
		
		if(timer >= timerMax)
		{
			TileEntity g0 = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
			TileEntity g1 =  worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
			TileEntity g2 =  worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
			TileEntity g3 =  worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
			int heat = 0;
			int heat0 = 0;
			int heat1 = 0;
			int heat2 = 0;
			int heat3 = 0;
			if(g0 != null && g0 instanceof TileHeatGenerator)
			{
				heat0 = ((TileHeatGenerator)g0).getHeat();
			}
			if(g1 != null  && g1 instanceof TileHeatGenerator)
			{
				heat1 = ((TileHeatGenerator)g1).getHeat();
			}
			if(g2 != null  && g2 instanceof TileHeatGenerator)
			{
				heat2 = ((TileHeatGenerator)g2).getHeat();
			}
			if(g3 != null  && g3 instanceof TileHeatGenerator)
			{
				heat3 = ((TileHeatGenerator)g3).getHeat();
			}
			if(heat1 > heat0)
			heat = Math.max(heat0, heat1);
			if(heat2 > heat)
			heat = Math.max(heat, heat2);
			if(heat3 > heat)
			heat = Math.max(heat, heat3);
			this.heat = heat;
		}
		else
			timer++;
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		heat = nbt.getInteger("Heat");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("Heat", heat);
	}

}
