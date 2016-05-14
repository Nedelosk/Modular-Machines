package de.nedelosk.forestmods.common.network.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestmods.library.modular.IModularTileEntity;
import de.nedelosk.forestmods.library.modules.IModule;
import de.nedelosk.forestmods.library.modules.ModuleUID;
import de.nedelosk.forestmods.library.network.PacketTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketModule extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketModule, IMessage> {

	private int index;
	private NBTTagCompound nbt;

	public PacketModule() {
	}

	public <T extends TileEntity & IModularTileEntity> PacketModule(T tile, IModule module) {
		super(tile);
		this.index = module.getIndex();
		NBTTagCompound nbt = new NBTTagCompound();
		module.writeToNBT(nbt, tile.getModular());
		this.nbt = nbt;
	}

	public <T extends TileEntity & IModularTileEntity> PacketModule(T tile, int index) {
		super(tile);
		this.index = index;
		NBTTagCompound nbt = new NBTTagCompound();
		tile.getModular().getModule(index).writeToNBT(nbt, tile.getModular());
		this.nbt = nbt;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		index = buf.readInt();
		nbt = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(index);
		ByteBufUtils.writeTag(buf, nbt);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage onMessage(PacketModule message, MessageContext ctx) {
		World world = Minecraft.getMinecraft().theWorld;
		TileEntity tile = message.getTileEntity(world);
		if (tile == null || ((IModularTileEntity) tile).getModular() == null) {
			return null;
		}
		IModule module = ((IModularTileEntity) tile).getModular().getModule(message.index);
		module.readFromNBT(message.nbt, ((IModularTileEntity) tile).getModular());
		return null;
	}
}
