package de.nedelosk.forestmods.common.network.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.nedelosk.forestcore.network.PacketTileEntity;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import de.nedelosk.forestmods.common.core.ForestMods;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketSelectModuleStack extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketSelectModuleStack, IMessage> {

	public ModuleUID UID;

	public PacketSelectModuleStack() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		UID = new ModuleUID(ByteBufUtils.readUTF8String(buf));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		ByteBufUtils.writeUTF8String(buf, UID.toString());
	}

	public PacketSelectModuleStack(TileEntity tile, ModuleStack stack) {
		super(tile);
		this.UID = stack.getUID();
	}

	public PacketSelectModuleStack(TileEntity tile, ModuleUID UID) {
		super(tile);
		this.UID = UID;
	}

	@Override
	public IMessage onMessage(PacketSelectModuleStack message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		IModularTileEntity tile = (IModularTileEntity) message.getTileEntity(world);
		EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
		tile.getModular().setCurrentStack(tile.getModular().getModuleStack(message.UID));
		entityPlayerMP.openGui(ForestMods.instance, 0, world, message.x, message.y, message.z);
		return null;
	}
}
