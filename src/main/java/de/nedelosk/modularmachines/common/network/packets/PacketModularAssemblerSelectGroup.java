package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.assembler.IAssembler;
import de.nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import de.nedelosk.modularmachines.common.core.ModularMachines;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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
		ctx.getServerHandler().playerEntity.openGui(ModularMachines.instance, 0, world, message.pos.getX(), message.pos.getY(), message.pos.getZ());
		return null;
	}
}
