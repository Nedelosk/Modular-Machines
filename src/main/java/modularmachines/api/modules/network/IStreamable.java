package modularmachines.api.modules.network;

import java.io.IOException;

public interface IStreamable {

	void writeData(DataOutputStreamMM data) throws IOException;

	void readData(DataInputStreamMM data) throws IOException;
}
