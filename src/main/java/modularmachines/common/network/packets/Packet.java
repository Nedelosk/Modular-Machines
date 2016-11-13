package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import modularmachines.api.modules.network.DataInputStreamMM;
import modularmachines.api.modules.network.DataOutputStreamMM;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.utils.Log;

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