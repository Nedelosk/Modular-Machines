package nedelosk.forestday.structure.base.blocks.tile;

import nedelosk.forestday.api.structure.tile.ITileCoilHeat;
import net.minecraft.nbt.NBTTagCompound;

public class TileCoilHeat extends TileStructure implements ITileCoilHeat {
	
	public TileCoilHeat() {
		super(100, "coilHeat");
		this.producibleHeat = 50;
	}
	
	public TileCoilHeat(int maxHeat, int pHeat, String uid) {
		super(maxHeat, "coilHeat" + uid);
		this.producibleHeat = pHeat;
	}
	
	private int producibleHeat;
	
	public int getProducibleHeat()
	{
		return this.producibleHeat;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("MaxCoilHeat", this.producibleHeat);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.producibleHeat = nbt.getInteger("MaxCoilHeat");
		
	}

	@Override
	public void updateClient() {
		
	}

	@Override
	public void updateServer() {
		
	}

}
