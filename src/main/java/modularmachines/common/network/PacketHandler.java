package modularmachines.common.network;

import java.io.IOException;
import com.google.common.base.Preconditions;

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

import modularmachines.common.network.packets.IPacket;
import modularmachines.common.network.packets.IPacketHandlerClient;
import modularmachines.common.network.packets.IPacketHandlerServer;
import modularmachines.common.network.packets.PacketAssemblerPosition;
import modularmachines.common.network.packets.PacketSelectModule;
import modularmachines.common.network.packets.PacketSelectModulePage;
import modularmachines.common.network.packets.PacketSyncHandlerState;
import modularmachines.common.network.packets.PacketSyncMode;
import modularmachines.common.network.packets.PacketSyncModule;
import modularmachines.common.network.packets.PacketUpdateModule;
import modularmachines.common.utils.Log;

public class PacketHandler {

	public static final String channelId = "MM";
	private final static FMLEventChannel channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(channelId);
	
	public PacketHandler() {
		channel.register(this);
		registerClientPacket(PacketId.ASSEMBLER_POSITION, new PacketAssemblerPosition.Handler());
		registerServerPacket(PacketId.ASSEMBLER_POSITION, new PacketAssemblerPosition.Handler());
		registerClientPacket(PacketId.SELECT_MODULE, new PacketSelectModule.Handler());
		registerServerPacket(PacketId.SELECT_MODULE, new PacketSelectModule.Handler());
		registerClientPacket(PacketId.SELECT_PAGE, new PacketSelectModulePage.Handler());
		registerServerPacket(PacketId.SELECT_PAGE, new PacketSelectModulePage.Handler());
		registerClientPacket(PacketId.SYNC_HANDLER_STATE, new PacketSyncHandlerState.Handler());
		registerServerPacket(PacketId.SYNC_HANDLER_STATE, new PacketSyncHandlerState.Handler());
		registerClientPacket(PacketId.SYNC_MODULE, new PacketSyncModule.Handler());
		registerClientPacket(PacketId.UPDATE_MODULE, new PacketUpdateModule.Handler());
		registerClientPacket(PacketId.SYNC_MODE, new PacketSyncMode.Handler());
		registerServerPacket(PacketId.SYNC_MODE, new PacketSyncMode.Handler());
		/*registerClientPacket(new PacketModuleCleaner());
		registerServerPacket(new PacketModuleCleaner());
		registerClientPacket(new PacketSyncRedstoneMode());
		registerServerPacket(new PacketSyncRedstoneMode());
		registerClientPacket(new PacketSyncToolMode());
		registerServerPacket(new PacketSyncToolMode());
		registerClientPacket(new PacketSyncRedstoneMode());
		registerServerPacket(new PacketSyncRedstoneMode());
		registerClientPacket(new PacketSyncPermission());
		registerServerPacket(new PacketSyncPermission());
		registerClientPacket(new PacketSyncHeatBuffer());*/
	}

	public static void registerClientPacket(PacketId packetID, IPacketHandlerClient packet) {
		packetID.setHandlerClient(packet);
	}

	public static void registerServerPacket(PacketId packetID, IPacketHandlerServer packet) {
		packetID.setHandlerServer(packet);
	}

	public static void sendToNetwork(IPacket packet, BlockPos pos, WorldServer world) {
		if (packet == null) {
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
					sendToPlayer(packet, player);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static void sendToServer(IPacket packet) {
		NetHandlerPlayClient netHandler = Minecraft.getMinecraft().getConnection();
		if (netHandler != null) {
			netHandler.sendPacket(packet.getPacket());
		}
	}

	public static void sendToPlayer(IPacket packet, EntityPlayer entityplayer) {
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
		PacketBufferMM data = new PacketBufferMM(event.getPacket().payload());
		EntityPlayerMP player = ((NetHandlerPlayServer) event.getHandler()).playerEntity;
		
		byte packetIdOrdinal = data.readByte();
		PacketId packetId = PacketId.VALUES[packetIdOrdinal];
		IPacketHandlerServer packetHandler = packetId.getServerHandler();
		checkThreadAndEnqueue(packetHandler, data, player, player.getServerWorld());
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPacket(ClientCustomPacketEvent event) {
		PacketBufferMM data = new PacketBufferMM(event.getPacket().payload());
		
		byte packetIdOrdinal = data.readByte();
		PacketId packetId = PacketId.VALUES[packetIdOrdinal];
		IPacketHandlerClient packetHandler = packetId.getClientHandler();
		checkThreadAndEnqueue(packetHandler, data, Minecraft.getMinecraft());
	}

	@SideOnly(Side.CLIENT)
	private static void checkThreadAndEnqueue(final IPacketHandlerClient packet, final PacketBufferMM data, IThreadListener threadListener) {
		if (!threadListener.isCallingFromMinecraftThread()) {
			threadListener.addScheduledTask(() -> {
				try {
					EntityPlayer player = Minecraft.getMinecraft().player;
					Preconditions.checkNotNull(player, "Tried to send data to client before the player exists.");
					packet.onPacketData(data, player);
				} catch (IOException e) {
					Log.err("Network Error", e);
				}
			});
		}
	}

	private static void checkThreadAndEnqueue(final IPacketHandlerServer packet, final PacketBufferMM data, final EntityPlayerMP player, IThreadListener threadListener) {
		if (!threadListener.isCallingFromMinecraftThread()) {
			threadListener.addScheduledTask(() -> {
				try {
					packet.onPacketData(data, player);
				} catch (IOException e) {
					Log.err("Network Error", e);
				}
			});
		}
	}
}
