package de.nedelosk.forestmods.common.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import de.nedelosk.forestmods.common.network.packets.PacketModularAssemblerCreateGroup;
import de.nedelosk.forestmods.common.network.packets.PacketModularAssemblerSelectGroup;
import de.nedelosk.forestmods.common.network.packets.PacketModularAssemblerSyncSlot;
import de.nedelosk.forestmods.common.network.packets.PacketModule;
import de.nedelosk.forestmods.common.network.packets.PacketSelectModule;
import de.nedelosk.forestmods.common.network.packets.PacketSelectModulePage;
import de.nedelosk.forestmods.common.network.packets.PacketSyncMachineMode;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("forestmods");
	private static int ID = 0;

	public static void preInit() {
		INSTANCE.registerMessage(PacketSyncMachineMode.class, PacketSyncMachineMode.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSelectModule.class, PacketSelectModule.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSelectModulePage.class, PacketSelectModulePage.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSelectModulePage.class, PacketSelectModulePage.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketModule.class, PacketModule.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketModularAssemblerSelectGroup.class, PacketModularAssemblerSelectGroup.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketModularAssemblerSyncSlot.class, PacketModularAssemblerSyncSlot.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketModularAssemblerCreateGroup.class, PacketModularAssemblerCreateGroup.class, nextID(), Side.SERVER);
	}

	public static int nextID() {
		return ID++;
	}
}
