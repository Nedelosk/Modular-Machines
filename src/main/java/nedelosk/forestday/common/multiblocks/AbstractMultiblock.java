package nedelosk.forestday.common.multiblocks;

import cpw.mods.fml.common.FMLCommonHandler;
import nedelosk.forestday.api.ForestDayApi;
import nedelosk.forestday.api.multiblocks.IMultiblock;
import nedelosk.forestday.client.renderer.tile.TileMultiblockRenderer;

public abstract class AbstractMultiblock implements IMultiblock {

	public AbstractMultiblock() {
		if (createPatterns() != null)
			ForestDayApi.addMultiblockPattern(getMultiblockName(), createPatterns());
		else
			ForestDayApi.addMultiblockPattern(getMultiblockName(), createPattern());
		if (FMLCommonHandler.instance().getEffectiveSide().isClient())
			if (getRenderer() != null)
				TileMultiblockRenderer.registerRenderer(getClass(), getRenderer());
		ForestDayApi.registerMuliblock(getMultiblockName(), this);
	}

}
