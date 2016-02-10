package de.nedelosk.forestmods.common.transport.node;

import de.nedelosk.forestmods.api.ForestModsApi;
import de.nedelosk.forestmods.api.RedstoneMode;
import de.nedelosk.forestmods.api.transport.ITransportTileEntity;
import de.nedelosk.forestmods.api.transport.node.INodeSide;
import de.nedelosk.forestmods.api.transport.node.INodeType;
import de.nedelosk.forestmods.api.transport.node.ITransportNode;
import de.nedelosk.forestmods.common.transport.TransportPart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;

public class TransportNode extends TransportPart implements ITransportNode {

	private INodeSide[] sides;
	private INodeType type;
	private RedstoneMode redstoneMode;

	public TransportNode(ITransportTileEntity tileEntity) {
		super(tileEntity);
		this.redstoneMode = RedstoneMode.IGNORE;
	}

	@Override
	public void setType(INodeType type) {
		this.type = type;
	}

	@Override
	public void createSides() {
		this.sides = new INodeSide[6];
		for ( ForgeDirection side : ForgeDirection.VALID_DIRECTIONS ) {
			sides[side.ordinal()] = new NodeSide(side, this);
		}
	}

	@Override
	public void setSide(INodeSide side) {
		this.sides[side.getSide().ordinal()] = side;
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
		super.writeToNBT(nbt);
		nbt.setInteger("RedMode", redstoneMode.ordinal());
		int type;
		if (ForestModsApi.getNodeTypes().indexOf(this.type) == -1) {
			type = 0;
		} else {
			type = ForestModsApi.getNodeTypes().indexOf(this.type);
		}
		nbt.setInteger("NodeType", type);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		redstoneMode = RedstoneMode.values()[nbt.getInteger("RedMode")];
		type = ForestModsApi.getNodeType(nbt.getInteger("NodeType"));
	}

	@Override
	protected void readSidesFromNBT(NBTTagList list) {
		for ( int i = 0; i < 6; i++ ) {
			NBTTagCompound nbtTag = list.getCompoundTagAt(i);
			sides[i] = new NodeSide(ForgeDirection.values()[i], this);
			sides[i].readFromNBT(nbtTag);
		}
	}

	@Override
	public RedstoneMode getRedstoneMode() {
		return redstoneMode;
	}

	@Override
	public void setRedstoneMode(RedstoneMode mode) {
		this.redstoneMode = mode;
	}
}
