package nedelosk.nedeloskcore.common.blocks.multiblocks;

import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.api.multiblock.IMultiblock;
import nedelosk.nedeloskcore.client.renderer.tile.TileMultiblockRenderer;

public abstract class AbstractMultiblock implements IMultiblock {

	public AbstractMultiblock() {
		if(createPatterns() != null)
			NCoreApi.addMultiblockPattern(getMultiblockName(), createPatterns());
		else
			NCoreApi.addMultiblockPattern(getMultiblockName(), createPattern());
		if(getRenderer() != null)
			TileMultiblockRenderer.registerRenderer(getClass(), getRenderer());
		NCoreApi.registerMuliblock(getMultiblockName(), this);
	}
	
}
