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
import de.nedelosk.forestmods.library.modules.ModuleUID;
import de.nedelosk.forestmods.library.network.PacketTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketModularAssemblerSyncSlot extends PacketTileEntity<TileModularAssembler> implements IMessageHandler<PacketModularAssemblerSyncSlot, IMessage> {

	public int slotIndex;
	public ItemStack stack;

	public PacketModularAssemblerSyncSlot() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		slotIndex = buf.readInt();
		if(buf.readBoolean()){
			stack = ByteBufUtils.readItemStack(buf);
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(slotIndex);
		buf.writeBoolean(stack != null);
		if(stack != null){
			ByteBufUtils.writeItemStack(buf, stack);
		}
	}

	public PacketModularAssemblerSyncSlot(TileModularAssembler tile, int slotIndex, ItemStack stack) {
		super(tile);
		
		this.slotIndex = slotIndex;
		this.stack = stack;
	}

	@Override
	public IMessage onMessage(PacketModularAssemblerSyncSlot message, MessageContext ctx) {
		EntityPlayerMP player = ctx.getServerHandler().playerEntity;
		World world = ctx.getServerHandler().playerEntity.worldObj;
		TileModularAssembler tileAssembler = message.getTileEntity(world);
		IAssembler assembler = tileAssembler.getAssembler();
		if(assembler.getStack(message.slotIndex) != null){
			player.inventory.setItemStack(message.stack.copy());
			assembler.setStack(message.slotIndex, null);
		}else{
			player.inventory.getItemStack().stackSize--;
			if(player.inventory.getItemStack().stackSize == 0){
				player.inventory.setItemStack(null);
			}
			assembler.setStack(message.slotIndex, message.stack);
		}
		assembler.updateActivitys(ctx.getServerHandler().playerEntity, true);
		assembler.getTile().markDirty();
		return null;
	}
}
