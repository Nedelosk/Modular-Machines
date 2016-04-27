package de.nedelosk.forestmods.library.transport.node;

import java.util.List;

import de.nedelosk.forestmods.library.RedstoneMode;
import de.nedelosk.forestmods.library.transport.IPartSide;
import de.nedelosk.forestmods.library.utils.BlockPos;
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
