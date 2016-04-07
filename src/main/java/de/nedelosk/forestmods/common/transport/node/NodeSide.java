package de.nedelosk.forestmods.common.transport.node;

import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.forestcore.utils.BlockPos;
import de.nedelosk.forestmods.api.RedstoneMode;
import de.nedelosk.forestmods.api.transport.node.EnumNodeMode;
import de.nedelosk.forestmods.api.transport.node.IContentHandler;
import de.nedelosk.forestmods.api.transport.node.INodeSide;
import de.nedelosk.forestmods.api.transport.node.ITransportNode;
import de.nedelosk.forestmods.common.transport.PartSide;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class NodeSide extends PartSide implements INodeSide {

	private int priority;
	private RedstoneMode redstoneMode;
	private EnumNodeMode mode;
	private List<IContentHandler> handlers;

	public NodeSide(ForgeDirection side, ITransportNode part) {
		super(side, part);
		this.redstoneMode = RedstoneMode.IGNORE;
		this.mode = EnumNodeMode.NONE;
		this.handlers = Lists.newArrayList();
		for(Class<? extends IContentHandler> handlerClass : part.getType().getHandlers(this)) {
			try {
				handlers.add(handlerClass.getConstructor(ITransportNode.class).newInstance(part));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public BlockPos getSidePos() {
		return new BlockPos(part.getTileEntity().xCoord + side.offsetX, part.getTileEntity().yCoord + side.offsetY, part.getTileEntity().zCoord + side.offsetZ);
	}

	@Override
	public TileEntity getSideTile() {
		BlockPos pos = getSidePos();
		return part.getTileEntity().getWorldObj().getTileEntity(pos.x, pos.y, pos.z);
	}

	@Override
	public boolean isConnected() {
		return mode == EnumNodeMode.CONNECTED;
	}

	@Override
	public boolean canConnect() {
		for(IContentHandler handler : getContentHandlers()) {
			if (handler.canConnectToSide(this)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("RedMode", redstoneMode.ordinal());
		compound.setInteger("Mode", mode.ordinal());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		redstoneMode = RedstoneMode.values()[compound.getInteger("RedMode")];
		mode = EnumNodeMode.values()[compound.getInteger("Mode")];
	}

	@Override
	public RedstoneMode getRedstoneMode() {
		return redstoneMode;
	}

	@Override
	public void setRedstoneMode(RedstoneMode mode) {
		this.redstoneMode = mode;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public EnumNodeMode getNodeMode() {
		return mode;
	}

	@Override
	public boolean isActive() {
		return mode == EnumNodeMode.LASER;
	}

	@Override
	public void setNodeMode(EnumNodeMode mode) {
		this.mode = mode;
	}

	@Override
	public List<IContentHandler> getContentHandlers() {
		return handlers;
	}
}
