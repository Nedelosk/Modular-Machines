package de.nedelosk.forestmods.client.render.item;

import de.nedelosk.forestmods.client.core.ClientProxy;
import de.nedelosk.forestmods.client.render.tile.TileModularAssemblerRenderer;
import de.nedelosk.forestmods.common.blocks.tile.TileModularAssembler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemModularAssemblerRenderer implements IItemRenderer {

	public TileModularAssemblerRenderer assembler;

	public ItemModularAssemblerRenderer() {
		this.assembler = (TileModularAssemblerRenderer) ClientProxy.getRenderer(TileModularAssembler.class);
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
		assembler.renderItem();
	}
}
