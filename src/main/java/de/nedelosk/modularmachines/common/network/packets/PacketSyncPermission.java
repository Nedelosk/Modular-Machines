package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.controller.IModuleControlled;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class PacketSyncPermission extends PacketModularHandler {

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
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		permission = buf.readBoolean();
		moduleIndex = buf.readInt();
		index = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeBoolean(permission);
		buf.writeInt(moduleIndex);
		buf.writeInt(index);
	}

	@Override
	public void handleClientSafe(NetHandlerPlayClient netHandler) {
		IModularHandler modularHandler = getModularHandler(netHandler);
		BlockPos pos = getPos(modularHandler);

		if(modularHandler.getModular() != null && modularHandler.isAssembled()){
			IModuleState<IModuleControlled> moduleState = modularHandler.getModular().getModule(index);
			if (moduleState != null) {
				moduleState.getModule().getModuleControl(moduleState).setPermission(modularHandler.getModular().getModule(moduleIndex), permission);
			}
		}
	}

	@Override
	public void handleServerSafe(NetHandlerPlayServer netHandler) {
		IModularHandler modularHandler = getModularHandler(netHandler);
		BlockPos pos = getPos(modularHandler);

		if(modularHandler.getModular() != null && modularHandler.isAssembled()){
			IModuleState<IModuleControlled> moduleState = modularHandler.getModular().getModule(index);
			if (moduleState != null) {
				moduleState.getModule().getModuleControl(moduleState).setPermission(modularHandler.getModular().getModule(moduleIndex), permission);
			}
		}

		PacketHandler.sendToNetwork(this, pos, (WorldServer) netHandler.playerEntity.worldObj);
	}
}
