package de.nedelosk.modularmachines.common.network.packets;

import de.nedelosk.modularmachines.api.modular.IModularHandler;
import de.nedelosk.modularmachines.api.modules.state.IModuleState;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketModule extends PacketModularHandler implements IMessageHandler<PacketModule, IMessage> {

	private int index;
	private NBTTagCompound nbt;

	public PacketModule() {
	}

	public PacketModule(IModularHandler handler, IModuleState module) {
		this(handler, module.getIndex());
	}

	public PacketModule(IModularHandler handler, int index) {
		super(handler);
		this.index = index;
		NBTTagCompound nbt = new NBTTagCompound();
		handler.getModular().getModule(index).writeToNBT(nbt, handler.getModular());
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
		IModularHandler handler = message.getModularHandler(ctx);
		if (handler == null || handler.getModular() == null) {
			return null;
		}
		IModuleState module = handler.getModular().getModule(message.index);
		module.readFromNBT(message.nbt, handler.getModular());
		return null;
	}
}
