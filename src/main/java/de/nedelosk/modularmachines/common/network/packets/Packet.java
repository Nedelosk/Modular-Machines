package de.nedelosk.modularmachines.common.network.packets;

import java.io.IOException;

import de.nedelosk.modularmachines.api.modules.network.DataInputStreamMM;
import de.nedelosk.modularmachines.api.modules.network.DataOutputStreamMM;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import de.nedelosk.modularmachines.common.utils.Log;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

public abstract class Packet implements IPacket {

	private final PacketId id = getPacketId();

	@Override
	public final FMLProxyPacket getPacket() {
		ByteBufOutputStream buf = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStreamMM data = new DataOutputStreamMM(buf);
		try {
			data.writeByte(id.ordinal());
			writeData(data);
		} catch (IOException e) {
			Log.err("Failed to write packet.", e);
		}
		return new FMLProxyPacket(new PacketBuffer(buf.buffer()), PacketHandler.channelId);
	}

	protected void writeData(DataOutputStreamMM data) throws IOException {
	}

	@Override
	public void readData(DataInputStreamMM data) throws IOException {
	}
}