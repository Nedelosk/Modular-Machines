package nedelosk.forestday.common.blocks.tiles;

import nedelosk.forestcore.library.tile.TileBase;
import nedelosk.forestday.api.crafting.ForestDayCrafting;
import nedelosk.forestday.api.crafting.WoodType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;

public class TileAsh extends TileBase {
	
	private WoodType[] woodTypes;
	
	public TileAsh() {
	}

	@Override
	public void updateClient() {}

	@Override
	public void updateServer() {}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		if (woodTypes != null) {
			NBTTagList nbtList = new NBTTagList();
			for(WoodType type : woodTypes){
				nbtList.appendTag(new NBTTagString(type.getName()));
			}
			nbt.setTag("WoodTypes", nbtList);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		if (nbt.hasKey("WoodTypes")) {
			NBTTagList nbtList = nbt.getTagList("WoodTypes", 8);
			woodTypes = new WoodType[nbtList.tagCount()];
			for(int i = 0;i < nbtList.tagCount();i++){
				String type = nbtList.getStringTagAt(i);
				woodTypes[i] = ForestDayCrafting.woodManager.get(type);
			}
		}
	}
	
	
	public WoodType[] getWoodTypes() {
		return woodTypes;
	}
	
	
	public void setWoodTypes(WoodType[] woodTypes) {
		this.woodTypes = woodTypes;
	}
}
