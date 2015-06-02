package nedelosk.forestbotany.client.renderers.items;

import nedelosk.forestbotany.client.renderers.tile.TileInfuserBaseRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemInfuserBase implements IItemRenderer {

	public TileInfuserBaseRenderer renderer;
	
	public ItemInfuserBase(TileInfuserBaseRenderer renderer) {
		this.renderer = renderer;
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		renderer.renderItem(item);
	}

}
