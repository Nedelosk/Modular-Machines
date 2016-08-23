package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.AssemblerException;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.api.modular.handlers.IModularHandlerTileEntity;
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

public class PacketSyncAssembler extends PacketModularHandler {

	private boolean isAssembled;

	public PacketSyncAssembler() {
	}

	public PacketSyncAssembler(IModularHandler handler, boolean isAssembled) {
		super(handler);
		this.isAssembled = isAssembled;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		isAssembled = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeBoolean(isAssembled);
	}

	@Override
	public void handleClientSafe(NetHandlerPlayClient netHandler) {
		IModularHandler handler = getModularHandler(netHandler);
		if(handler != null && handler.getModular() != null){
			handler.setAssembled(isAssembled);
			if(isAssembled){
				try {
					handler.setModular(handler.getAssembler().assemble());
				} catch (AssemblerException e) {
					e.printStackTrace();
				}
				handler.setAssembler(null);
			}else{
				handler.setAssembler(handler.getModular().disassemble());
				handler.setModular(null);
			}
		}
	}

	@Override
	public void handleServerSafe(NetHandlerPlayServer netHandler) {
		IModularHandler handler = getModularHandler(netHandler);
		handler.setAssembled(isAssembled);
		if(isAssembled){
			try {
				handler.setModular(handler.getAssembler().assemble());
			} catch (AssemblerException e) {
				e.printStackTrace();
			}
			handler.setAssembler(null);
		}else{
			handler.setAssembler(handler.getModular().disassemble());
			handler.setModular(null);
		}

		PacketHandler.sendToNetwork(this, ((IModularHandlerTileEntity)handler).getPos(), (WorldServer) netHandler.playerEntity.worldObj);
		WorldServer server = netHandler.playerEntity.getServerWorld();
		BlockPos pos = getPos(handler);
		server.notifyBlockUpdate(pos, server.getBlockState(pos), server.getBlockState(pos), 3);

		for(EntityPlayer otherPlayer : server.playerEntities) {
			if(otherPlayer.openContainer instanceof ContainerAssembler) {
				ContainerAssembler assembler = (ContainerAssembler) otherPlayer.openContainer;
				if(handler == assembler.getHandler()) {
					ItemStack heldStack = null;

					if(handler.getModular() != null && handler.getModular().getCurrentModuleState() == null){
						otherPlayer.closeScreen();
					}else{
						if(otherPlayer.inventory.getItemStack() != null) {
							heldStack = otherPlayer.inventory.getItemStack();
							otherPlayer.inventory.setItemStack(null);
						}
						otherPlayer.openGui(ModularMachines.instance, 0, otherPlayer.worldObj, pos.getX(), pos.getY(), pos.getZ());
					}

					if(heldStack != null) {
						otherPlayer.inventory.setItemStack(heldStack);
						((EntityPlayerMP)otherPlayer).connection.sendPacket(new SPacketSetSlot(-1, -1, heldStack));
					}
				}
			}
		}
	}

}
