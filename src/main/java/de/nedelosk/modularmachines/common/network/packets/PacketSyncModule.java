package de.nedelosk.modularmachines.common.network.packets;

import java.io.IOException;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.network.DataInputStreamMM;
import de.nedelosk.modularmachines.api.modules.network.DataOutputStreamMM;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.state.IModuleStateClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSyncModule extends PacketModule implements IPacketClient{

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
		IModuleState moduleState = handler.getModular().getModule(index);
		moduleState.deserializeNBT(nbt);

		if(moduleState.getModule().needHandlerReload((IModuleStateClient) moduleState)){
			((IModuleStateClient)moduleState).getModelHandler().setNeedReload(true);
			if(handler instanceof IModularHandlerTileEntity){
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
