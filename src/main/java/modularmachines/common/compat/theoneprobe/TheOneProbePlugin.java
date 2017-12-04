package modularmachines.common.compat.theoneprobe;

import net.minecraftforge.fml.common.event.FMLInterModComms;

import modularmachines.common.compat.ICompatPlugin;

//TODO: Add Better Compat System
public class TheOneProbePlugin implements ICompatPlugin {
	
	public void postInit() {
		FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", ProbeProvider.class.getName());
	}
}
