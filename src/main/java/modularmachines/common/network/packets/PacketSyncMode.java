package modularmachines.common.network.packets;

import java.io.IOException;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.logic.IModuleGuiLogic;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.common.modules.IModuleMode;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.PacketId;
import modularmachines.common.utils.ModuleUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSyncMode extends PacketModule {

	private int mode;

	public PacketSyncMode() {
	}

	public PacketSyncMode(Module module, IModuleMode moduleMode) {
		super(module);
		this.mode = moduleMode.getCurrentMode().ordinal();
	}
	
	public PacketSyncMode(Module module, int ordinal) {
		super(module);
		this.mode = ordinal;
	}

	@Override
	protected void writeData(PacketBufferMM data) throws IOException {
		super.writeData(data);
		data.writeInt(mode);
	}

	public static final class Handler implements IPacketHandlerClient, IPacketHandlerServer{
	
		@SideOnly(Side.CLIENT)
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayer player) throws IOException {
			World world = player.getEntityWorld();
			BlockPos pos = data.readBlockPos();
			IModuleGuiLogic guiLogic = ModuleUtil.getGuiLogic(player);
			if (guiLogic != null) {
				IModuleLogic logic = guiLogic.getLogic();
				int index = data.readVarInt();
				Module module = logic.getModule(index);
				int mode = data.readVarInt();
				if(module instanceof IModuleMode){
					((IModuleMode) module).setCurrentMode(mode);
				}
			}
		}
	
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayerMP player) throws IOException {
			World world = player.getEntityWorld();
			BlockPos pos = data.readBlockPos();
			IModuleGuiLogic guiLogic = ModuleUtil.getGuiLogic(player);
			if (guiLogic != null) {
				IModuleLogic logic = guiLogic.getLogic();
				int index = data.readVarInt();
				Module module = logic.getModule(index);
				int mode = data.readVarInt();
				if(module instanceof IModuleMode){
					((IModuleMode) module).setCurrentMode(mode);
				}
				PacketHandler.sendToNetwork(new PacketSyncMode(module, mode), pos, player.getServerWorld());
			}
		}
	}

	@Override
	public PacketId getPacketId() {
		return PacketId.SYNC_MODE;
	}
}
