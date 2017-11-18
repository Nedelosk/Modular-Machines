package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.PacketId;
import modularmachines.common.utils.Log;

import io.netty.buffer.Unpooled;

public abstract class Packet implements IPacket {
	
	private final PacketId id = getPacketId();
	
	@Override
	public final FMLProxyPacket getPacket() {
		PacketBufferMM data = new PacketBufferMM(Unpooled.buffer());
		
		try {
			data.writeByte(id.ordinal());
			writeData(data);
		} catch (IOException e) {
			Log.err("Failed to write packet.", e);
		}
		
		return new FMLProxyPacket(data, PacketHandler.channelId);
	}
	
	protected abstract void writeData(PacketBufferMM data) throws IOException;
}