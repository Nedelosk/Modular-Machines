package de.nedelosk.forestmods.client.render.item;

import de.nedelosk.forestmods.client.core.ClientProxy;
import de.nedelosk.forestmods.client.render.tile.TileModularMachineRenderer;
import de.nedelosk.forestmods.common.blocks.tile.TileModularMachine;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererModular implements IItemRenderer {

	public TileModularMachineRenderer modular;

	public ItemRendererModular() {
		this.modular = (TileModularMachineRenderer) ClientProxy.getRenderer(TileModularMachine.class);
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
		modular.renderTileEntityItem(item);
	}
}
