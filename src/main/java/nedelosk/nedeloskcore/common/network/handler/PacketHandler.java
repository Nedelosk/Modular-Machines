package nedelosk.nedeloskcore.common.network.handler;

import nedelosk.nedeloskcore.common.network.packets.PacketBlueprint;
import nedelosk.nedeloskcore.common.network.packets.PacketTilePlan;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
	
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("NedeloskCore".toLowerCase());

    private static int ID = 0;
    
    public static void preInit()
    {
    	INSTANCE.registerMessage(PacketBlueprintHandler.class, PacketBlueprint.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(PacketTilePlanHandler.class, PacketTilePlan.class, nextID(), Side.SERVER);
    }

    public static int nextID()
    {
        return ID++;
    }
	
}
