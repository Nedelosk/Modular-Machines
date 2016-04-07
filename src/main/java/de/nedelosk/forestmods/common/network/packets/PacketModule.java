package de.nedelosk.forestmods.common.network.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.forestcore.network.PacketTileEntity;
import de.nedelosk.forestmods.api.modular.IModularTileEntity;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketModule extends PacketTileEntity<TileEntity> implements IMessageHandler<PacketModule, IMessage> {

	private ModuleUID UID;
	private NBTTagCompound nbt;

	public PacketModule() {
	}

	public <T extends TileEntity & IModularTileEntity> PacketModule(T tile, ModuleStack stack) {
		super(tile);
		this.UID = stack.getUID();
		NBTTagCompound nbt = new NBTTagCompound();
		stack.getModule().writeToNBT(nbt, tile.getModular());
		this.nbt = nbt;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		UID = new ModuleUID(ByteBufUtils.readUTF8String(buf));
		nbt = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		ByteBufUtils.writeUTF8String(buf, UID.toString());
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
		ModuleStack stack = ((IModularTileEntity) tile).getModular().getModuleStack(message.UID);
		stack.getModule().readFromNBT(message.nbt, ((IModularTileEntity) tile).getModular());
		return null;
	}
}
