package nedelosk.forestday.common.network.packets;

import nedelosk.forestday.common.core.Defaults;
import nedelosk.forestday.common.network.packets.machines.PacketSwitchMode;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
	
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Defaults.MOD_ID.toLowerCase());

    private static int ID = 0;
    
    public static void preInit()
    {
    	INSTANCE.registerMessage(PacketSwitchMode.class, PacketSwitchMode.class, nextID(), Side.SERVER);
    }

    public static int nextID()
    {
        return ID++;
    }
	
}
