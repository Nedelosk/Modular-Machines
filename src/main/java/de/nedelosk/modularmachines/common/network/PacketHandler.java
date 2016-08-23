package de.nedelosk.modularmachines.common.network;

import de.nedelosk.modularmachines.common.network.packets.AbstractPacketHandler;
import de.nedelosk.modularmachines.common.network.packets.PacketModuleClean;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectAssemblerPosition;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectModule;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectModulePage;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncAssembler;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncHeatBuffer;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncMachineMode;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerChunkMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("modularmachines");
	private static AbstractPacketHandler handler;
	private static int ID = 0;

	public static void preInit() {
		handler = new AbstractPacketHandler();
		INSTANCE.registerMessage(handler, PacketSyncMachineMode.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(handler, PacketSyncMachineMode.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(handler, PacketSelectModule.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(handler, PacketSelectModule.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(handler, PacketSelectModulePage.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(handler, PacketSelectModulePage.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(handler, PacketModuleClean.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(handler, PacketModuleClean.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(handler, PacketSelectAssemblerPosition.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(handler, PacketSelectAssemblerPosition.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(handler, PacketSyncAssembler.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(handler, PacketSyncAssembler.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(handler, PacketSyncModule.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(handler, PacketSyncModule.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(handler, PacketSyncHeatBuffer.class, nextID(), Side.CLIENT);
	}

	public static void sendToNetwork(IMessage message, BlockPos pos, WorldServer world) {
		if (message == null) {
			return;
		}

		WorldServer worldServer = world;
		PlayerChunkMap playerManager = worldServer.getPlayerChunkMap();

		int chunkX = pos.getX() >> 4;
		int chunkZ = pos.getZ() >> 4;

		for (Object playerObj : world.playerEntities) {
			if (playerObj instanceof EntityPlayerMP) {
				EntityPlayerMP player = (EntityPlayerMP) playerObj;

				if (playerManager.isPlayerWatchingChunk(player, chunkX, chunkZ)) {
					INSTANCE.sendTo(message, player);
				}
			}
		}
	}

	public static int nextID() {
		return ID++;
	}
}
