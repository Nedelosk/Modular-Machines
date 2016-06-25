package de.nedelosk.modularmachines.api.transport.node;

import java.util.List;

import de.nedelosk.forestmods.library.RedstoneMode;
import de.nedelosk.modularmachines.api.transport.IPartSide;
import de.nedelosk.modularmachines.common.utils.AdvancedBlockPos;
import net.minecraft.tileentity.TileEntity;

public interface INodeSide extends IPartSide {

	AdvancedBlockPos getSidePos();

	TileEntity getSideTile();

	boolean isConnected();

	boolean canConnect();

	int getPriority();

	void setPriority(int priority);

	RedstoneMode getRedstoneMode();

	void setRedstoneMode(RedstoneMode mode);

	EnumNodeMode getNodeMode();

	void setNodeMode(EnumNodeMode mode);

	<H> IContentHandler<H> getHandler(Class<H> handlerClass);

	List<IContentHandler> getContentHandlers();
}
