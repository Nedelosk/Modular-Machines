package nedelosk.modularmachines.client.renderers.item;

import nedelosk.modularmachines.client.proxy.ClientProxy;
import nedelosk.modularmachines.client.renderers.tile.TileRendererModularAssembler;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererModularAssembler implements IItemRenderer {

	public TileRendererModularAssembler assembler;

	public ItemRendererModularAssembler() {
		this.assembler = (TileRendererModularAssembler) ClientProxy.getRenderer(TileModularAssembler.class);
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
		assembler.renderItem(item);
	}
}
