package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import modularmachines.api.modules.network.DataInputStreamMM;
import modularmachines.api.modules.network.DataOutputStreamMM;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.api.modules.state.IModuleStateClient;

public class PacketSyncModule extends PacketModule implements IPacketClient {

	private NBTTagCompound nbt;

	public PacketSyncModule() {
	}

	public PacketSyncModule(IModuleState module) {
		super(module);
		this.nbt = module.serializeNBT();
	}

	@Override
	protected void writeData(DataOutputStreamMM data) throws IOException {
		super.writeData(data);
		data.writeNBTTagCompound(nbt);
	}

	@Override
	public void readData(DataInputStreamMM data) throws IOException {
		super.readData(data);
		nbt = data.readNBTTagCompound();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onPacketData(DataInputStreamMM data, EntityPlayer player) throws IOException {
		IModularHandler handler = getModularHandler(player);
		if (handler == null || handler.getModular() == null) {
			return;
		}
		IModuleState moduleState = getModule(handler);
		moduleState.deserializeNBT(nbt);
		if (moduleState.getModule().needHandlerReload((IModuleStateClient) moduleState)) {
			((IModuleStateClient) moduleState).getModelHandler().setNeedReload(true);
			if (handler instanceof IModularHandlerTileEntity) {
				BlockPos pos = ((IModularHandlerTileEntity) handler).getPos();
				player.worldObj.markBlockRangeForRenderUpdate(pos, pos);
			}
		}
	}

	@Override
	public PacketId getPacketId() {
		return PacketId.SYNC_MODULE;
	}
}
