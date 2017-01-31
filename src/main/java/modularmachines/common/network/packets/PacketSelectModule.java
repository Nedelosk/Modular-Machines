package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import modularmachines.api.modules.Module;
import modularmachines.api.modules.logic.IModuleGuiLogic;
import modularmachines.api.modules.logic.IModuleLogic;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.PacketId;
import modularmachines.common.utils.ContainerUtil;
import modularmachines.common.utils.ModuleUtil;

public class PacketSelectModule extends PacketModule {

	public PacketSelectModule() {
	}

	public PacketSelectModule(Module module) {
		super(module);
	}

	@Override
	public PacketId getPacketId() {
		return PacketId.SELECT_MODULE;
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
				guiLogic.setCurrentPage(module.getPage(0), false);
			}
		}
	
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayerMP player) throws IOException {
			WorldServer world = player.getServerWorld();
			BlockPos pos = data.readBlockPos();
			IModuleGuiLogic guiLogic = ModuleUtil.getGuiLogic(player);
			if (guiLogic != null) {
				IModuleLogic logic = guiLogic.getLogic();
				int index = data.readVarInt();
				Module module = logic.getModule(index);
				guiLogic.setCurrentPage(module.getPage(0), false);
				PacketHandler.sendToNetwork(new PacketSelectModulePage(logic, index), pos, world);
				ContainerUtil.openGuiSave(logic);
			}
		}
		
	}
}
