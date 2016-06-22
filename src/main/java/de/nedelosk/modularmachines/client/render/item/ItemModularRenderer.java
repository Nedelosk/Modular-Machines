package de.nedelosk.modularmachines.client.render.item;

import de.nedelosk.modularmachines.client.core.ClientProxy;
import de.nedelosk.modularmachines.client.render.tile.TileModularMachineRenderer;
import de.nedelosk.modularmachines.common.blocks.tile.TileModular;
import net.minecraft.item.ItemStack;

public class ItemModularRenderer implements IItemRenderer {

	public TileModularMachineRenderer modular;

	public ItemModularRenderer() {
		this.modular = (TileModularMachineRenderer) ClientProxy.getRenderer(TileModular.class);
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
		modular.renderItem(item);
	}
}
