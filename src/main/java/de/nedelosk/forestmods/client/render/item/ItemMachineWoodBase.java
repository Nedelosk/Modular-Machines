package de.nedelosk.forestmods.client.render.item;

import de.nedelosk.forestmods.client.core.ClientProxy;
import de.nedelosk.forestmods.client.render.tile.TileCampfireRenderer;
import de.nedelosk.forestmods.common.blocks.tile.TileCampfire;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemMachineWoodBase implements IItemRenderer {

	public TileCampfireRenderer rendererCampfire;

	public ItemMachineWoodBase() {
		this.rendererCampfire = (TileCampfireRenderer) ClientProxy.getRenderer(TileCampfire.class);
	}

	@Override
	public boolean handleRenderType(ItemStack arg0, ItemRenderType arg1) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType arg0, ItemStack stack, Object... arg2) {
		rendererCampfire.renderItem(stack.getItemDamage(), 0.0D, 0.0D, 0.0D, 0.0F);
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType arg0, ItemStack arg1, ItemRendererHelper arg2) {
		return true;
	}
}
