package de.nedelosk.forestmods.library.transport;

import java.util.List;
import java.util.Set;

import de.nedelosk.forestmods.library.utils.BlockPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

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

	BlockPos getWorldLocation();

	IPartSide[] getSides();

	IPartSide getSide(ForgeDirection direction);

	IPartSide getSide(int direction);

	void setVisited();

	void setUnvisited();

	boolean isVisited();

	void assertDetached();

	void onAttached(ITransportSystem newSystem);

	void onDetached(ITransportSystem oldSystem);
}
