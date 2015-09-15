package nedelosk.nedeloskcore.client.renderer.tile;

import java.util.HashMap;

import nedelosk.nedeloskcore.common.blocks.multiblocks.AbstractMultiblock;
import nedelosk.nedeloskcore.common.blocks.multiblocks.TileMultiblockBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class TileMultiblockRenderer extends TileEntitySpecialRenderer {

	private static HashMap<Class<? extends AbstractMultiblock>, TileEntitySpecialRenderer> renderers = new HashMap<Class<? extends AbstractMultiblock>, TileEntitySpecialRenderer>();
	
	public static void registerRenderer(Class<? extends AbstractMultiblock> multiblock, TileEntitySpecialRenderer renderer)
	{
		renderers.put(multiblock, renderer);
	}
	
	public static TileEntitySpecialRenderer getRenderer(Class<? extends AbstractMultiblock> multiblock)
	{
		return renderers.get(multiblock);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float p_147500_8_) {
		if(tile instanceof TileMultiblockBase)
		{
			TileMultiblockBase base = (TileMultiblockBase) tile;
	    	if(base.master != null && base.master.multiblock != null || base.isMaster &&  base.isMultiblock)
	    		if(base.multiblock != null && TileMultiblockRenderer.getRenderer(base.multiblock.getClass()) != null && base.isMultiblock)
	    		{
	    			renderers.get(base.multiblock.getClass()).renderTileEntityAt(base, x, y, z, p_147500_8_);
	    		}
	    		else if(base.master != null && base.master.multiblock != null && TileMultiblockRenderer.getRenderer(base.master.multiblock.getClass()) != null)
	    		{
	    			renderers.get(base.master.multiblock.getClass()).renderTileEntityAt(base, x, y, z, p_147500_8_);
	    		}
		}
	}

}
