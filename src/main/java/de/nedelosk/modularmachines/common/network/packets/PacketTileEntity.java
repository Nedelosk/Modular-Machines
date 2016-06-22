package de.nedelosk.modularmachines.common.network.packets;

import java.nio.charset.StandardCharsets;

import de.nedelosk.modularmachines.common.utils.Log;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTileEntity<T extends TileEntity> implements IMessage {

	protected BlockPos pos;
	private Class<? extends TileEntity> tileClass;

	protected PacketTileEntity() {
	}

	protected PacketTileEntity(T tile) {
		tileClass = tile.getClass();
		pos = tile.getPos();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeInt(tileClass.getName().getBytes().length);
		buf.writeBytes(tileClass.getName().getBytes());
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		pos = new BlockPos(x, y, z);
		String str = null;
		try {
			byte[] bytes = new byte[buf.readInt()];
			buf.readBytes(bytes);
			str = new String(bytes, StandardCharsets.UTF_8);
		} catch (Exception e) {
		}
		try {
			tileClass = (Class<TileEntity>) Class.forName(str);
		} catch (Exception e) {
			Log.err("Not load tile entity class: " + str);
		}
	}

	public T getTileEntity(World worldObj) {
		if (worldObj == null) {
			return null;
		}
		TileEntity te = worldObj.getTileEntity(pos);
		if (te == null) {
			return null;
		}
		if (tileClass.isAssignableFrom(te.getClass())) {
			return (T) te;
		}
		return null;
	}

	protected World getWorld(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity.worldObj;
	}
}
