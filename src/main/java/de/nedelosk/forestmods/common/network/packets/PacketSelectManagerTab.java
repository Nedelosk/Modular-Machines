package de.nedelosk.forestmods.common.network.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.network.PacketTileEntity;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.modules.managers.IModuleManagerSaver;
import de.nedelosk.forestmods.api.utils.ModularUtils;
import de.nedelosk.forestmods.common.core.ForestMods;
import de.nedelosk.forestmods.common.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketSelectManagerTab extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketSelectManagerTab, IMessage> {

	public int tabID;
	public String moduleName;

	public PacketSelectManagerTab() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		tabID = buf.readInt();
		moduleName = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(tabID);
		ByteBufUtils.writeUTF8String(buf, moduleName);
	}

	public PacketSelectManagerTab(TileEntity tile, int tabID, String moduleUID) {
		super(tile);
		this.tabID = tabID;
		this.moduleName = moduleUID;
	}

	@Override
	public IMessage onMessage(PacketSelectManagerTab message, MessageContext ctx) {
		World world;
		if (ctx.side == Side.CLIENT) {
			handleClient(message, ctx);
		} else {
			handleServer(message, ctx);
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	private void handleClient(PacketSelectManagerTab message, MessageContext ctx) {
		World world = Minecraft.getMinecraft().theWorld;
		IModularTileEntity tile = (IModularTileEntity) message.getTileEntity(world);
		((IModuleManagerSaver) ModularUtils.getManagers(tile.getModular()).getStack(message.moduleName).getSaver()).setTab(message.tabID);
	}

	private void handleServer(PacketSelectManagerTab message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		IModularTileEntity tile = (IModularTileEntity) message.getTileEntity(world);
		((IModuleManagerSaver) ModularUtils.getManagers(tile.getModular()).getStack(message.moduleName).getSaver()).setTab(message.tabID);
		EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
		PacketHandler.INSTANCE.sendTo(message, entityPlayerMP);
		entityPlayerMP.openGui(ForestMods.instance, 0, entityPlayerMP.worldObj, message.x, message.y, message.z);
	}
}
