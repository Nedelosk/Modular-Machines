package de.nedelosk.modularmachines.common.network.packets;

import java.io.IOException;

import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerItem;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modules.network.DataInputStreamMM;
import de.nedelosk.modularmachines.api.modules.network.DataOutputStreamMM;
import de.nedelosk.modularmachines.common.modular.ModularHandlerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class PacketModularHandler extends Packet {

	private Object identifier;

	protected PacketModularHandler() {
	}

	protected PacketModularHandler(IModularHandler handler) {
		if (handler instanceof IModularHandlerItem) {
			this.identifier = ((IModularHandlerItem) handler).getUID();
		} else if (handler instanceof IModularHandlerTileEntity) {
			this.identifier = ((IModularHandlerTileEntity) handler).getPos();
		}
	}

	@Override
	protected void writeData(DataOutputStreamMM data) throws IOException {
		if (identifier instanceof String) {
			data.writeByte(0);
			data.writeUTF((String) identifier);
		} else if (identifier instanceof BlockPos) {
			data.writeByte(1);
			BlockPos pos = (BlockPos) identifier;
			data.writeInt(pos.getX());
			data.writeInt(pos.getY());
			data.writeInt(pos.getZ());
		}
	}

	@Override
	public void readData(DataInputStreamMM data) throws IOException {
		byte handlerType = data.readByte();
		if (handlerType == 0) {
			identifier = data.readUTF();
		} else if (handlerType == 1) {
			int x = data.readInt();
			int y = data.readInt();
			int z = data.readInt();
			identifier = new BlockPos(x, y, z);
		}
	}

	public static BlockPos getPos(IModularHandler modularHandler) {
		if (modularHandler instanceof IModularHandlerItem) {
			IModularHandlerItem handlerItem = (IModularHandlerItem) modularHandler;
			return handlerItem.getPlayerPos();
		} else if (modularHandler instanceof IModularHandlerTileEntity) {
			IModularHandlerTileEntity handlerTile = (IModularHandlerTileEntity) modularHandler;
			return handlerTile.getPos();
		}
		return null;
	}

	public IModularHandler getModularHandler(EntityPlayer player) {
		if (player == null) {
			return null;
		}
		World world = player.worldObj;
		if (identifier instanceof String) {
			String UID = (String) identifier;
			ItemStack stack = null;
			for(EnumHand hand : EnumHand.values()) {
				ItemStack held = player.getHeldItem(hand);
				if (ModularHandlerItem.hasItemUID(held, UID)) {
					stack = held;
					break;
				}
			}
			if (stack != null && stack.hasCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null)) {
				return stack.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			}
		} else if (identifier instanceof BlockPos) {
			BlockPos pos = (BlockPos) identifier;
			TileEntity tile = world.getTileEntity(pos);
			if (tile != null && tile.hasCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null)) {
				return tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			}
		}
		return null;
	}
}
