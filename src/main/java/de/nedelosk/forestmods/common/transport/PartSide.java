package de.nedelosk.forestmods.common.transport;

import de.nedelosk.forestmods.api.transport.IPartSide;
import de.nedelosk.forestmods.api.transport.ITransportPart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class PartSide implements IPartSide {

	protected final ForgeDirection side;
	protected boolean isActive;
	protected final ITransportPart part;

	public PartSide(ForgeDirection side, ITransportPart part) {
		this.side = side;
		this.part = part;
		this.isActive = true;
	}

	@Override
	public ForgeDirection getSide() {
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
