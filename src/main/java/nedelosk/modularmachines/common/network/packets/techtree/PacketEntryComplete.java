package nedelosk.modularmachines.common.network.packets.techtree;

import io.netty.buffer.ByteBuf;
import nedelosk.modularmachines.api.basic.techtree.TechPointTypes;
import nedelosk.modularmachines.api.basic.techtree.TechTreeManager;
import nedelosk.modularmachines.common.network.packets.PacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketEntryComplete implements IMessage, IMessageHandler<PacketEntryComplete, IMessage> {

	public String key;
	public int points;
	public TechPointTypes pointType;
	
	public PacketEntryComplete() {
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		key = ByteBufUtils.readUTF8String(buf);
		points = buf.readInt();
		pointType = TechPointTypes.values()[buf.readInt()];
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, key);
		buf.writeInt(points);
		buf.writeInt(pointType.ordinal());
	}

	public PacketEntryComplete(String key, int points, TechPointTypes type) {
		this.key = key;
		this.pointType = type;
		this.points = points;
	}
	
	@Override
	public IMessage onMessage(PacketEntryComplete message, MessageContext ctx) {
		
		EntityPlayerMP player = ctx.getServerHandler().playerEntity;
		TechTreeManager.completeEntry(player, message.key);
		TechTreeManager.setTechPoints(player, message.points, message.pointType);
		PacketHandler.INSTANCE.sendTo(new PacketEntryCompleteClient(message.key, message.points, message.pointType), player);
		
		return null;
	}

}
