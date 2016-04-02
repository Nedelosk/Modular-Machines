package de.nedelosk.forestmods.common.network.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.nedelosk.forestcore.network.PacketTileEntity;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.modular.managers.IModularInventoryManager;
import de.nedelosk.forestmods.api.producers.handlers.inventory.IModuleInventory;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import de.nedelosk.forestmods.common.core.ForestMods;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketSelectInventory extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketSelectInventory, IMessage> {

	public ModuleUID UID;

	public PacketSelectInventory() {
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

	public PacketSelectInventory(TileEntity tile, IModuleInventory inventory) {
		super(tile);
		this.UID = inventory.getModuleStack().getUID();
	}

	public PacketSelectInventory(TileEntity tile, ModuleUID UID) {
		super(tile);
		this.UID = UID;
	}

	@Override
	public IMessage onMessage(PacketSelectInventory message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		IModularTileEntity tile = (IModularTileEntity) message.getTileEntity(world);
		IModularInventoryManager inventoryManager = tile.getModular().getManager(IModularInventoryManager.class);
		inventoryManager.setCurrentInventory(inventoryManager.getInventory(message.UID));
		EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
		entityPlayerMP.openGui(ForestMods.instance, 0, entityPlayerMP.worldObj, message.x, message.y, message.z);
		return null;
	}
}
