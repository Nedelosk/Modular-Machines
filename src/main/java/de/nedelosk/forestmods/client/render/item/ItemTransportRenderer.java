package de.nedelosk.forestmods.client.render.item;

import de.nedelosk.forestmods.client.core.ClientProxy;
import de.nedelosk.forestmods.client.render.tile.TileTransportNodeRenderer;
import de.nedelosk.forestmods.client.render.tile.TileTransportRenderer;
import de.nedelosk.forestmods.common.transport.TileEntityTransport;
import de.nedelosk.forestmods.common.transport.node.TileEntityTransportNode;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemTransportRenderer implements IItemRenderer {

	public TileTransportRenderer base;
	public TileTransportNodeRenderer node;

	public ItemTransportRenderer() {
		base = (TileTransportRenderer) ClientProxy.getRenderer(TileEntityTransport.class);
		node = (TileTransportNodeRenderer) ClientProxy.getRenderer(TileEntityTransportNode.class);
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
			node.renderItem();
		}
	}
}
