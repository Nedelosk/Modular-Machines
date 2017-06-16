package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.common.modules.transfer.ModuleTransfer;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.PacketId;
import modularmachines.common.utils.ContainerUtil;
import modularmachines.common.utils.ModuleUtil;

public class PacketRemoveCycle extends PacketModule {

	protected int index;
	
	public PacketRemoveCycle() {
	}
	
	public PacketRemoveCycle(ModuleTransfer module, int index) {
		super(module);
		this.index = index;
	}
	
	@Override
	protected void writeData(PacketBufferMM data) throws IOException {
		super.writeData(data);
		data.writeInt(index);
	}
	
	@Override
	public PacketId getPacketId() {
		return PacketId.REMOVE_CYCLE;
	}
	
	public static final class Handler implements IPacketHandlerClient, IPacketHandlerServer{
		
		@SideOnly(Side.CLIENT)
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayer player) throws IOException {
			World world = player.world;
			IModuleLogic logic = PacketLocatable.getLogic(data, world);
			Module module = getModule(logic, data);
			//page index
			int page = data.readInt();
			if(module instanceof ModuleTransfer){
				ModuleTransfer transfer = (ModuleTransfer) module;
				transfer.getTransferCycles().remove(data.readInt());
			}
		}
	
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayerMP player) throws IOException {
			World world = player.getEntityWorld();
			BlockPos pos = data.readBlockPos();
			IModuleLogic logic = ModuleUtil.getLogic(pos, world);
			Module module = getModule(logic, data);
			//page index
			int page = data.readInt();
			if(module instanceof ModuleTransfer){
				ModuleTransfer transfer = (ModuleTransfer) module;
				int index = data.readInt();
				transfer.getTransferCycles().remove(index);
				PacketHandler.sendToNetwork(new PacketRemoveCycle(transfer, index), pos, player.getServerWorld());
				ContainerUtil.openGuiSave(logic, 1);
			}
		}
	}

}
