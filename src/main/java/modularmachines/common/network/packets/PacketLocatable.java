package modularmachines.common.network.packets;

import javax.annotation.Nullable;
import java.io.IOException;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import modularmachines.api.ILocatableSource;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.utils.ModuleUtil;

public abstract class PacketLocatable<S extends ILocatableSource> extends Packet {
	
	protected S source;
	
	protected PacketLocatable() {
	}
	
	protected PacketLocatable(S source) {
		this.source = source;
	}
	
	@Override
	protected void writeData(PacketBufferMM data) throws IOException {
		data.writeBlockPos(source.getLocatable().getCoordinates());
	}
	
	protected static BlockPos getPos(PacketBufferMM data) {
		return data.readBlockPos();
	}
	
	@Nullable
	protected static IModuleContainer getContainer(PacketBufferMM data, World world) {
		return ModuleUtil.getContainer(getPos(data), world);
	}
}
