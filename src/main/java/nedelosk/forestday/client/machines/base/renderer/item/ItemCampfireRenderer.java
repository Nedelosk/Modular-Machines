package nedelosk.forestday.client.machines.base.renderer.item;

import nedelosk.forestday.client.machines.base.renderer.tile.TileCampfireRenderer;
import nedelosk.forestday.client.proxy.ClientProxy;
import nedelosk.forestday.common.machines.base.wood.campfire.TileCampfire;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemCampfireRenderer implements IItemRenderer {

	public TileCampfireRenderer rendererCampfire;
	public String texture;
	
	public ItemCampfireRenderer(String texture) {
		this.rendererCampfire = (TileCampfireRenderer)ClientProxy.getRenderer(TileCampfire.class);
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
