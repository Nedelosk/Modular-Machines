package nedelosk.modularmachines.api.packets;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import nedelosk.modularmachines.api.packets.pages.PacketSelectGui;
import nedelosk.modularmachines.api.packets.pages.PacketSelectManagerTab;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("modularmachines");
	private static int ID = 0;

	public static void preInit() {
		// Modular
		INSTANCE.registerMessage(PacketSwitchMode.class, PacketSwitchMode.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSelectGui.class, PacketSelectGui.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSelectGui.class, PacketSelectGui.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketSelectManagerTab.class, PacketSelectManagerTab.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSelectManagerTab.class, PacketSelectManagerTab.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketTankManager.class, PacketTankManager.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketTankManager.class, PacketTankManager.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketProducerEngine.class, PacketProducerEngine.class, nextID(), Side.SERVER);
	}

	public static int nextID() {
		return ID++;
	}
}
