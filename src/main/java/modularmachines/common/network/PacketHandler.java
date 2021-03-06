package modularmachines.common.network;

import com.google.common.base.Preconditions;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.management.PlayerChunkMap;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
import modularmachines.common.network.packets.PacketExtractModule;
import modularmachines.common.network.packets.PacketInjectModule;
import modularmachines.common.network.packets.PacketUpdateModule;
import modularmachines.common.network.packets.PacketUpdateModuleContainer;
import modularmachines.common.utils.Log;

public class PacketHandler {
	
	public static final String channelId = "MM";
	private final static FMLEventChannel channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(channelId);
	
	public PacketHandler() {
		channel.register(this);
		registerClientPacket(PacketId.UPDATE_MODULE, new PacketUpdateModule.Handler());
		registerClientPacket(PacketId.ADD_MODULE, new PacketInjectModule.Handler());
		registerClientPacket(PacketId.REMOVE_MODULE, new PacketExtractModule.Handler());
		registerClientPacket(PacketId.UPDATE_MODULE_CONTAINER, new PacketUpdateModuleContainer.Handler());
	}
	
	private static void registerClientPacket(PacketId packetID, IPacketHandlerClient packet) {
		packetID.setHandlerClient(packet);
	}
	
	private static void registerServerPacket(PacketId packetID, IPacketHandlerServer packet) {
		packetID.setHandlerServer(packet);
	}
	
	public static void sendToNetwork(IPacket packet, BlockPos pos, World world) {
		if (!(world instanceof WorldServer)) {
			return;
		}
		WorldServer worldServer = (WorldServer) world;
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
		EntityPlayerMP player = ((NetHandlerPlayServer) event.getHandler()).player;
		
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
