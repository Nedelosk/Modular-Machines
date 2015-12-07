package nedelosk.forestday.api.multiblocks;

import net.minecraft.nbt.NBTTagCompound;

public class MultiblockModifierValveType implements IMultiblockModifier {

	public ValveType valveType;

	public static enum ValveType {
		DEFAULT, INPUT, OUTPUT
	}

	@Override
	public String getModifierName() {
		return "valveType";
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("ValveType"))
			valveType = ValveType.values()[nbt.getInteger("ValveType")];
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		if (valveType != null)
			nbt.setInteger("ValveType", valveType.ordinal());
	}

}
