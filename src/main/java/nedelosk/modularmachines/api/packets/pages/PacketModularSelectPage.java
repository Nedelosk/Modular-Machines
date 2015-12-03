package nedelosk.modularmachines.api.packets.pages;

import io.netty.buffer.ByteBuf;
import nedelosk.forestday.api.packets.PacketTileEntity;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketModularSelectPage extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketModularSelectPage, IMessage> {

	public String page;

	public PacketModularSelectPage() {
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

	public PacketModularSelectPage(TileEntity tile, String page) {
		super(tile);
		this.page = page;
	}

	@Override
	public IMessage onMessage(PacketModularSelectPage message, MessageContext ctx) {
		IModularTileEntity tile = (IModularTileEntity) message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);

		tile.getModular().getGuiManager().setPage(message.page);
		EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
		if(entityPlayerMP.getExtendedProperties(ModularPageSaver.class.getName()) != null)
			if(((ModularPageSaver)entityPlayerMP.getExtendedProperties(ModularPageSaver.class.getName())).getSave(message.x, message.y, message.z) != null)
				((ModularPageSaver)entityPlayerMP.getExtendedProperties(ModularPageSaver.class.getName())).getSave(message.x, message.y, message.z).page = message.page;
			else
				((ModularPageSaver)entityPlayerMP.getExtendedProperties(ModularPageSaver.class.getName())).saver.add(new ModularPageTileSaver(message.page, message.x, message.y, message.z));
		else
			entityPlayerMP.registerExtendedProperties(ModularPageSaver.class.getName(), new ModularPageSaver(new ModularPageTileSaver(message.page, message.x, message.y, message.z)));
		getWorld(ctx).markBlockForUpdate(message.x, message.y, message.z);
		ModularMachinesApi.handler.openGui(entityPlayerMP, 0, entityPlayerMP.worldObj, message.x, message.y, message.z);

		return null;
	}

}
