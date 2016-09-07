package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.controller.EnumRedstoneMode;
import de.nedelosk.modularmachines.api.modules.controller.IModuleControlled;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class PacketSyncRedstoneMode extends PacketModularHandler {

	public int mode;
	public int index;

	public PacketSyncRedstoneMode() {
		super();
	}

	public PacketSyncRedstoneMode(IModularHandler modularHandler, IModuleState<IModuleControlled> moduleState) {
		super(modularHandler);
		this.mode = moduleState.getModule().getModuleControl(moduleState).getRedstoneMode().ordinal();
		this.index = moduleState.getIndex();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		mode = buf.readInt();
		index = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(mode);
		buf.writeInt(index);
	}

	@Override
	public void handleClientSafe(NetHandlerPlayClient netHandler) {
		IModularHandler modularHandler = getModularHandler(netHandler);
		BlockPos pos = getPos(modularHandler);

		if(modularHandler.getModular() != null && modularHandler.isAssembled()){
			IModuleState<IModuleControlled> moduleState = modularHandler.getModular().getModule(index);
			if (moduleState != null) {
				moduleState.getModule().getModuleControl(moduleState).setRedstoneMode(EnumRedstoneMode.VALUES[mode]);
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
				moduleState.getModule().getModuleControl(moduleState).setRedstoneMode(EnumRedstoneMode.VALUES[mode]);
			}
		}

		PacketHandler.sendToNetwork(this, pos, (WorldServer) netHandler.playerEntity.worldObj);
	}
}
