package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modular.IModularHandlerItem;
import de.nedelosk.modularmachines.api.modular.IModularHandlerTileEntity;
import de.nedelosk.modularmachines.api.modular.ModularManager;
import de.nedelosk.modularmachines.common.modular.handlers.ModularHandlerItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketModularHandler implements IMessage {

	private Object identifier;

	protected PacketModularHandler() {
	}

	protected PacketModularHandler(IModularHandler handler) {
		if(handler instanceof IModularHandlerItem){
			this.identifier = ((IModularHandlerItem) handler).getUID();
		}else if(handler instanceof IModularHandlerTileEntity){
			this.identifier = ((IModularHandlerTileEntity) handler).getPos();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		if(identifier instanceof String){
			buf.writeByte(0);
			ByteBufUtils.writeUTF8String(buf, (String) identifier);
		}else if(identifier instanceof BlockPos){
			buf.writeByte(1);
			BlockPos pos = (BlockPos) identifier;
			buf.writeInt(pos.getX());
			buf.writeInt(pos.getY());
			buf.writeInt(pos.getZ());
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		byte handlerType = buf.readByte();
		if(handlerType == 0){
			identifier = ByteBufUtils.readUTF8String(buf);

		}else if( handlerType == 1){
			int x = buf.readInt();
			int y = buf.readInt();
			int z = buf.readInt();
			identifier = new BlockPos(x, y, z);
		}
	}

	public static BlockPos getPos(IModularHandler modularHandler){
		if(modularHandler instanceof IModularHandlerItem){
			IModularHandlerItem handlerItem = (IModularHandlerItem) modularHandler;
			return handlerItem.getPlayerPos();
		}else if(modularHandler instanceof IModularHandlerTileEntity){
			IModularHandlerTileEntity handlerTile = (IModularHandlerTileEntity) modularHandler;
			return handlerTile.getPos();
		}
		return null;
	}

	public IModularHandler getModularHandler(MessageContext ctx) {
		EntityPlayer player;
		if(ctx.side == Side.CLIENT){
			player = Minecraft.getMinecraft().thePlayer;
		}else{
			player = ctx.getServerHandler().playerEntity;
		}
		World world = player.worldObj;
		if(identifier instanceof String){
			String UID = (String) identifier;
			ItemStack stack = null;
			for (EnumHand hand : EnumHand.values()) {
				ItemStack held = player.getHeldItem(hand);
				if (ModularHandlerItem.hasItemUID(held, UID)) {
					stack = held;
					break;
				}
			}
			if(stack != null && stack.hasCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null)){
				return stack.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			}
		}else if(identifier instanceof BlockPos){
			BlockPos pos = (BlockPos) identifier;
			TileEntity tile = world.getTileEntity(pos);
			if(tile != null && tile.hasCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null)){
				return tile.getCapability(ModularManager.MODULAR_HANDLER_CAPABILITY, null);
			}
		}
		return null;
	}
}
