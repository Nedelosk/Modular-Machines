package de.nedelosk.modularmachines.common.transport.node;

import java.util.List;

import com.google.common.collect.Lists;

import de.nedelosk.forestmods.library.RedstoneMode;
import de.nedelosk.modularmachines.api.transport.node.EnumNodeMode;
import de.nedelosk.modularmachines.api.transport.node.IContentHandler;
import de.nedelosk.modularmachines.api.transport.node.INodeSide;
import de.nedelosk.modularmachines.api.transport.node.ITransportNode;
import de.nedelosk.modularmachines.common.transport.PartSide;
import de.nedelosk.modularmachines.common.utils.AdvancedBlockPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class NodeSide extends PartSide implements INodeSide {

	private int priority;
	private RedstoneMode redstoneMode;
	private EnumNodeMode mode;
	private List<IContentHandler> handlers;

	public NodeSide(EnumFacing side, ITransportNode part) {
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
	public AdvancedBlockPos getSidePos() {
		return new AdvancedBlockPos(part.getTileEntity().getPos().offset(getSide()));
	}

	@Override
	public TileEntity getSideTile() {
		AdvancedBlockPos pos = getSidePos();
		return part.getTileEntity().getWorld().getTileEntity(pos);
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

	@Override
	public <H> IContentHandler<H> getHandler(Class<H> handlerClass) {
		for(IContentHandler handler : handlers){
			if(handler.getHandlerClass() == handlerClass){
				return handler;
			}
		}
		return null;
	}
}
