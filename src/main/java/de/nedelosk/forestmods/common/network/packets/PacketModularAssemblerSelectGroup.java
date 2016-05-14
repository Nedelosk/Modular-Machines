package de.nedelosk.forestmods.common.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.nedelosk.forestmods.common.blocks.tile.TileModularAssembler;
import de.nedelosk.forestmods.common.core.ForestMods;
import de.nedelosk.forestmods.library.modular.assembler.IAssembler;
import de.nedelosk.forestmods.library.network.PacketTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;

public class PacketModularAssemblerSelectGroup extends PacketTileEntity<TileModularAssembler> implements IMessageHandler<PacketModularAssemblerSelectGroup, IMessage> {

	public int groupID;

	public PacketModularAssemblerSelectGroup() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		groupID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(groupID);
	}

	public PacketModularAssemblerSelectGroup(TileModularAssembler tile, int groupID) {
		super(tile);

		this.groupID = groupID;
	}

	@Override
	public IMessage onMessage(PacketModularAssemblerSelectGroup message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		TileModularAssembler tileAssembler = message.getTileEntity(world);
		IAssembler assembler = tileAssembler.getAssembler();
		assembler.setCurrentGroup(assembler.getGroups().get(groupID), ctx.getServerHandler().playerEntity);
		ctx.getServerHandler().playerEntity.openGui(ForestMods.instance, 0, world, message.x, message.y, message.z);
		return null;
	}
}
