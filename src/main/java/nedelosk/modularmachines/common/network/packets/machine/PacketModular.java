package nedelosk.modularmachines.common.network.packets.machine;

import io.netty.buffer.ByteBuf;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.network.packets.saver.ModularSaveModule;
import nedelosk.modularmachines.common.network.packets.saver.ModularTileEntitySave;
import nedelosk.nedeloskcore.common.network.packets.PacketTileEntity;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketModular extends PacketTileEntity<TileModular> implements IMessageHandler<PacketModular, IMessage> {

	public String page;
	
	public PacketModular() {
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

	public PacketModular(TileModular tile, String page) {
		super(tile);
		this.page = page;
	}
	
	@Override
	public IMessage onMessage(PacketModular message, MessageContext ctx) {
		TileModular tile = message.getTileEntity(ctx.getServerHandler().playerEntity.worldObj);
		
		tile.page = message.page;
		EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
		if(entityPlayerMP.getExtendedProperties(ModularSaveModule.class.getName()) != null)
			if(((ModularSaveModule)entityPlayerMP.getExtendedProperties(ModularSaveModule.class.getName())).getSave(message.x, message.y, message.z) != null)
				((ModularSaveModule)entityPlayerMP.getExtendedProperties(ModularSaveModule.class.getName())).getSave(message.x, message.y, message.z).page = message.page;
			else
				((ModularSaveModule)entityPlayerMP.getExtendedProperties(ModularSaveModule.class.getName())).saver.add(new ModularTileEntitySave(message.page, message.x, message.y, message.z));
		else
			entityPlayerMP.registerExtendedProperties(ModularSaveModule.class.getName(), new ModularSaveModule(new ModularTileEntitySave(message.page, message.x, message.y, message.z)));
		getWorld(ctx).markBlockForUpdate(message.x, message.y, message.z);
		entityPlayerMP.openGui(ModularMachines.instance, 0, entityPlayerMP.worldObj, message.x, message.y, message.z);
		
		return null;
	}

}
