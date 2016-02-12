package de.nedelosk.forestmods.common.transport.node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.nedelosk.forestcore.utils.BlockPos;
import de.nedelosk.forestcore.utils.Log;
import de.nedelosk.forestmods.api.ForestModsApi;
import de.nedelosk.forestmods.api.transport.IPartSide;
import de.nedelosk.forestmods.api.transport.ITransportPart;
import de.nedelosk.forestmods.api.transport.ITransportSystem;
import de.nedelosk.forestmods.api.transport.ITransportTileEntity;
import de.nedelosk.forestmods.api.transport.node.INodeSide;
import de.nedelosk.forestmods.api.transport.node.INodeType;
import de.nedelosk.forestmods.api.transport.node.ITransportNode;
import de.nedelosk.forestmods.common.transport.TransportSystem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.util.ForgeDirection;

public class TransportNode implements ITransportNode {

	private INodeSide[] sides;
	private INodeType type;
	private int timer;
	protected final ITransportTileEntity tileEntity;
	protected ITransportSystem system;
	private boolean visited;

	public TransportNode(ITransportTileEntity tileEntity) {
		this.tileEntity = tileEntity;
		visited = false;
	}

	@Override
	public void update() {
		for ( INodeSide side : sides ) {
		}
	}

	@Override
	public void setType(INodeType type) {
		this.type = type;
	}

	@Override
	public void createSides() {
		setType(ForestModsApi.getNodeType(tileEntity.getBlockMetadata() - 1));
		this.sides = new INodeSide[6];
		for ( ForgeDirection side : ForgeDirection.VALID_DIRECTIONS ) {
			sides[side.ordinal()] = new NodeSide(side, this);
		}
	}

	@Override
	public INodeSide getSide(ForgeDirection direction) {
		return sides[direction.ordinal()];
	}

	@Override
	public INodeSide getSide(int direction) {
		return sides[direction];
	}

	@Override
	public INodeSide[] getSides() {
		return sides;
	}

	@Override
	public INodeType getType() {
		return type;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		int type;
		if (ForestModsApi.getNodeTypes().indexOf(this.type) == -1) {
			type = 0;
		} else {
			type = ForestModsApi.getNodeTypes().indexOf(this.type);
		}
		nbt.setInteger("NodeType", type);
		nbt.setInteger("Timer", timer);
		nbt.setTag("Sides", writeSidesToNBT());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		type = ForestModsApi.getNodeType(nbt.getInteger("NodeType"));
		timer = nbt.getInteger("Timer");
		readSidesFromNBT(nbt.getTagList("Sides", 10));
	}

	protected void readSidesFromNBT(NBTTagList list) {
		sides = new INodeSide[6];
		for ( int i = 0; i < 6; i++ ) {
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			sides[i] = new NodeSide(ForgeDirection.values()[i], this);
			sides[i].readFromNBT(nbtTag);
		}
	}

	@Override
	public ITransportSystem getSystem() {
		return system;
	}

	@Override
	public ITransportSystem createNewSystem() {
		return new TransportSystem(tileEntity.getWorldObj());
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
		for ( ITransportPart neighborPart : partsToCheck ) {
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
		int xCoord = tileEntity.xCoord;
		int yCoord = tileEntity.yCoord;
		int zCoord = tileEntity.zCoord;
		TileEntity te;
		List<ITransportPart> neighborParts = new ArrayList<ITransportPart>();
		IChunkProvider chunkProvider = tileEntity.getWorldObj().getChunkProvider();
		testSide : for ( IPartSide side : getSides() ) {
			if (side.isActive()) {
				for ( int i = 1; i < 9; i++ ) {
					int x = xCoord + (side.getSide().offsetX * i);
					int y = yCoord + (side.getSide().offsetY * i);
					int z = zCoord + (side.getSide().offsetZ * i);
					if (!chunkProvider.chunkExists(x >> 4, z >> 4)) {
						continue testSide;
					}
					te = tileEntity.getWorldObj().getTileEntity(x, y, z);
					int opposites = ForgeDirection.OPPOSITES[side.getSide().ordinal()];
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

	protected NBTTagList writeSidesToNBT() {
		NBTTagList list = new NBTTagList();
		for ( IPartSide side : getSides() ) {
			NBTTagCompound nbtTag = new NBTTagCompound();
			side.writeToNBT(nbtTag);
			list.appendTag(nbtTag);
		}
		return list;
	}

	@Override
	public BlockPos getWorldLocation() {
		return new BlockPos(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
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
	public void assertDetached() {
		if (this.system != null) {
			Log.info(
					"[assert] Part @ (%d, %d, %d) should be detached already, but detected that it was not. This is not a fatal error, and will be repaired, but is unusual.",
					tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
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
