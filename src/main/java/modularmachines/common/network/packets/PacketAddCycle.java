package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.common.modules.transfer.ITransferCycle;
import modularmachines.common.modules.transfer.ModuleTransfer;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketHandler;
import modularmachines.common.network.PacketId;
import modularmachines.common.utils.ContainerUtil;
import modularmachines.common.utils.ModuleUtil;

public class PacketAddCycle extends PacketModule {
	
	protected ITransferCycle cycle;
	
	public PacketAddCycle() {
	}
	
	public PacketAddCycle(ModuleTransfer module, ITransferCycle cycle) {
		super(module);
		this.cycle = cycle;
	}
	
	@Override
	protected void writeData(PacketBufferMM data) throws IOException {
		super.writeData(data);
		data.writeCompoundTag(cycle.writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	public PacketId getPacketId() {
		return PacketId.ADD_CYCLE;
	}
	
	public static final class Handler implements IPacketHandlerClient, IPacketHandlerServer {
		
		@SideOnly(Side.CLIENT)
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayer player) throws IOException {
			World world = player.world;
			IModuleContainer logic = PacketLocatable.getContainer(data, world);
			Module module = getModule(logic, data);
			//page index
			int page = data.readInt();
			if (module instanceof ModuleTransfer) {
				ModuleTransfer transfer = (ModuleTransfer) module;
				transfer.addCycle(transfer.getCycle(data.readCompoundTag()));
			}
		}
		
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayerMP player) throws IOException {
			World world = player.getEntityWorld();
			BlockPos pos = data.readBlockPos();
			IModuleContainer logic = ModuleUtil.getContainer(pos, world);
			Module module = getModule(logic, data);
			//page index
			int page = data.readInt();
			if (module instanceof ModuleTransfer) {
				ModuleTransfer transfer = (ModuleTransfer) module;
				ITransferCycle cycle = transfer.getCycle(data.readCompoundTag());
				transfer.addCycle(cycle);
				PacketHandler.sendToNetwork(new PacketAddCycle(transfer, cycle), pos, player.getServerWorld());
				ContainerUtil.openGuiSave(logic, 1);
			}
		}
	}
	
}
