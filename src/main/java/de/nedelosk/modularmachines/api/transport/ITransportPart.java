package de.nedelosk.modularmachines.api.transport;

import java.util.List;
import java.util.Set;

import de.nedelosk.modularmachines.common.utils.AdvancedBlockPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public interface ITransportPart {

	ITransportSystem getSystem();

	void createSides();

	Class<? extends ITransportSystem> getSystemType();

	ITransportSystem createNewSystem();

	void setSystem(ITransportSystem system);

	ITransportTileEntity getTileEntity();

	Set<ITransportSystem> attachToNeighbors();

	List<ITransportPart> getNeighboringParts();

	boolean isConnected();

	void writeToNBT(NBTTagCompound compound);

	void readFromNBT(NBTTagCompound compound);

	AdvancedBlockPos getWorldLocation();

	IPartSide[] getSides();

	IPartSide getSide(EnumFacing direction);

	IPartSide getSide(int direction);

	void setVisited();

	void setUnvisited();

	boolean isVisited();

	void assertDetached();

	void onAttached(ITransportSystem newSystem);

	void onDetached(ITransportSystem oldSystem);
}
