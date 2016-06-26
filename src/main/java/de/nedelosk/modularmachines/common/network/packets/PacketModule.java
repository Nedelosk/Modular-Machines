package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketModule extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketModule, IMessage> {

	private int index;
	private NBTTagCompound nbt;

	public PacketModule() {
	}

	public <T extends TileEntity & IModularHandler> PacketModule(T tile, IModuleState module) {
		super(tile);
		this.index = module.getIndex();
		NBTTagCompound nbt = new NBTTagCompound();
		module.writeToNBT(nbt, tile.getModular());
		this.nbt = nbt;
	}

	public <T extends TileEntity & IModularHandler> PacketModule(T tile, int index) {
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
		if (tile == null || ((IModularHandler) tile).getModular() == null) {
			return null;
		}
		IModuleState module = ((IModularHandler) tile).getModular().getModule(message.index);
		module.readFromNBT(message.nbt, ((IModularHandler) tile).getModular());
		return null;
	}
}
