package de.nedelosk.modularmachines.common.network.packets;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class AbstractPacket implements IMessage {

	abstract IMessage handleClient(NetHandlerPlayClient netHandler);

	abstract IMessage handleServer(NetHandlerPlayServer netHandler);

}
