package de.nedelosk.modularmachines.common.network;

import java.io.IOException;
import java.io.InputStream;

import de.nedelosk.modularmachines.api.modules.network.DataInputStreamMM;
import de.nedelosk.modularmachines.common.network.packets.IPacketClient;
import de.nedelosk.modularmachines.common.network.packets.IPacketServer;
import de.nedelosk.modularmachines.common.network.packets.PacketId;
import de.nedelosk.modularmachines.common.network.packets.PacketModuleCleaner;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectAssemblerPosition;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectModule;
import de.nedelosk.modularmachines.common.network.packets.PacketSelectModulePage;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncHandlerState;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncHeatBuffer;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncModule;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncPermission;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncRedstoneMode;
import de.nedelosk.modularmachines.common.network.packets.PacketSyncToolMode;
import de.nedelosk.modularmachines.common.network.packets.PacketUpdateModule;
import de.nedelosk.modularmachines.common.utils.Log;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.management.PlayerChunkMap;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketHandler {

	public static final String channelId = "modularmachines";
	private final static FMLEventChannel channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(channelId);

	public PacketHandler() {
		channel.register(this);
		registerClientPacket(new PacketModuleCleaner());
		registerServerPacket(new PacketModuleCleaner());
		registerClientPacket(new PacketSelectAssemblerPosition());
		registerServerPacket(new PacketSelectAssemblerPosition());
		registerClientPacket(new PacketSelectModule());
		registerServerPacket(new PacketSelectModule());
		registerClientPacket(new PacketSelectModulePage());
		registerServerPacket(new PacketSelectModulePage());
		registerClientPacket(new PacketSyncToolMode());
		registerServerPacket(new PacketSyncToolMode());
		registerClientPacket(new PacketSyncHandlerState());
		registerServerPacket(new PacketSyncHandlerState());
		registerClientPacket(new PacketSyncModule());
		registerClientPacket(new PacketSyncRedstoneMode());
		registerServerPacket(new PacketSyncRedstoneMode());
		registerClientPacket(new PacketSyncToolMode());
		registerServerPacket(new PacketSyncToolMode());
		registerClientPacket(new PacketSyncRedstoneMode());
		registerServerPacket(new PacketSyncRedstoneMode());
		registerClientPacket(new PacketSyncPermission());
		registerServerPacket(new PacketSyncPermission());
		registerClientPacket(new PacketSyncHeatBuffer());
		registerClientPacket(new PacketUpdateModule());
	}

	public static void registerClientPacket(IPacketClient packet) {
		packet.getPacketId().setPacketClient(packet);
	}

	public static void registerServerPacket(IPacketServer packet) {
		packet.getPacketId().setPacketServer(packet);
	}

	public static void sendToNetwork(IPacketClient packet, BlockPos pos, WorldServer world) {
		if (packet == null) {
			return;
		}
		WorldServer worldServer = world;
		PlayerChunkMap playerManager = worldServer.getPlayerChunkMap();
		int chunkX = pos.getX() >> 4;
		int chunkZ = pos.getZ() >> 4;
		for(Object playerObj : world.playerEntities) {
			if (playerObj instanceof EntityPlayerMP) {
				EntityPlayerMP player = (EntityPlayerMP) playerObj;
				if (playerManager.isPlayerWatchingChunk(player, chunkX, chunkZ)) {
					sendToPlayer(packet, player);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static void sendToServer(IPacketServer packet) {
		NetHandlerPlayClient netHandler = Minecraft.getMinecraft().getConnection();
		if (netHandler != null) {
			netHandler.sendPacket(packet.getPacket());
		}
	}

	public static void sendToPlayer(IPacketClient packet, EntityPlayer entityplayer) {
		if (!(entityplayer instanceof EntityPlayerMP) || entityplayer instanceof FakePlayer) {
			return;
		}
		EntityPlayerMP player = (EntityPlayerMP) entityplayer;
		sendPacket(packet.getPacket(), player);
	}

	public static void sendPacket(FMLProxyPacket packet, EntityPlayerMP player) {
		channel.sendTo(packet, player);
	}

	@SubscribeEvent
	public void onPacket(ServerCustomPacketEvent event) {
		DataInputStreamMM data = getStream(event.getPacket());
		EntityPlayerMP player = ((NetHandlerPlayServer) event.getHandler()).playerEntity;
		try {
			byte packetIdOrdinal = data.readByte();
			PacketId packetId = PacketId.VALUES[packetIdOrdinal];
			IPacketServer packetHandler = packetId.getServerPacket();
			checkThreadAndEnqueue(packetHandler, data, player, player.getServerWorld());
		} catch (IOException e) {
			Log.err("Failed to read packet.", e);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPacket(ClientCustomPacketEvent event) {
		DataInputStreamMM data = getStream(event.getPacket());
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		try {
			byte packetIdOrdinal = data.readByte();
			PacketId packetId = PacketId.VALUES[packetIdOrdinal];
			IPacketClient packetHandler = packetId.getClientPacket();
			checkThreadAndEnqueue(packetHandler, data, player, Minecraft.getMinecraft());
		} catch (IOException e) {
			Log.err("Failed to read packet.", e);
		}
	}

	private static DataInputStreamMM getStream(FMLProxyPacket fmlPacket) {
		InputStream is = new ByteBufInputStream(fmlPacket.payload());
		return new DataInputStreamMM(is);
	}

	@SideOnly(Side.CLIENT)
	private static void checkThreadAndEnqueue(final IPacketClient packet, final DataInputStreamMM data, final EntityPlayer player, IThreadListener threadListener) {
		if (!threadListener.isCallingFromMinecraftThread()) {
			threadListener.addScheduledTask(new Runnable() {

				@Override
				public void run() {
					try {
						packet.readData(data);
						packet.onPacketData(data, player);
					} catch (IOException e) {
						Log.err("Network Error", e);
					}
				}
			});
		}
	}

	private static void checkThreadAndEnqueue(final IPacketServer packet, final DataInputStreamMM data, final EntityPlayerMP player, IThreadListener threadListener) {
		if (!threadListener.isCallingFromMinecraftThread()) {
			threadListener.addScheduledTask(new Runnable() {

				@Override
				public void run() {
					try {
						packet.readData(data);
						packet.onPacketData(data, player);
					} catch (IOException e) {
						Log.err("Network Error", e);
					}
				}
			});
		}
	}
}
