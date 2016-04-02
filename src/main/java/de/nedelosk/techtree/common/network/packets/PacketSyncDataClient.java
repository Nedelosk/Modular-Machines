package de.nedelosk.techtree.common.network.packets;

import java.util.Collection;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import de.nedelosk.techtree.api.TechTreeCategories;
import de.nedelosk.techtree.api.TechTreeCategoryList;
import de.nedelosk.techtree.api.TechTreeEntry;
import de.nedelosk.techtree.api.TechTreeManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketSyncDataClient implements IMessage, IMessageHandler<PacketSyncDataClient, IMessage> {

	public PacketSyncDataClient() {
	}

	@Override
	public IMessage onMessage(PacketSyncDataClient message, MessageContext ctx) {
		EntityPlayer player = ctx.getServerHandler().playerEntity;
		Collection<TechTreeCategoryList> tc = TechTreeCategories.entryCategories.values();
		for ( TechTreeCategoryList cat : tc ) {
			Collection<TechTreeEntry> rl = cat.entrys.values();
			for ( TechTreeEntry tte : rl ) {
				if ((TechTreeManager.isEntryComplete(player, tte.key)) && (tte.siblings != null)) {
					for ( String sib : tte.siblings ) {
						if (!TechTreeManager.isEntryComplete(player, sib)) {
							TechTreeManager.completeEntry(player, sib);
						}
					}
				}
			}
		}
		PacketHandler.INSTANCE.sendTo(new PacketSyncData(player), (EntityPlayerMP) player);
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}
}
