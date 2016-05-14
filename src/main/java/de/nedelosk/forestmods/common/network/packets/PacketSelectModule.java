package de.nedelosk.forestmods.common.network.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.nedelosk.forestmods.common.core.ForestMods;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.ModuleUID;
import de.nedelosk.forestmods.library.network.PacketTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketSelectModule extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketSelectModule, IMessage> {

	public int index;

	public PacketSelectModule() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		index = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(index)
	}

	public PacketSelectModule(TileEntity tile, IModule module) {
		this(tile, module.getIndex());
	}

	public PacketSelectModule(TileEntity tile, int index) {
		super(tile);
		this.index = index;
	}

	@Override
	public IMessage onMessage(PacketSelectModule message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		IModularTileEntity tile = (IModularTileEntity) message.getTileEntity(world);
		EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
		tile.getModular().setCurrentModule(tile.getModular().getModule(message.index));
		entityPlayerMP.openGui(ForestMods.instance, 0, world, message.x, message.y, message.z);
		return null;
	}
}
