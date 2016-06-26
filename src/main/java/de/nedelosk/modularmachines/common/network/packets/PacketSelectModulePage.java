package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSelectModulePage extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketSelectModulePage, IMessage> {

	public int pageID;

	public PacketSelectModulePage() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		pageID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(pageID);
	}

	public PacketSelectModulePage(TileEntity tile, int pageID) {
		super(tile);
		this.pageID = pageID;
	}

	@Override
	public IMessage onMessage(PacketSelectModulePage message, MessageContext ctx) {
		World world;
		if (ctx.side == Side.CLIENT) {
			handleClient(message, ctx);
		} else {
			handleServer(message, ctx);
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	private void handleClient(PacketSelectModulePage message, MessageContext ctx) {
		World world = Minecraft.getMinecraft().theWorld;
		IModularHandler tile = (IModularHandler) message.getTileEntity(world);
		tile.getModular().setCurrentPage(message.pageID);
	}

	private void handleServer(PacketSelectModulePage message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		IModularHandler tile = (IModularHandler) message.getTileEntity(world);
		tile.getModular().setCurrentPage(message.pageID);
		EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
		// PacketHandler.INSTANCE.sendTo(message, entityPlayerMP);
		entityPlayerMP.openGui(ModularMachines.instance, 0, entityPlayerMP.worldObj, message.pos.getX(), message.pos.getY(), message.pos.getZ());
	}
}
