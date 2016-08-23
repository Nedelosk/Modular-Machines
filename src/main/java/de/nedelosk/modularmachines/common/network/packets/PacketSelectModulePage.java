package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
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
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class PacketSelectModulePage extends PacketModularHandler {

	public String pageID;

	public PacketSelectModulePage() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		pageID = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		ByteBufUtils.writeUTF8String(buf, pageID);
	}

	public PacketSelectModulePage(IModularHandler handler, String pageID) {
		super(handler);
		this.pageID = pageID;
	}

	@Override
	public void handleClientSafe(NetHandlerPlayClient netHandler) {
		IModularHandler modularHandler = getModularHandler(netHandler);
		BlockPos pos = getPos(modularHandler);

		if(modularHandler.getModular() != null && modularHandler.isAssembled()){
			modularHandler.getModular().setCurrentPage(pageID);
		}
	}

	@Override
	public void handleServerSafe(NetHandlerPlayServer netHandler) {
		IModularHandler modularHandler = getModularHandler(netHandler);
		BlockPos pos = getPos(modularHandler);

		if(modularHandler.getModular() != null && modularHandler.isAssembled()){
			modularHandler.getModular().setCurrentPage(pageID);
		}

		PacketHandler.sendToNetwork(this, pos, (WorldServer) netHandler.playerEntity.worldObj);

		WorldServer server = netHandler.playerEntity.getServerWorld();
		for(EntityPlayer otherPlayer : server.playerEntities) {
			if(otherPlayer.openContainer instanceof ContainerModular) {
				ContainerModular container = (ContainerModular) otherPlayer.openContainer;
				if(modularHandler == container.getHandler()) {
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
