package nedelosk.modularmachines.api.packets.pages;

import io.netty.buffer.ByteBuf;
import nedelosk.forestcore.library.packets.PacketTileEntity;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.packets.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PacketSelectTankManagerTab extends PacketTileEntity<TileEntity>
		implements IMessageHandler<PacketSelectTankManagerTab, IMessage> {

	public int tabID;

	public PacketSelectTankManagerTab() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		tabID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(tabID);
	}

	public PacketSelectTankManagerTab(TileEntity tile, int tabID) {
		super(tile);
		this.tabID = tabID;
	}

	@Override
	public IMessage onMessage(PacketSelectTankManagerTab message, MessageContext ctx) {
		World world;
		if (ctx.side == Side.CLIENT) {
			world = Minecraft.getMinecraft().theWorld;
		} else {
			world = ctx.getServerHandler().playerEntity.worldObj;
		}
		IModularTileEntity tile = (IModularTileEntity) message.getTileEntity(world);

		tile.getModular().getTankManeger().getProducer().setTab(message.tabID);
		if (ctx.side == Side.CLIENT) {
		} else {
			EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
			PacketHandler.INSTANCE.sendTo(message, entityPlayerMP);
			getWorld(ctx).markBlockForUpdate(message.x, message.y, message.z);
			ModularMachinesApi.handler.openGui(entityPlayerMP, 0, entityPlayerMP.worldObj, message.x, message.y,
					message.z);
		}

		return null;
	}

}
