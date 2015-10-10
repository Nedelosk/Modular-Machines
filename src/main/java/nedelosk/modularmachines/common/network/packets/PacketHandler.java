package nedelosk.modularmachines.common.network.packets;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import nedelosk.modularmachines.common.network.packets.machine.PacketModular;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularAssemblerSelection;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularSelection;

public class PacketHandler {
	
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("modularmachines");

    private static int ID = 0;
    
    public static void preInit()
    {
    	INSTANCE.registerMessage(PacketModular.class, PacketModular.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(PacketModularSelection.class, PacketModularSelection.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(PacketModularSelection.class, PacketModularSelection.class, nextID(), Side.CLIENT);
    	
    	//Assembler
    	INSTANCE.registerMessage(PacketModularAssemblerSelection.class, PacketModularAssemblerSelection.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(PacketModularAssemblerSelection.class, PacketModularAssemblerSelection.class, nextID(), Side.SERVER);
    }

    public static int nextID()
    {
        return ID++;
    }
	
}
