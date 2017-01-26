package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import modularmachines.api.modules.assemblers.IAssembler;
import modularmachines.api.modules.storages.IStoragePosition;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.PacketId;
import modularmachines.common.utils.ContainerUtil;
import modularmachines.common.utils.ModuleUtils;

public class PacketAssemblerPosition extends PacketLocatable {
	
	public int position;
	
	public PacketAssemblerPosition() {
	}

	public PacketAssemblerPosition(IAssembler assembler, IStoragePosition position) {
		super(assembler);
		this.position = assembler.getIndex(position);
	}
	
	private PacketAssemblerPosition(IAssembler assembler, int position) {
		super(assembler);
		this.position = position;
	}
	
	@Override
	protected void writeData(PacketBufferMM data) throws IOException {
		super.writeData(data);
		data.writeShort(position);
	}
	
	@Override
	public PacketId getPacketId() {
		return PacketId.ASSEMBLER_POSITION;
	}

	public static final class Handler implements IPacketHandlerClient, IPacketHandlerServer{

		@SideOnly(Side.CLIENT)
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayer player) throws IOException {
			IAssembler assembler = getAssembler(data, player.world);
			short positionIndex = data.readShort();
			if (assembler != null) {
				assembler.setSelectedPosition(assembler.getPosition(positionIndex));
			}
		}

		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayerMP player) throws IOException {
			BlockPos pos = getPos(data);
			IAssembler assembler = ModuleUtils.getAssembler(pos, player.world);
			short positionIndex = data.readShort();
			if (assembler != null) {
				assembler.setSelectedPosition(assembler.getPosition(positionIndex));
			}
			PacketHandler.sendToNetwork(new PacketAssemblerPosition(assembler, positionIndex), pos, player.getServerWorld());
			ContainerUtil.openGuiSave(assembler);
		}
		
	}
}
