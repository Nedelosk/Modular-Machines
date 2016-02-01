package nedelosk.modularmachines.api.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import nedelosk.forestcore.library.packets.PacketTileEntity;
import nedelosk.modularmachines.api.modular.tile.IModularTileEntity;
import nedelosk.modularmachines.api.utils.ModuleStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketModule<T extends TileEntity & IModularTileEntity> extends PacketTileEntity<T> implements IMessageHandler<PacketModule, IMessage> {

	private String UID;
	private NBTTagCompound nbt;
	private boolean onlySaver;

	public PacketModule() {
	}

	public PacketModule(T tile, ModuleStack stack, boolean onlySaver) {
		super(tile);
		this.UID = stack.getModule().getUID();
		this.onlySaver = onlySaver;
		NBTTagCompound nbt = new NBTTagCompound();
		if (onlySaver) {
			stack.getSaver().writeToNBT(nbt, tile.getModular(), stack);
		} else {
			stack.writeToNBT(nbt, tile.getModular());
		}
		this.nbt = nbt;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		UID = ByteBufUtils.readUTF8String(buf);
		nbt = ByteBufUtils.readTag(buf);
		onlySaver = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		ByteBufUtils.writeUTF8String(buf, UID);
		ByteBufUtils.writeTag(buf, nbt);
		buf.writeBoolean(onlySaver);
	}

	@Override
	public IMessage onMessage(PacketModule message, MessageContext ctx) {
		World world = ctx.getServerHandler().playerEntity.worldObj;
		T tile = (T) message.getTileEntity(world);
		ModuleStack stack = tile.getModular().getModuleManager().getMultiModule(message.UID.split(":")[0]).getStack(message.UID.split(":")[1]);
		if (message.onlySaver) {
			stack.getSaver().readFromNBT(message.nbt, tile.getModular(), stack);
		} else {
			stack = ModuleStack.loadFromNBT(message.nbt, tile.getModular());
			tile.getModular().getModuleManager().getMultiModule(message.UID.split(":")[0]).setStack(stack, message.UID.split(":")[1]);
		}
		return null;
	}
}
