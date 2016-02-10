package de.nedelosk.forestmods.client.render.item;

import de.nedelosk.forestmods.client.core.ClientProxy;
import de.nedelosk.forestmods.client.render.tile.TileTransportRenderer;
import de.nedelosk.forestmods.common.transport.TileEntityTransport;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemTransport implements IItemRenderer {

	public TileTransportRenderer base;

	public ItemTransport() {
		base = (TileTransportRenderer) ClientProxy.getRenderer(TileEntityTransport.class);
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
		if (item.getItemDamage() == 0) {
			base.renderItem();
		} else {
		}
	}
}
