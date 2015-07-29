package nedelosk.modularmachines.client.renderers;

import nedelosk.modularmachines.client.proxy.ClientProxy;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemModularMachinesRenderer implements IItemRenderer {

	public TileModularMachinesRenderer rendererCampfire;
	
	public ItemModularMachinesRenderer() {
		this.rendererCampfire = (TileModularMachinesRenderer )ClientProxy.getRenderer(TileModularAssembler.class);
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
		rendererCampfire.renderItem();
	}

}
