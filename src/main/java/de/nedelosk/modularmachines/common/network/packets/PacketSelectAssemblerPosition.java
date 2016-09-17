package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.IModularAssembler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modules.position.IStoragePosition;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import de.nedelosk.modularmachines.common.inventory.ContainerAssembler;
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

public class PacketSelectAssemblerPosition extends PacketModularHandler {

	public int position;

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		position = buf.readShort();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeShort(position);
	}

	public PacketSelectAssemblerPosition() {
	}

	public PacketSelectAssemblerPosition(IModularHandler handler, IStoragePosition position) {
		super(handler);
		IModularAssembler assembler = handler.getAssembler();
		this.position = assembler.getIndex(position);
	}

	@Override
	public void handleClientSafe(NetHandlerPlayClient netHandler) {
		IModularHandler modularHandler = getModularHandler(netHandler);

		if(modularHandler != null && modularHandler.getAssembler() != null && !modularHandler.isAssembled()){
			IModularAssembler assembler = modularHandler.getAssembler();
			assembler.setSelectedPosition(assembler.getStoragePositions().get(position));
		}
	}

	@Override
	public void handleServerSafe(NetHandlerPlayServer netHandler) {
		IModularHandler modularHandler = getModularHandler(netHandler);
		BlockPos pos = getPos(modularHandler);

		if(modularHandler.getAssembler() != null && !modularHandler.isAssembled()){
			IModularAssembler assembler = modularHandler.getAssembler();
			assembler.setSelectedPosition(assembler.getStoragePositions().get(position));
		}

		PacketHandler.sendToNetwork(this, pos, (WorldServer) netHandler.playerEntity.worldObj);

		WorldServer server = netHandler.playerEntity.getServerWorld();
		for(EntityPlayer otherPlayer : server.playerEntities) {
			if(otherPlayer.openContainer instanceof ContainerAssembler) {
				ContainerAssembler assembler = (ContainerAssembler) otherPlayer.openContainer;
				if(modularHandler == assembler.getHandler()) {
					ItemStack heldStack = null;

					if(otherPlayer.inventory.getItemStack() != null) {
						heldStack = otherPlayer.inventory.getItemStack();
						otherPlayer.inventory.setItemStack(null);
					}
					otherPlayer.openGui(ModularMachines.instance, 0, otherPlayer.worldObj, pos.getX(), pos.getY(), pos.getZ());

					if(heldStack != null) {
						otherPlayer.inventory.setItemStack(heldStack);
						((EntityPlayerMP)otherPlayer).connection.sendPacket(new SPacketSetSlot(-1, -1, heldStack));
					}
				}
			}
		}
	}
}
