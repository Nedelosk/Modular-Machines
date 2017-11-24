package modularmachines.common.compat.theoneprobe;

import net.minecraftforge.fml.common.event.FMLInterModComms;

//TODO: Add Better Compat System
public class TheOneProbeCompat {
	
	public static void postInit() {
		FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", ProbeProvider.class.getName());
	}
}
