package de.nedelosk.forestmods.api.transport.node;

import java.util.List;

import de.nedelosk.forestcore.utils.BlockPos;
import de.nedelosk.forestmods.api.RedstoneMode;
import de.nedelosk.forestmods.api.transport.IPartSide;
import net.minecraft.tileentity.TileEntity;

public interface INodeSide extends IPartSide {

	BlockPos getSidePos();

	TileEntity getSideTile();

	boolean isConnected();

	boolean canConnect();

	int getPriority();

	void setPriority(int priority);

	RedstoneMode getRedstoneMode();

	void setRedstoneMode(RedstoneMode mode);

	EnumNodeMode getNodeMode();

	void setNodeMode(EnumNodeMode mode);

	List<IContentHandler> getContentHandlers();
}
