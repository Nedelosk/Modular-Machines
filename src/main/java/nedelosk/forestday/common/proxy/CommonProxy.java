package nedelosk.forestday.common.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import nedelosk.forestcore.library.multiblock.MultiblockServerTickHandler;

public class CommonProxy {

	public void registerRenderers() {
	}

	public void registerTickHandlers() {
		FMLCommonHandler.instance().bus().register(new MultiblockServerTickHandler());
	}

}
