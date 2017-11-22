package modularmachines.api.components;

import java.io.IOException;

import net.minecraft.network.PacketBuffer;

public interface INetworkComponent {
	void writeData(PacketBuffer data);
	
	void readData(PacketBuffer data) throws IOException;
}
