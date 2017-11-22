package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.common.network.PacketBufferMM;
import modularmachines.common.network.PacketId;

public class PacketSyncModule extends PacketModule {
	
	private NBTTagCompound nbt;
	
	public PacketSyncModule() {
	}
	
	public PacketSyncModule(IModule module) {
		super(module);
		this.nbt = module.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	protected void writeData(PacketBufferMM data) throws IOException {
		super.writeData(data);
		data.writeCompoundTag(nbt);
	}
	
	@Override
	public PacketId getPacketId() {
		return PacketId.SYNC_MODULE;
	}
	
	public static class Handler implements IPacketHandlerClient {
		
		@SideOnly(Side.CLIENT)
		@Override
		public void onPacketData(PacketBufferMM data, EntityPlayer player) throws IOException {
			World world = player.world;
			IModuleContainer provider = PacketLocatable.getContainer(data, world);
			IModule module = getModule(provider, data);
			//page index
			int page = data.readInt();
			module.readFromNBT(data.readCompoundTag());
			//TODO: MODEL SYSTEM
			/*if (moduleState.getModule().needHandlerReload((IModuleStateClient) moduleState)) {
				((IModuleStateClient) moduleState).getModelHandler().setNeedReload(true);
				if (handler instanceof IModularHandlerTileEntity) {
					BlockPos pos = ((IModularHandlerTileEntity) handler).getPos();
					player.worldObj.markBlockRangeForRenderUpdate(pos, pos);
				}
			}*/
		}
		
	}
}
