package de.nedelosk.forestmods.common.network.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.nedelosk.forestmods.common.blocks.tile.TileModularAssembler;
import de.nedelosk.forestmods.common.core.ForestMods;
import de.nedelosk.forestmods.library.modular.assembler.IAssembler;
import de.nedelosk.forestmods.library.modular.assembler.IAssemblerGroup;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.IModuleContainer;
import de.nedelosk.forestmods.library.modules.IModuleController;
import de.nedelosk.forestmods.library.modules.ModuleManager;
import de.nedelosk.forestmods.library.modules.ModuleUID;
import de.nedelosk.forestmods.library.network.PacketTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
		World world = ctx.getServerHandler().playerEntity.worldObj;
		TileModularAssembler tileAssembler = message.getTileEntity(world);
		IAssembler assembler = tileAssembler.getAssembler();
		IModuleController controller = ModuleManager.moduleRegistry.getFakeModule(ModuleManager.moduleRegistry.getContainerFromItem(message.stack));
		IAssemblerGroup group = controller.createGroup(assembler, message.stack, message.groupID);
		assembler.setStack(group.getControllerSlot().getIndex(), message.stack);
		assembler.updateActivitys(ctx.getServerHandler().playerEntity, true);
		return null;
	}
}
