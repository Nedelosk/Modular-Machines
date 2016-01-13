package nedelosk.forestday.client.renderer.item;

import nedelosk.forestday.client.proxy.ClientProxy;
import nedelosk.forestday.client.renderer.tile.TileCampfireRenderer;
import nedelosk.forestday.client.renderer.tile.TileWorkbenchRenderer;
import nedelosk.forestday.common.blocks.tiles.TileCampfire;
import nedelosk.forestday.common.blocks.tiles.TileWorkbench;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemMachineWoodBase implements IItemRenderer {

	public TileWorkbenchRenderer rendererWorkbench;
	public TileCampfireRenderer rendererCampfire;

	public ItemMachineWoodBase() {
		this.rendererWorkbench = (TileWorkbenchRenderer) ClientProxy.getRenderer(TileWorkbench.class);
		this.rendererCampfire = (TileCampfireRenderer) ClientProxy.getRenderer(TileCampfire.class);
	}

	@Override
	public boolean handleRenderType(ItemStack arg0, ItemRenderType arg1) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType arg0, ItemStack stack, Object... arg2) {
		if (stack.getItemDamage() == 0) {
			rendererCampfire.renderItem(stack.getItemDamage(), 0.0D, 0.0D, 0.0D, 0.0F);
		} else if (stack.getItemDamage() == 1 || stack.getItemDamage() == 2) {
			rendererWorkbench.renderItem(stack.getItemDamage(), 0.0D, 0.0D, 0.0D, 0.0F);
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType arg0, ItemStack arg1, ItemRendererHelper arg2) {
		return true;
	}
}
