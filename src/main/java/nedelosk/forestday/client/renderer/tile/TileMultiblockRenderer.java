package nedelosk.forestday.client.renderer.tile;

import java.util.HashMap;

import nedelosk.forestday.api.multiblocks.IMultiblock;
import nedelosk.forestday.common.multiblocks.AbstractMultiblock;
import nedelosk.forestday.common.multiblocks.TileMultiblockBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class TileMultiblockRenderer extends TileEntitySpecialRenderer {

	private static HashMap<Class<? extends AbstractMultiblock>, TileEntitySpecialRenderer> renderers = new HashMap<Class<? extends AbstractMultiblock>, TileEntitySpecialRenderer>();

	public static void registerRenderer(Class<? extends AbstractMultiblock> multiblock,
			TileEntitySpecialRenderer renderer) {
		renderers.put(multiblock, renderer);
	}

	public static TileEntitySpecialRenderer getRenderer(Class<? extends IMultiblock> multiblock) {
		return renderers.get(multiblock);
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float p_147500_8_) {
		if (tile instanceof TileMultiblockBase) {
			TileMultiblockBase base = (TileMultiblockBase) tile;
			if (base.master != null && base.master.getMultiblock() != null || base.isMaster && base.isMultiblock)
				if (base.multiblock != null && getRenderer(base.multiblock.getClass()) != null && base.isMultiblock) {
					renderers.get(base.multiblock.getClass()).renderTileEntityAt(base, x, y, z, p_147500_8_);
				} else if (base.master != null && base.master.getMultiblock() != null
						&& TileMultiblockRenderer.getRenderer(base.master.getMultiblock().getClass()) != null) {
					renderers.get(base.master.getMultiblock().getClass()).renderTileEntityAt(base, x, y, z,
							p_147500_8_);
				}
		}
	}

}
