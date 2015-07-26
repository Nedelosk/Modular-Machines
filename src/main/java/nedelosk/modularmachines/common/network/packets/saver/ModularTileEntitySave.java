package nedelosk.modularmachines.common.network.packets.saver;

import nedelosk.nedeloskcore.api.INBTTagable;
import net.minecraft.nbt.NBTTagCompound;

public class ModularTileEntitySave implements INBTTagable {

	public int x;
	public int y;
	public int z;
	public String page;
	
	public ModularTileEntitySave(String page, int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.page = page;
	}
	
	public ModularTileEntitySave(NBTTagCompound nbt) {
		readFromNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		x = nbt.getInteger("X");
		y = nbt.getInteger("Y");
		z = nbt.getInteger("Z");
		
		this.page = nbt.getString("Page");
		
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("X", x);
		nbt.setInteger("Y", y);
		nbt.setInteger("Z", z);
		
		nbt.setString("Page", page);
	}
	
}
