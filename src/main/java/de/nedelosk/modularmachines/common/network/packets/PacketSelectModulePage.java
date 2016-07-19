package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.handlers.IModularHandler;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSelectModulePage extends PacketModularHandler implements IMessageHandler<PacketSelectModulePage, IMessage> {

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
		IModularHandler tile = message.getModularHandler(ctx);
		tile.getModular().setCurrentPage(message.pageID);
	}

	private void handleServer(PacketSelectModulePage message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
		IModularHandler modularHandler = message.getModularHandler(ctx);
		BlockPos pos = getPos(modularHandler);

		modularHandler.getModular().setCurrentPage(message.pageID);
		entityPlayerMP.openGui(ModularMachines.instance, 0, entityPlayerMP.worldObj, pos.getX(), pos.getY(), pos.getZ());
	}
}
