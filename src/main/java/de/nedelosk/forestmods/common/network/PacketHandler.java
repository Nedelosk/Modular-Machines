package de.nedelosk.forestmods.common.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import de.nedelosk.forestmods.common.network.packets.PacketModule;
import de.nedelosk.forestmods.common.network.packets.PacketSelectInventory;
import de.nedelosk.forestmods.common.network.packets.PacketSelectManagerTab;
import de.nedelosk.forestmods.common.network.packets.PacketSwitchMachineMode;
import de.nedelosk.forestmods.common.network.packets.PacketSwitchWorktableMode;
import de.nedelosk.forestmods.common.network.packets.PacketSyncEnergy;
import de.nedelosk.forestmods.common.network.packets.PacketSyncEngineProgress;
import de.nedelosk.forestmods.common.network.packets.PacketSyncManagers;
import de.nedelosk.forestmods.common.network.packets.PacketTankManager;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("forestmods");
	private static int ID = 0;

	public static void preInit() {
		INSTANCE.registerMessage(PacketSwitchWorktableMode.class, PacketSwitchWorktableMode.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSwitchMachineMode.class, PacketSwitchMachineMode.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSelectInventory.class, PacketSelectInventory.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSyncManagers.class, PacketSyncManagers.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketSelectManagerTab.class, PacketSelectManagerTab.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSelectManagerTab.class, PacketSelectManagerTab.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketTankManager.class, PacketTankManager.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketSyncEngineProgress.class, PacketSyncEngineProgress.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(PacketModule.class, PacketModule.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(PacketSyncEnergy.class, PacketSyncEnergy.class, nextID(), Side.CLIENT);
	}

	public static int nextID() {
		return ID++;
	}
}
