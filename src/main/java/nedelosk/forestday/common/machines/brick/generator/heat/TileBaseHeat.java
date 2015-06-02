package nedelosk.forestday.common.machines.brick.generator.heat;

import nedelosk.forestday.common.machines.brick.base.TileBricks;
import nedelosk.nedeloskcore.common.blocks.tile.TileBaseInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileBaseHeat extends TileBricks {

	protected TileHeatGenerator generator;
	protected int heat;
	
	public TileBaseHeat(int slots) {
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
		
		if(burnTime >= burnTimeTotal)
		{
			TileHeatGenerator g0 = (TileHeatGenerator) worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
			TileHeatGenerator g1 = (TileHeatGenerator) worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
			TileHeatGenerator g2 = (TileHeatGenerator) worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
			TileHeatGenerator g3 = (TileHeatGenerator) worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
			TileHeatGenerator generator;
			int heat = 0;
			int heat0 = 0;
			int heat1 = 0;
			int heat2 = 0;
			int heat3 = 0;
			if(g0 != null)
			{
				heat0 = g0.getHeat();
			}
			if(g1 != null)
			{
				heat1 = g1.getHeat();
			}
			if(g2 != null)
			{
				heat2 = g2.getHeat();
			}
			if(g3 != null)
			{
				heat3 = g3.getHeat();
			}
			generator = g0;
			if(heat1 > heat0)
				generator = g1;
			heat = Math.max(heat0, heat1);
			if(heat2 > heat)
				generator = g2;
			heat = Math.max(heat, heat2);
			if(heat3 > heat)
				generator = g3;
			heat = Math.max(heat, heat3);
			this.heat = heat;
			this.generator = generator;
		}
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if(this.worldObj != null)
		{
			if(worldObj.getTileEntity(nbt.getInteger("gX"), nbt.getInteger("gY"), nbt.getInteger("gZ")) instanceof TileHeatGenerator)
			{
			generator = (TileHeatGenerator)worldObj.getTileEntity(nbt.getInteger("gX"), nbt.getInteger("gY"), nbt.getInteger("gZ"));
			}
			else
			{
				generator = null;
			}
		}
		heat = nbt.getInteger("Heat");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("Heat", heat);
		
		if(generator != null)
		{
		nbt.setInteger("gX", generator.xCoord);
		nbt.setInteger("gY", generator.yCoord);
		nbt.setInteger("gZ", generator.zCoord);
		}
	}

}
