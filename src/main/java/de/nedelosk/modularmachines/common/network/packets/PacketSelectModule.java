package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import de.nedelosk.modularmachines.common.inventory.ContainerModular;
import de.nedelosk.modularmachines.common.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class PacketSelectModule extends PacketModularHandler {

	public int index;

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		index = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(index);
	}

	public PacketSelectModule() {
	}

	public PacketSelectModule(IModularHandler handler, IModuleState module) {
		this(handler, module.getIndex());
	}

	public PacketSelectModule(IModularHandler handler, int index) {
		super(handler);
		this.index = index;
	}

	@Override
	public void handleClientSafe(NetHandlerPlayClient netHandler) {
		IModularHandler modularHandler = getModularHandler(netHandler);
		BlockPos pos = getPos(modularHandler);

		if(modularHandler.getModular() != null && modularHandler.isAssembled()){
			modularHandler.getModular().setCurrentModuleState(modularHandler.getModular().getModule(index));
		}
	}

	@Override
	public void handleServerSafe(NetHandlerPlayServer netHandler) {
		IModularHandler modularHandler = getModularHandler(netHandler);
		BlockPos pos = getPos(modularHandler);

		if(modularHandler.getModular() != null && modularHandler.isAssembled()){
			modularHandler.getModular().setCurrentModuleState(modularHandler.getModular().getModule(index));
		}

		PacketHandler.INSTANCE.sendToAll(this);

		// find all people who also have the same gui open and update them too
		WorldServer server = netHandler.playerEntity.getServerWorld();
		for(EntityPlayer otherPlayer : server.playerEntities) {
			if(otherPlayer.openContainer instanceof ContainerModular) {
				ContainerModular assembler = (ContainerModular) otherPlayer.openContainer;
				if(modularHandler == assembler.getHandler()) {
					// same gui, send him an update
					ItemStack heldStack = null;
					if(otherPlayer.inventory.getItemStack() != null) {
						heldStack = otherPlayer.inventory.getItemStack();
						// set it to null so it's not getting dropped
						otherPlayer.inventory.setItemStack(null);
					}
					otherPlayer.openGui(ModularMachines.instance, 0, otherPlayer.worldObj, pos.getX(), pos.getY(), pos.getZ());

					if(heldStack != null) {
						otherPlayer.inventory.setItemStack(heldStack);
						// also send it to the client
						((EntityPlayerMP)otherPlayer).connection.sendPacket(new SPacketSetSlot(-1, -1, heldStack));
					}
				}
			}
		}
	}
}
