package nedelosk.modularmachines.api.packets.pages;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import nedelosk.forestcore.library.packets.PacketTileEntity;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.packets.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketSelectPage extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketSelectPage, IMessage> {

	public String page;

	public PacketSelectPage() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		page = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		ByteBufUtils.writeUTF8String(buf, page);
	}

	public PacketSelectPage(TileEntity tile, String page) {
		super(tile);
		this.page = page;
	}

	@Override
	public IMessage onMessage(PacketSelectPage message, MessageContext ctx) {
		World world;
		if (ctx.side == Side.CLIENT) {
			world = Minecraft.getMinecraft().theWorld;
		} else {
			world = ctx.getServerHandler().playerEntity.worldObj;
		}
		IModularTileEntity tile = (IModularTileEntity) message.getTileEntity(world);
		tile.getModular().getGuiManager().setPage(message.page);
		if (ctx.side == Side.CLIENT) {
		} else {
			EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
			PacketHandler.INSTANCE.sendTo(message, entityPlayerMP);
			getWorld(ctx).markBlockForUpdate(message.x, message.y, message.z);
			ModularMachinesApi.handler.openGui(entityPlayerMP, 0, entityPlayerMP.worldObj, message.x, message.y, message.z);
		}
		return null;
	}
}
