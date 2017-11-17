package modularmachines.common.network.packets;

import javax.annotation.Nullable;
import java.io.IOException;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import modularmachines.api.ILocatableSource;
import modularmachines.api.modules.IModuleContainer;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.logic.IModuleLogic;
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
	
	protected static BlockPos getPos(PacketBufferMM data){
		return data.readBlockPos();
	}
	
	@Nullable
	protected static IAssembler getAssembler(PacketBufferMM data, World world){
		return ModuleUtil.getAssembler(getPos(data), world);
	}
	
	@Nullable
	protected static IModuleLogic getLogic(PacketBufferMM data, World world){
		return ModuleUtil.getLogic(getPos(data), world);
	}
	
	@Nullable
	protected static IModuleContainer getProvider(PacketBufferMM data, World world){
		return ModuleUtil.getContainer(getPos(data), world);
	}
}
