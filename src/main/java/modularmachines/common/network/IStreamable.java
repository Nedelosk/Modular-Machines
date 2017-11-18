package modularmachines.common.network;

import java.io.IOException;

public interface IStreamable {
	
	void writeData(PacketBufferMM data);
	
	void readData(PacketBufferMM data) throws IOException;
	
}
