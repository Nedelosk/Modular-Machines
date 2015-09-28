package nedelosk.modularmachines.common.network.packets;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import nedelosk.modularmachines.common.network.packets.machine.PacketModular;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularAssemblerSelection;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularNBT;
import nedelosk.modularmachines.common.network.packets.techtree.PacketEntryComplete;
import nedelosk.modularmachines.common.network.packets.techtree.PacketEntryCompleteClient;
import nedelosk.modularmachines.common.network.packets.techtree.PacketSyncData;
import nedelosk.modularmachines.common.network.packets.techtree.PacketSyncDataClient;
import nedelosk.modularmachines.common.network.packets.techtree.editor.PacketTechTreeEditor;

public class PacketHandler {
	
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("modularmachines");

    private static int ID = 0;
    
    public static void preInit()
    {
    	INSTANCE.registerMessage(PacketModular.class, PacketModular.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(PacketModularNBT.class, PacketModularNBT.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(PacketModularNBT.class, PacketModularNBT.class, nextID(), Side.CLIENT);
    	
    	//Assembler
    	INSTANCE.registerMessage(PacketModularAssemblerSelection.class, PacketModularAssemblerSelection.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(PacketModularAssemblerSelection.class, PacketModularAssemblerSelection.class, nextID(), Side.SERVER);
    	
    	//TechTree
    	INSTANCE.registerMessage(PacketEntryComplete.class, PacketEntryComplete.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(PacketEntryCompleteClient.class, PacketEntryCompleteClient.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(PacketSyncData.class, PacketSyncData.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(PacketSyncDataClient.class, PacketSyncDataClient.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(PacketTechTreeEditor.class, PacketTechTreeEditor.class, nextID(), Side.SERVER);
    }

    public static int nextID()
    {
        return ID++;
    }
	
}
