package nedelosk.modularmachines.client.renderers.item;

import nedelosk.modularmachines.client.proxy.ClientProxy;
import nedelosk.modularmachines.client.renderers.tile.TileRendererModular;
import nedelosk.modularmachines.client.renderers.tile.TileRendererModularAssembler;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererModular implements IItemRenderer {

	public TileRendererModular modular;
	
	public ItemRendererModular() {
		this.modular = (TileRendererModular )ClientProxy.getRenderer(TileModular.class);
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
