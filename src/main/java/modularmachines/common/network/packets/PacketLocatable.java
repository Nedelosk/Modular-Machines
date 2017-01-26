package modularmachines.common.network.packets;

import java.io.IOException;

import javax.annotation.Nullable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import modularmachines.api.ILocatable;
import modularmachines.api.ILocatableSource;
import modularmachines.api.modules.IModuleLogic;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.utils.ModuleUtils;

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
	
	protected static BlockPos getPos(PacketBufferMM data){
		return data.readBlockPos();
	}
	
	@Nullable
	protected static IAssembler getAssembler(PacketBufferMM data, World world){
		return ModuleUtils.getAssembler(getPos(data), world);
	}
	
	@Nullable
	protected static IModuleLogic getLogic(PacketBufferMM data, World world){
		return ModuleUtils.getLogic(getPos(data), world);
	}
}
