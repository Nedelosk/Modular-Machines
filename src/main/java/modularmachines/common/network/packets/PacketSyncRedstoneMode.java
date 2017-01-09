package modularmachines.common.network.packets;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import modularmachines.api.modular.handlers.IModularHandler;
import modularmachines.api.modules.controller.EnumRedstoneMode;
import modularmachines.api.modules.controller.IModuleControlled;
import modularmachines.api.modules.network.DataInputStreamMM;
import modularmachines.api.modules.network.DataOutputStreamMM;
import modularmachines.api.modules.state.IModuleState;
import modularmachines.common.network.PacketHandler;

public class PacketSyncRedstoneMode extends PacketModularHandler implements IPacketClient, IPacketServer {

	private int mode;
	private int position;

	public PacketSyncRedstoneMode() {
		super();
	}

	public PacketSyncRedstoneMode(IModularHandler modularHandler, IModuleState<IModuleControlled> moduleState) {
		super(modularHandler);
		this.mode = moduleState.getModule().getModuleControl(moduleState).getRedstoneMode().ordinal();
		this.position = moduleState.getPosition();
	}

	@Override
	protected void writeData(DataOutputStreamMM data) throws IOException {
		super.writeData(data);
		data.writeInt(mode);
		data.writeInt(position);
	}

	@Override
	public void readData(DataInputStreamMM data) throws IOException {
		super.readData(data);
		mode = data.readInt();
		position = data.readInt();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onPacketData(DataInputStreamMM data, EntityPlayer player) throws IOException {
		IModularHandler modularHandler = getModularHandler(player);
		BlockPos pos = getPos(modularHandler);
		if (modularHandler.getModular() != null && modularHandler.isAssembled()) {
			IModuleState<IModuleControlled> moduleState = modularHandler.getModular().getModule(position);
			if (moduleState != null) {
				moduleState.getModule().getModuleControl(moduleState).setRedstoneMode(EnumRedstoneMode.VALUES[mode]);
			}
		}
	}

	@Override
	public void onPacketData(DataInputStreamMM data, EntityPlayerMP player) throws IOException {
		IModularHandler modularHandler = getModularHandler(player);
		BlockPos pos = getPos(modularHandler);
		if (modularHandler.getModular() != null && modularHandler.isAssembled()) {
			IModuleState<IModuleControlled> moduleState = modularHandler.getModular().getModule(position);
			if (moduleState != null) {
				moduleState.getModule().getModuleControl(moduleState).setRedstoneMode(EnumRedstoneMode.VALUES[mode]);
			}
		}
		PacketHandler.sendToNetwork(this, pos, player.getServerWorld());
	}

	@Override
	public PacketId getPacketId() {
		return PacketId.SYNC_REDSTONE_MODE;
	}
}
