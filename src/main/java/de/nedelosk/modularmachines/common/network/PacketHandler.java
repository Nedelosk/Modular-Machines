package de.nedelosk.modularmachines.common.network;

import de.nedelosk.modularmachines.common.network.packets.PacketModularAssembler;
import de.nedelosk.modularmachines.common.network.packets.PacketModule;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectAssemblerPosition;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectModule;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectModulePage;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncMachineMode;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("modularmachines");
	private static int ID = 0;

	public static void preInit() {
		INSTANCE.registerMessage(PacketSyncMachineMode.class, PacketSyncMachineMode.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSelectModule.class, PacketSelectModule.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSelectModulePage.class, PacketSelectModulePage.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSelectModulePage.class, PacketSelectModulePage.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketModule.class, PacketModule.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketSelectAssemblerPosition.class, PacketSelectAssemblerPosition.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketModularAssembler.class, PacketModularAssembler.class, nextID(), Side.SERVER);
	}

	public static int nextID() {
		return ID++;
	}
}
