package nedelosk.nedeloskcore.api;

import net.minecraft.nbt.NBTTagCompound;

public class MultiblockModifierValveTypeString extends MultiblockModifierValveType {

	public String filter;
	
	@Override
	public String getModifierName() {
		return super.getModifierName() + "Fluid";
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if(filter != null)
			nbt.setString("Filter", filter);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if(nbt.hasKey("FilterID"))
			filter = nbt.getString("Filter");
		
	}
	
}
