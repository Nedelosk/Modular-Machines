package nedelosk.forestday.machines.kiln.render;

import nedelosk.forestday.common.machines.brick.kiln.TileKiln;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RendererKilnItem implements IItemRenderer {

	private TileEntitySpecialRenderer render;

	
	public RendererKilnItem(TileEntitySpecialRenderer renderer) {
        this.render = renderer;
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
			this.render.renderTileEntityAt(new TileKiln(), 0.0D, 0.0D, 0.0D, 0.0F);
	}

}
