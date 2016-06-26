package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.api.modules.tool.IModuleToolAdvanced;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncMachineMode extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketSyncMachineMode, IMessage> {

	public int mode;
	public int index;

	public PacketSyncMachineMode() {
		super();
	}

	public PacketSyncMachineMode(TileEntity tile, IModuleState<IModuleToolAdvanced> moduleState) {
		super(tile);
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
	public IMessage onMessage(PacketSyncMachineMode message, MessageContext ctx) {
		IModularHandler tile = (IModularHandler) message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);
		if (tile.getModular() != null) {
			IModuleState<IModuleToolAdvanced> machine = tile.getModular().getModule(message.index);
			if (machine != null) {
				machine.getModule().setCurrentMode(machine, machine.getModule().getModeClass().getEnumConstants()[message.mode]);
			}
		}
		return null;
	}
}
