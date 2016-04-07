package de.nedelosk.forestmods.client.render.item;

import de.nedelosk.forestmods.client.core.ClientProxy;
import de.nedelosk.forestmods.client.render.tile.TileCampfireRenderer;
import de.nedelosk.forestmods.common.blocks.tile.TileCampfire;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class CampfireItemsRenderer implements IItemRenderer {

	public TileCampfireRenderer rendererCampfire;
	public String texture;

	public CampfireItemsRenderer(String texture) {
		this.rendererCampfire = (TileCampfireRenderer) ClientProxy.getRenderer(TileCampfire.class);
		this.texture = texture;
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
		rendererCampfire.renderItem(item.getItemDamage(), texture);
	}
}
