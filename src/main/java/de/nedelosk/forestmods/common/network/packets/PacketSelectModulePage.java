package de.nedelosk.forestmods.common.network.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.network.PacketTileEntity;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modular.managers.IModularModuleManager;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import de.nedelosk.forestmods.common.core.ForestMods;
import de.nedelosk.forestmods.common.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketSelectModulePage extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketSelectModulePage, IMessage> {

	public int pageID;
	public ModuleUID UID;

	public PacketSelectModulePage() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		pageID = buf.readInt();
		UID = new ModuleUID(ByteBufUtils.readUTF8String(buf));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(pageID);
		ByteBufUtils.writeUTF8String(buf, UID.toString());
	}

	public PacketSelectModulePage(TileEntity tile, int pageID, ModuleUID UID) {
		super(tile);
		this.pageID = pageID;
		this.UID = UID;
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
		IModularTileEntity tile = (IModularTileEntity) message.getTileEntity(world);
		tile.getModular().getManager(IModularModuleManager.class).getModuleStack(message.UID).getModule().setCurrentPage(message.pageID);
	}

	private void handleServer(PacketSelectModulePage message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		IModularTileEntity tile = (IModularTileEntity) message.getTileEntity(world);
		tile.getModular().getManager(IModularModuleManager.class).getModuleStack(message.UID).getModule().setCurrentPage(message.pageID);
		EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
		PacketHandler.INSTANCE.sendTo(message, entityPlayerMP);
		entityPlayerMP.openGui(ForestMods.instance, 0, entityPlayerMP.worldObj, message.x, message.y, message.z);
	}
}
