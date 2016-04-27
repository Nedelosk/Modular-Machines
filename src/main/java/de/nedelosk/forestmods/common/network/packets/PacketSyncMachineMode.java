package de.nedelosk.forestmods.common.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modules.IModuleMachineWithMode;
import de.nedelosk.forestmods.library.network.PacketTileEntity;
import de.nedelosk.forestmods.library.recipes.IMachineMode;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

public class PacketSyncMachineMode extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketSyncMachineMode, IMessage> {

	public int mode;

	public PacketSyncMachineMode() {
		super();
	}

	public PacketSyncMachineMode(TileEntity tile, IMachineMode mode) {
		super(tile);
		this.mode = mode.ordinal();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		mode = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(mode);
	}

	@Override
	public IMessage onMessage(PacketSyncMachineMode message, MessageContext ctx) {
		try {
			IModularTileEntity tile = (IModularTileEntity) message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);
			if (tile.getModular() != null) {
				IModuleMachineWithMode machine = tile.getModular().getModules(IModuleMachineWithMode.class).get(0);
				if (machine != null) {
					machine.setCurrentMode(machine.getModeClass().getEnumConstants()[message.mode]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
