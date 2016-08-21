package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.storaged.tools.IModuleModeMachine;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.math.BlockPos;

public class PacketSyncMachineMode extends PacketModularHandler {

	public int mode;
	public int index;

	public PacketSyncMachineMode() {
		super();
	}

	public PacketSyncMachineMode(IModularHandler modularHandler, IModuleState<IModuleModeMachine> moduleState) {
		super(modularHandler);
		this.mode = moduleState.getModule().getCurrentMode(moduleState).ordinal();
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
			IModuleState<IModuleModeMachine> machine = modularHandler.getModular().getModule(index);
			if (machine != null) {
				machine.getModule().setCurrentMode(machine, machine.getModule().getMode(mode));
			}
		}
	}

	@Override
	public void handleServerSafe(NetHandlerPlayServer netHandler) {
		IModularHandler modularHandler = getModularHandler(netHandler);
		BlockPos pos = getPos(modularHandler);

		if(modularHandler.getModular() != null && modularHandler.isAssembled()){
			IModuleState<IModuleModeMachine> machine = modularHandler.getModular().getModule(index);
			if (machine != null) {
				machine.getModule().setCurrentMode(machine, machine.getModule().getMode(mode));
			}
		}

		PacketHandler.INSTANCE.sendToAll(this);
	}
}
