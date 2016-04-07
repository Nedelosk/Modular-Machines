package de.nedelosk.forestmods.common.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.nedelosk.forestcore.network.PacketTileEntity;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.IModuleAdvancedWithMode;
import de.nedelosk.forestmods.api.recipes.IMachineMode;
import de.nedelosk.forestmods.api.utils.ModularUtils;
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
				IModuleAdvancedWithMode machine = ModularUtils.<IModuleAdvancedWithMode> getMachine(tile.getModular()).getModule();
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
