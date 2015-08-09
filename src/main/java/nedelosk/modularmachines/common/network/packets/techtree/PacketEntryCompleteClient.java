package nedelosk.modularmachines.common.network.packets.techtree;

import io.netty.buffer.ByteBuf;
import nedelosk.modularmachines.api.techtree.TechPointTypes;
import nedelosk.modularmachines.api.techtree.TechTreeManager;
import nedelosk.modularmachines.client.proxy.ClientProxy;
import nedelosk.modularmachines.client.techtree.gui.GuiTechTree;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import java.util.ArrayList;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PacketEntryCompleteClient implements IMessage, IMessageHandler<PacketEntryCompleteClient, IMessage> {

	public String key;
	public int points;
	public TechPointTypes pointType;
	
	public PacketEntryCompleteClient() {
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
	
	public PacketEntryCompleteClient(String key, int points, TechPointTypes type) {
		this.key = key;
		this.pointType = type;
		this.points = points;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(PacketEntryCompleteClient message, MessageContext ctx) {
		
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		TechTreeManager.completeEntry(player, message.key);
		if(Minecraft.getMinecraft().currentScreen instanceof GuiTechTree)
		{
		ArrayList<String> list = new ArrayList<String>();
		if(GuiTechTree.completedEntrys.get(player.getCommandSenderName()) != null)
			list = GuiTechTree.completedEntrys.get(player.getCommandSenderName());
			list.add(message.key);
			GuiTechTree.completedEntrys.put(player.getCommandSenderName(), list);
			((GuiTechTree)Minecraft.getMinecraft().currentScreen).updateEntrys();
		}
		TechTreeManager.setTechPoints(player, message.points, message.pointType);
		ClientProxy.techPointGui.addPoints(message.pointType, message.points);
		
		return null;
	}

}
