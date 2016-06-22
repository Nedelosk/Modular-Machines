package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.assembler.IAssembler;
import de.nedelosk.modularmachines.api.modular.assembler.IAssemblerGroup;
import de.nedelosk.modularmachines.api.modules.IModuleController;
import de.nedelosk.modularmachines.api.modules.ModuleManager;
import de.nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketModularAssemblerCreateGroup extends PacketTileEntity<TileModularAssembler> implements IMessageHandler<PacketModularAssemblerCreateGroup, IMessage> {

	public int groupID;
	public ItemStack stack;

	public PacketModularAssemblerCreateGroup() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		groupID = buf.readInt();
		stack = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(groupID);
		ByteBufUtils.writeItemStack(buf, stack);
	}

	public PacketModularAssemblerCreateGroup(TileModularAssembler tile, int groupID, ItemStack stack) {
		super(tile);

		this.stack = stack;
		this.groupID = groupID;
	}

	@Override
	public IMessage onMessage(PacketModularAssemblerCreateGroup message, MessageContext ctx) {
		EntityPlayerMP player = ctx.getServerHandler().playerEntity;
		World world = ctx.getServerHandler().playerEntity.worldObj;
		TileModularAssembler tileAssembler = message.getTileEntity(world);
		IAssembler assembler = tileAssembler.getAssembler();
		IModuleController controller = (IModuleController) ModuleManager.getContainerFromItem(message.stack).getModule();
		IAssemblerGroup group = controller.createGroup(assembler, message.stack, message.groupID);
		assembler.setStack(group.getControllerSlot().getIndex(), message.stack);
		player.inventory.getItemStack().stackSize--;
		if(player.inventory.getItemStack().stackSize == 0){
			player.inventory.setItemStack(null);
		}
		assembler.update(ctx.getServerHandler().playerEntity, true);
		return null;
	}
}
