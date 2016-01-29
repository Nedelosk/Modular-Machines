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
import nedelosk.modularmachines.api.modules.managers.IModuleManagerSaver;
import nedelosk.modularmachines.api.packets.PacketHandler;
import nedelosk.modularmachines.api.utils.ModularUtils;
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
			world = Minecraft.getMinecraft().theWorld;
		} else {
			world = ctx.getServerHandler().playerEntity.worldObj;
		}
		IModularTileEntity tile = (IModularTileEntity) message.getTileEntity(world);
		((IModuleManagerSaver) ModularUtils.getManagers(tile.getModular()).getStack(message.moduleName).getSaver()).setTab(message.tabID);
		if (ctx.side == Side.SERVER) {
			EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
			PacketHandler.INSTANCE.sendTo(message, entityPlayerMP);
			getWorld(ctx).markBlockForUpdate(message.x, message.y, message.z);
			ModularMachinesApi.handler.openGui(entityPlayerMP, 0, entityPlayerMP.worldObj, message.x, message.y, message.z);
		}
		return null;
	}
}
