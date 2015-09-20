package nedelosk.modularmachines.common.network.packets.techtree.editor;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import nedelosk.modularmachines.client.techtree.utils.TechTreeEditorData;

public class PacketTechTreeEditorSave implements IMessage, IMessageHandler<PacketTechTreeEditorSave, IMessage> {

	public TechTreeEditorData data;
	
	@Override
	public IMessage onMessage(PacketTechTreeEditorSave message, MessageContext ctx) {
		if(ctx.getServerHandler().playerEntity.worldObj.loadItemData(TechTreeEditorData.class, "mm.techtree.editor") == null){
			ctx.getServerHandler().playerEntity.worldObj.setItemData("mm.techtree.editor", message.data);
		}else{
			TechTreeEditorData data = (TechTreeEditorData) ctx.getServerHandler().playerEntity.worldObj.loadItemData(TechTreeEditorData.class, "mm.techtree.editor");
			data.key = message.data.key;
			data.category = message.data.category;
			data.column = message.data.column;
			data.row = message.data.row;
			data.pages = message.data.pages;
			data.parents = message.data.parents;
			data.siblings = message.data.siblings;
			data.cBackground = message.data.cBackground;
			data.cIcon = message.data.cIcon;
			data.cKey = message.data.cKey;
			data.cKeyLanguag = message.data.cKeyLanguag;
			data.icon_item = message.data.icon_item;
			data.isAutoUnlock = message.data.isAutoUnlock;
			data.isConcealed = message.data.isConcealed;
			data.languag = message.data.languag;
			data.techPoints = message.data.techPoints;
			data.pointType = message.data.pointType;
			data.languagData = message.data.languagData;
			
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
	}

	@Override
	public void toBytes(ByteBuf buf){
		
	}

}
