package nedelosk.forestday.common.blocks.tiles;

import nedelosk.forestday.common.multiblocks.TileMultiblockBase;
import nedelosk.forestday.common.types.WoodType;
import net.minecraft.nbt.NBTTagCompound;

public class TileCharcoalKiln extends TileMultiblockBase {
	
	public WoodType type;
	public boolean isConsumed;
	
	public TileCharcoalKiln() {
		super();
	}
	
	public void setType(WoodType type) {
		this.type = type;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		if(type != null)
		{
			NBTTagCompound nbtTag = new NBTTagCompound();
			type.writeToNBT(nbtTag);
			nbt.setTag("Wood", nbtTag);
		}
		nbt.setBoolean("isConsumed", isConsumed);
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		if(nbt.hasKey("Wood"))
		{
			type = WoodType.loadFromNBT(nbt.getCompoundTag("Wood"));
		}
		isConsumed = nbt.getBoolean("isConsumed");
		
	}

}
