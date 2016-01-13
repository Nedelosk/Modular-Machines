package nedelosk.modularmachines.api.packets;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import nedelosk.modularmachines.api.packets.pages.PacketSelectPage;
import nedelosk.modularmachines.api.packets.pages.PacketSelectTankManagerTab;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("modularmachines");
	private static int ID = 0;

	public static void preInit() {
		// Modular
		INSTANCE.registerMessage(PacketSwitchMode.class, PacketSwitchMode.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSelectPage.class, PacketSelectPage.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSelectPage.class, PacketSelectPage.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketSelectTankManagerTab.class, PacketSelectTankManagerTab.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSelectTankManagerTab.class, PacketSelectTankManagerTab.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketTankManager.class, PacketTankManager.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketTankManager.class, PacketTankManager.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketProducerEngine.class, PacketProducerEngine.class, nextID(), Side.SERVER);
	}

	public static int nextID() {
		return ID++;
	}
}
