package de.nedelosk.modularmachines.common.transport;

import de.nedelosk.modularmachines.api.transport.IPartSide;
import de.nedelosk.modularmachines.api.transport.ITransportPart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class PartSide implements IPartSide {

	protected final EnumFacing side;
	protected boolean isActive;
	protected final ITransportPart part;

	public PartSide(EnumFacing side, ITransportPart part) {
		this.side = side;
		this.part = part;
		this.isActive = false;
	}

	@Override
	public EnumFacing getSide() {
		return side;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public void setActive() {
		isActive = true;
	}

	@Override
	public void setUnactive() {
		isActive = false;
	}

	@Override
	public ITransportPart getPart() {
		return part;
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("IsActive", isActive);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		isActive = compound.getBoolean("IsActive");
	}
}
