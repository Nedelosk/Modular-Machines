package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import modularmachines.api.modules.IModuleGuiLogic;
import modularmachines.api.modules.IModuleLogic;
import modularmachines.api.modules.Module;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.PacketId;
import modularmachines.common.utils.ContainerUtil;
import modularmachines.common.utils.ModuleUtil;

public class PacketSelectModulePage extends PacketModule {

	public PacketSelectModulePage() {
	}

	public PacketSelectModulePage(IModuleLogic logic, int pageIndex) {
		super(logic, -1, pageIndex);
	}

	@Override
	public PacketId getPacketId() {
		return PacketId.SELECT_PAGE;
	}
	
	public static final class Handler implements IPacketHandlerClient, IPacketHandlerServer{
	
		@SideOnly(Side.CLIENT)
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayer player) throws IOException {
			World world = player.getEntityWorld();
			BlockPos pos = data.readBlockPos();
			IModuleGuiLogic guiLogic = ModuleUtil.getGuiLogic(player);
			if (guiLogic != null) {
				Module module = guiLogic.getCurrentModule();
				int pageIndex = data.readVarInt();
				guiLogic.setCurrentPage(module.getPage(pageIndex), false);
			}
		}
	
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayerMP player) throws IOException {
			WorldServer world = player.getServerWorld();
			BlockPos pos = data.readBlockPos();
			IModuleGuiLogic guiLogic = ModuleUtil.getGuiLogic(player);
			if (guiLogic != null) {
				IModuleLogic logic = guiLogic.getLogic();
				Module module = guiLogic.getCurrentModule();
				int pageIndex = data.readVarInt();
				guiLogic.setCurrentPage(module.getPage(pageIndex), false);
				PacketHandler.sendToNetwork(new PacketSelectModulePage(logic, pageIndex), pos, world);
				ContainerUtil.openGuiSave(logic);
			}
		}
	}

}
