package nedelosk.modularmachines.common.network.packets;

import nedelosk.forestday.common.core.Defaults;
import nedelosk.modularmachines.common.ModularMachines;
import nedelosk.modularmachines.common.network.packets.assembler.PacketModularAssembler;
import nedelosk.modularmachines.common.network.packets.assembler.PacketModularAssemblerBookmark;
import nedelosk.modularmachines.common.network.packets.assembler.PacketModularAssemblerBuildMachine;
import nedelosk.modularmachines.common.network.packets.machine.PacketModularMachine;
import nedelosk.modularmachines.common.network.packets.machine.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
	
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModularMachines.class.getName().toLowerCase());

    private static int ID = 0;
    
    public static void preInit()
    {
    	INSTANCE.registerMessage(PacketModularAssembler.class, PacketModularAssembler.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(PacketModularMachine.class, PacketModularMachine.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(PacketModularMachineNBT.class, PacketModularMachineNBT.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(PacketModularMachineNBT.class, PacketModularMachineNBT.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(PacketModularAssembler.class, PacketModularAssembler.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(PacketModularAssemblerBookmark.class, PacketModularAssemblerBookmark.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(PacketModularAssemblerBuildMachine.class, PacketModularAssemblerBuildMachine.class, nextID(), Side.SERVER);
    }

    public static int nextID()
    {
        return ID++;
    }
	
}
