package de.nedelosk.modularmachines.common.network.packets;

import java.io.IOException;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.controller.IModuleControlled;
import de.nedelosk.modularmachines.api.modules.network.DataInputStreamMM;
import de.nedelosk.modularmachines.api.modules.network.DataOutputStreamMM;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSyncPermission extends PacketModularHandler implements IPacketClient, IPacketServer {

	public boolean permission;
	public int moduleIndex;
	public int index;

	public PacketSyncPermission() {
		super();
	}

	public PacketSyncPermission(IModularHandler modularHandler, IModuleState<IModuleControlled> moduleState, IModuleState state) {
		super(modularHandler);
		this.moduleIndex = state.getIndex();
		this.permission = moduleState.getModule().getModuleControl(moduleState).hasPermission(state);
		this.index = moduleState.getIndex();
	}

	@Override
	public void readData(DataInputStreamMM data) throws IOException {
		super.readData(data);
		permission = data.readBoolean();
		moduleIndex = data.readInt();
		index = data.readInt();
	}

	@Override
	protected void writeData(DataOutputStreamMM data) throws IOException {
		super.writeData(data);
		data.writeBoolean(permission);
		data.writeInt(moduleIndex);
		data.writeInt(index);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onPacketData(DataInputStreamMM data, EntityPlayer player) throws IOException {
		IModularHandler modularHandler = getModularHandler(player);
		BlockPos pos = getPos(modularHandler);
		if (modularHandler.getModular() != null && modularHandler.isAssembled()) {
			IModuleState<IModuleControlled> moduleState = modularHandler.getModular().getModule(index);
			if (moduleState != null) {
				moduleState.getModule().getModuleControl(moduleState).setPermission(modularHandler.getModular().getModule(moduleIndex), permission);
			}
		}
	}

	@Override
	public void onPacketData(DataInputStreamMM data, EntityPlayerMP player) throws IOException {
		IModularHandler modularHandler = getModularHandler(player);
		BlockPos pos = getPos(modularHandler);
		if (modularHandler.getModular() != null && modularHandler.isAssembled()) {
			IModuleState<IModuleControlled> moduleState = modularHandler.getModular().getModule(index);
			if (moduleState != null) {
				moduleState.getModule().getModuleControl(moduleState).setPermission(modularHandler.getModular().getModule(moduleIndex), permission);
			}
		}
		PacketHandler.sendToNetwork(this, pos, player.getServerWorld());
	}

	@Override
	public PacketId getPacketId() {
		return PacketId.SYNC_PERMISSON;
	}
}
