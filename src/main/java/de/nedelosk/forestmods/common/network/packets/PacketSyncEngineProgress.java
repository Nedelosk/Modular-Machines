package de.nedelosk.forestmods.common.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.engine.IModuleEngineSaver;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketSyncEngineProgress implements IMessage, IMessageHandler<PacketSyncEngineProgress, IMessage> {

	public float progress;
	protected int x;
	protected int y;
	protected int z;

	public PacketSyncEngineProgress() {
	}

	public PacketSyncEngineProgress(IModularTileEntity modular, float progress) {
		x = modular.getXCoord();
		y = modular.getYCoord();
		z = modular.getZCoord();
		this.progress = progress;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeFloat(progress);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		progress = buf.readFloat();
	}

	public IModularTileEntity getTileEntity(World worldObj) {
		if (worldObj == null) {
			return null;
		}
		TileEntity te = worldObj.getTileEntity(x, y, z);
		if (te == null) {
			return null;
		}
		return (IModularTileEntity) te;
	}

	protected World getWorld(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity.worldObj;
	}

	@Override
	public IMessage onMessage(PacketSyncEngineProgress message, MessageContext ctx) {
		IModularTileEntity modular = message.getTileEntity(getWorld(ctx));
		if (modular != null) {
			((IModuleEngineSaver) ModularUtils.getEngine(modular.getModular()).getStack().getSaver()).setProgress(message.progress);
		}
		return null;
	}
}
