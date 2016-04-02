package de.nedelosk.techtree.common.network.packets;

import java.util.ArrayList;
import java.util.Iterator;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.nedelosk.techtree.api.TechPointTypes;
import de.nedelosk.techtree.api.TechTreeManager;
import de.nedelosk.techtree.api.TechTreePlayerData;
import de.nedelosk.techtree.client.gui.GuiTechTree;
import de.nedelosk.techtree.client.proxy.ClientProxy;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PacketSyncData implements IMessage, IMessageHandler<PacketSyncData, IMessage> {

	public ArrayList<String> entrys;
	public int[] points;

	public PacketSyncData() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.entrys = new ArrayList<String>();
		if (buf.readBoolean()) {
			int size = buf.readInt();
			for ( int i = 0; i < size; i++ ) {
				this.entrys.add(ByteBufUtils.readUTF8String(buf));
			}
		}
		if (buf.readBoolean()) {
			points = new int[TechPointTypes.values().length];
			for ( TechPointTypes type : TechPointTypes.values() ) {
				points[type.ordinal()] = buf.readInt();
			}
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(entrys != null && !entrys.isEmpty());
		if (entrys != null && !entrys.isEmpty()) {
			buf.writeInt(entrys.size());
			for ( String entry : entrys ) {
				if (entry != null) {
					ByteBufUtils.writeUTF8String(buf, entry);
				}
			}
		}
		buf.writeBoolean(points != null);
		if (points != null) {
			for ( int i : points ) {
				buf.writeInt(i);
			}
		}
	}

	public PacketSyncData(EntityPlayer player) {
		entrys = player.getExtendedProperties("MODULARMACHINES:TECHTREE") != null
				? ((TechTreePlayerData) player.getExtendedProperties("MODULARMACHINES:TECHTREE")).techEntrys : null;
		points = player.getExtendedProperties("MODULARMACHINES:TECHTREE") != null
				? ((TechTreePlayerData) player.getExtendedProperties("MODULARMACHINES:TECHTREE")).techPoints : null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(PacketSyncData message, MessageContext ctx) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if (player.getExtendedProperties("MODULARMACHINES:TECHTREE") != null) {
			((TechTreePlayerData) player.getExtendedProperties("MODULARMACHINES:TECHTREE")).techEntrys = new ArrayList<String>();
		}
		String key;
		for ( Iterator i$ = message.entrys.iterator(); i$.hasNext(); TechTreeManager.completeEntry(player, key) ) {
			key = (String) i$.next();
		}
		GuiTechTree.completedEntrys.put(player.getCommandSenderName(), message.entrys);
		for ( TechPointTypes type : TechPointTypes.values() ) {
			if (message.points[type.ordinal()] > TechTreeManager.getTechPoints(player)[type.ordinal()]) {
				ClientProxy.techPointGui.addPoints(type, message.points[type.ordinal()] - TechTreeManager.getTechPoints(player)[type.ordinal()]);
			}
		}
		TechTreeManager.setTechPoints(player, message.points);
		return null;
	}
}
