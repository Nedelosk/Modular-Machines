package de.nedelosk.modularmachines.common.network.packets;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class AbstractPacketThreadsafe extends AbstractPacket {

	@Override
	public final IMessage handleClient(final NetHandlerPlayClient netHandler) {
		IThreadListener listener = FMLCommonHandler.instance().getWorldThread(netHandler);
        if (!listener.isCallingFromMinecraftThread()){
			listener.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					handleClientSafe(netHandler);
				}
			});
        }
		return null;
	}

	@Override
	public final IMessage handleServer(final NetHandlerPlayServer netHandler) {
		IThreadListener listener = FMLCommonHandler.instance().getWorldThread(netHandler);
        if (!listener.isCallingFromMinecraftThread()){
			listener.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					handleServerSafe(netHandler);
				}
			});
        }
		return null;
	}

	abstract void handleClientSafe(NetHandlerPlayClient netHandler);

	abstract void handleServerSafe(NetHandlerPlayServer netHandler);
}
