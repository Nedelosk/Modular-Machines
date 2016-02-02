package nedelosk.modularmachines.api.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import nedelosk.forestcore.library.packets.PacketTileEntity;
import nedelosk.modularmachines.api.ModularMachinesApi;
import nedelosk.modularmachines.api.modular.basic.IModularDefault;
import nedelosk.modularmachines.api.modular.managers.IModularInventoryManager;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.modules.inventory.IModuleInventory;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketSelectInventory extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketSelectInventory, IMessage> {

	public String UID;

	public PacketSelectInventory() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		UID = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		ByteBufUtils.writeUTF8String(buf, UID);
	}

	public PacketSelectInventory(TileEntity tile, IModuleInventory inventory) {
		super(tile);
		this.UID = inventory.getUID();
	}

	public PacketSelectInventory(TileEntity tile, String UID) {
		super(tile);
		this.UID = UID;
	}

	@Override
	public IMessage onMessage(PacketSelectInventory message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		IModularTileEntity<IModularDefault> tile = (IModularTileEntity) message.getTileEntity(world);
		IModularInventoryManager inventoryManager = tile.getModular().getInventoryManager();
		inventoryManager.setCurrentInventory(inventoryManager.getInventory(message.UID));
		EntityPlayerMP entityPlayerMP = ctx.getServerHandler().playerEntity;
		getWorld(ctx).markBlockForUpdate(message.x, message.y, message.z);
		ModularMachinesApi.handler.openGui(entityPlayerMP, 0, entityPlayerMP.worldObj, message.x, message.y, message.z);
		return null;
	}
}
