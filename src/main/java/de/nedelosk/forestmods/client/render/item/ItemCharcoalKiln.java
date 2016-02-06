package de.nedelosk.forestmods.client.render.item;

import de.nedelosk.forestcore.utils.RenderUtil;
import de.nedelosk.forestmods.api.crafting.WoodType;
import de.nedelosk.forestmods.common.core.RecipeManager;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemCharcoalKiln implements IItemRenderer {

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
		RenderUtil.bindBlockTexture();
		WoodType typeWood = RecipeManager.readFromStack(item);
		if (typeWood != null) {
			RenderBlocks.getInstance().renderBlockAsItem(Block.getBlockFromItem(typeWood.getWood().getItem()), typeWood.getWood().getItemDamage(), 1.0F);
		}
	}
}
