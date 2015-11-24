package nedelosk.modularmachines.common.network.packets;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularSelectPage;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularAssemblerSelection;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularSelectTankManager;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularSwitchMode;
import nedelosk.modularmachines.common.network.packets.machine.PacketProducerEngine;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("modularmachines");

	private static int ID = 0;

	public static void preInit() {
		
		//Modular
		INSTANCE.registerMessage(PacketModularSwitchMode.class, PacketModularSwitchMode.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketModularSelectPage.class, PacketModularSelectPage.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketModularSelectPage.class, PacketModularSelectPage.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketModularSelectTankManager.class, PacketModularSelectTankManager.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketModularSelectTankManager.class, PacketModularSelectTankManager.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketProducerEngine.class, PacketProducerEngine.class, nextID(), Side.SERVER);

		// Assembler
		INSTANCE.registerMessage(PacketModularAssemblerSelection.class, PacketModularAssemblerSelection.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketModularAssemblerSelection.class, PacketModularAssemblerSelection.class, nextID(), Side.SERVER);
	}

	public static int nextID() {
		return ID++;
	}

}
