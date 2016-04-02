package de.nedelosk.techtree.common.network.packets;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import de.nedelosk.techtree.common.network.packets.editor.PacketTechTreeEditor;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("techtree");
	private static int ID = 0;

	public static void preInit() {
		// TechTree
		INSTANCE.registerMessage(PacketEntryComplete.class, PacketEntryComplete.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketEntryCompleteClient.class, PacketEntryCompleteClient.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketSyncData.class, PacketSyncData.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketSyncDataClient.class, PacketSyncDataClient.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketTechTreeEditor.class, PacketTechTreeEditor.class, nextID(), Side.SERVER);
	}

	public static int nextID() {
		return ID++;
	}
}
