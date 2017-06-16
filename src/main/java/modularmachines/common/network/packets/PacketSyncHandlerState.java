package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.ILocatableSource;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketId;
import modularmachines.common.utils.ModuleUtil;

public class PacketSyncHandlerState extends PacketLocatable{

	private boolean isAssembled;

	public PacketSyncHandlerState() {
	}

	public PacketSyncHandlerState(ILocatableSource source, boolean isAssembled) {
		super(source);
		this.isAssembled = isAssembled;
	}

	@Override
	protected void writeData(PacketBufferMM data) throws IOException {
		super.writeData(data);
		data.writeBoolean(isAssembled);
	}
	
	@Override
	public PacketId getPacketId() {
		return PacketId.SYNC_HANDLER_STATE;
	}
	
	public static final class Handler implements IPacketHandlerClient, IPacketHandlerServer{
	
		@SideOnly(Side.CLIENT)
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayer player) throws IOException {
			BlockPos pos = data.readBlockPos();
			World world = player.world;
			IModuleLogic logic = ModuleUtil.getLogic(pos, world);
			IAssembler assembler = ModuleUtil.getAssembler(pos, world);
			boolean assembled = data.readBoolean();
			if(assembler != null && logic != null){
				if (assembled) {
					logic.assemble(assembler, player);
				}else {
					assembler.disassemble(logic, player);
				}
			}
		}
	
		@Override
		public void onPacketData(PacketBufferMM  data, EntityPlayerMP player) throws IOException {
			BlockPos pos = data.readBlockPos();
			World world = player.world;
			IModuleLogic logic = ModuleUtil.getLogic(pos, world);
			IAssembler assembler = ModuleUtil.getAssembler(pos, world);
			boolean assembled = data.readBoolean();
			if(assembler != null && logic != null){
				if (assembled) {
					logic.assemble(assembler, player);
				}else{
					assembler.disassemble(logic, player);
				}
			}
		}
	}
}
