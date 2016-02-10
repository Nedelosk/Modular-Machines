package de.nedelosk.forestmods.common.network.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.network.PacketTileEntity;
import de.nedelosk.forestmods.api.modular.tile.IModularTileEntity;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketModule extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketModule, IMessage> {

	private String UID;
	private NBTTagCompound nbt;
	private boolean onlySaver;

	public PacketModule() {
	}

	public <T extends TileEntity & IModularTileEntity> PacketModule(T tile, ModuleStack stack, boolean onlySaver) {
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

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage onMessage(PacketModule message, MessageContext ctx) {
		World world = Minecraft.getMinecraft().theWorld;
		TileEntity tile = message.getTileEntity(world);
		if (tile == null || ((IModularTileEntity) tile).getModular() == null || ((IModularTileEntity) tile).getModular().getModuleManager() == null) {
			return null;
		}
		ModuleStack stack = ((IModularTileEntity) tile).getModular().getModuleManager().getModuleFromUID(message.UID);
		if (message.onlySaver) {
			stack.getSaver().readFromNBT(message.nbt, ((IModularTileEntity) tile).getModular(), stack);
		} else {
			stack = ModuleStack.loadFromNBT(message.nbt, ((IModularTileEntity) tile).getModular());
			((IModularTileEntity) tile).getModular().getModuleManager().getMultiModule(message.UID.split(":")[0]).setStack(stack, message.UID.split(":")[1]);
		}
		return null;
	}
}
