package modularmachines.common.network.packets;

import javax.annotation.Nullable;
import java.io.IOException;

import modularmachines.api.modules.IModuleContainer;
import modularmachines.api.modules.Module;
import modularmachines.common.network.PacketBufferMM;

public abstract class PacketModuleProvider extends PacketLocatable<IModuleContainer> {
	
	protected int index;
	protected int positionIndex;
	
	public PacketModuleProvider() {
	}
	
	public PacketModuleProvider(IModuleContainer provider, int index, int positionIndex) {
		super(provider);
		this.index = index;
		this.positionIndex = positionIndex;
	}
	
	@Override
	protected void writeData(PacketBufferMM data) throws IOException {
		super.writeData(data);
		data.writeVarInt(index);
		data.writeVarInt(positionIndex);
	}
	
	@Nullable
	protected static Module getModule(@Nullable IModuleContainer provider, PacketBufferMM data) {
		if (provider == null) {
			return null;
		}
		return provider.getModule(data.readInt());
	}
}
