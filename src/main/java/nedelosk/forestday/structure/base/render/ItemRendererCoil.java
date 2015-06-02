package nedelosk.forestday.structure.base.render;

import nedelosk.forestday.structure.base.blocks.tile.TileCoilGrinding;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererCoil implements IItemRenderer {

	private TileEntitySpecialRenderer renderer;
	
	public ItemRendererCoil(TileEntitySpecialRenderer tileEntitySpecialRenderer) {
		this.renderer = tileEntitySpecialRenderer;
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
		((RendererCoil)renderer).renderItem(item.getItemDamage());
	}

}
