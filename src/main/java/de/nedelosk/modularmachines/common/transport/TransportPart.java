package de.nedelosk.modularmachines.common.transport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.nedelosk.modularmachines.api.transport.IPartSide;
import de.nedelosk.modularmachines.api.transport.ITransportPart;
import de.nedelosk.modularmachines.api.transport.ITransportSystem;
import de.nedelosk.modularmachines.api.transport.ITransportTileEntity;
import de.nedelosk.modularmachines.common.utils.AdvancedBlockPos;
import de.nedelosk.modularmachines.common.utils.Log;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.IChunkProvider;

public class TransportPart implements ITransportPart {

	protected final ITransportTileEntity tileEntity;
	protected IPartSide[] sides;
	protected ITransportSystem system;
	private boolean visited;

	public TransportPart(ITransportTileEntity tileEntity) {
		this.tileEntity = tileEntity;
		visited = false;
	}

	@Override
	public void createSides() {
		this.sides = new IPartSide[6];
		for(EnumFacing side : EnumFacing.VALUES) {
			sides[side.ordinal()] = new PartSide(side, this);
		}
	}

	@Override
	public ITransportSystem getSystem() {
		return system;
	}

	@Override
	public ITransportSystem createNewSystem() {
		return new TransportSystem(tileEntity.getWorld());
	}

	@Override
	public Class<? extends ITransportSystem> getSystemType() {
		return TransportSystem.class;
	}

	@Override
	public ITransportTileEntity getTileEntity() {
		return tileEntity;
	}

	@Override
	public Set<ITransportSystem> attachToNeighbors() {
		Set<ITransportSystem> controllers = null;
		ITransportSystem bestSystem = null;
		// Look for a compatible controller in our neighboring parts.
		List<ITransportPart> partsToCheck = getNeighboringParts();
		for(ITransportPart neighborPart : partsToCheck) {
			if (neighborPart.isConnected()) {
				ITransportSystem candidate = neighborPart.getSystem();
				if (!candidate.getClass().equals(getSystemType())) {
					// Skip multiblocks with incompatible types
					continue;
				}
				if (controllers == null) {
					controllers = new HashSet<ITransportSystem>();
					bestSystem = candidate;
				} else if (!controllers.contains(candidate) && candidate.shouldConsume(bestSystem)) {
					bestSystem = candidate;
				}
				controllers.add(candidate);
			}
		}
		// If we've located a valid neighboring controller, attach to it.
		if (bestSystem != null) {
			// attachBlock will call onAttached, which will set the controller.
			system = bestSystem;
			bestSystem.attachPart(this);
		}
		return controllers;
	}

	@Override
	public List<ITransportPart> getNeighboringParts() {
		int xCoord = tileEntity.getPos().getX();
		int yCoord = tileEntity.getPos().getY();
		int zCoord = tileEntity.getPos().getZ();
		TileEntity te;
		List<ITransportPart> neighborParts = new ArrayList<ITransportPart>();
		IChunkProvider chunkProvider = tileEntity.getWorld().getChunkProvider();
		testSide: for(IPartSide side : getSides()) {
			if (side.isActive()) {
				for(int i = 1; i < 9; i++) {
					int x = xCoord + (side.getSide().getFrontOffsetX() * i);
					int y = yCoord + (side.getSide().getFrontOffsetY() * i);
					int z = zCoord + (side.getSide().getFrontOffsetZ() * i);
					if (chunkProvider.getLoadedChunk(x >> 4, z >> 4) == null) {
						continue testSide;
					}
					te = tileEntity.getWorld().getTileEntity(new BlockPos(x, y, z));
					int opposites = side.getSide().getOpposite().ordinal();
					if (te instanceof ITransportTileEntity && ((ITransportTileEntity) te).getPart().getSides()[opposites].isActive()) {
						neighborParts.add(((ITransportTileEntity) te).getPart());
						continue testSide;
					}
				}
			}
		}
		return neighborParts;
	}

	@Override
	public boolean isConnected() {
		return system != null;
	}

	@Override
	public void setSystem(ITransportSystem system) {
		this.system = system;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		readSidesFromNBT(nbt.getTagList("Sides", 10));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setTag("Sides", writeSidesToNBT());
	}

	protected NBTTagList writeSidesToNBT() {
		NBTTagList list = new NBTTagList();
		for(IPartSide side : getSides()) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			side.writeToNBT(nbtTag);
			list.appendTag(nbtTag);
		}
		return list;
	}

	protected void readSidesFromNBT(NBTTagList list) {
		sides = new IPartSide[6];
		for(int i = 0; i < 6; i++) {
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			sides[i] = new PartSide(EnumFacing.VALUES[i], this);
			sides[i].readFromNBT(nbtTag);
		}
	}

	@Override
	public AdvancedBlockPos getWorldLocation() {
		return new AdvancedBlockPos(tileEntity.getPos());
	}

	@Override
	public void setUnvisited() {
		visited = false;
	}

	@Override
	public void setVisited() {
		visited = true;
	}

	@Override
	public boolean isVisited() {
		return visited;
	}

	@Override
	public IPartSide getSide(EnumFacing direction) {
		return sides[direction.ordinal()];
	}

	@Override
	public IPartSide getSide(int direction) {
		return sides[direction];
	}

	@Override
	public IPartSide[] getSides() {
		return sides;
	}

	@Override
	public void assertDetached() {
		if (this.system != null) {
			Log.info(
					"[assert] Part @ (%d, %d, %d) should be detached already, but detected that it was not. This is not a fatal error, and will be repaired, but is unusual.",
					tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ());
			this.system = null;
		}
	}

	@Override
	public void onAttached(ITransportSystem newSystem) {
		system = newSystem;
	}

	@Override
	public void onDetached(ITransportSystem oldSystem) {
		system = null;
	}
}