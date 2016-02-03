package de.nedelosk.forestmods.client.render.item;

import de.nedelosk.forestmods.client.core.ClientProxy;
import de.nedelosk.forestmods.client.render.tile.TileCampfireRenderer;
import de.nedelosk.forestmods.client.render.tile.TileWorkbenchRenderer;
import de.nedelosk.forestmods.common.blocks.tile.TileCampfire;
import de.nedelosk.forestmods.common.blocks.tile.TileWorkbench;
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
