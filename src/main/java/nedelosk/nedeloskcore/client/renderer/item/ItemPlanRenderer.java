package nedelosk.nedeloskcore.client.renderer.item;

import nedelosk.nedeloskcore.client.renderer.tile.TilePlanRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemPlanRenderer implements IItemRenderer {

	TilePlanRenderer plan;
	
	public ItemPlanRenderer(TilePlanRenderer plan) {
		this.plan = plan;
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		plan.renderItem();
	}

}
