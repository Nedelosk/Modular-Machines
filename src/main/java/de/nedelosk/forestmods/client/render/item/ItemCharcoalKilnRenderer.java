package de.nedelosk.forestmods.client.render.item;

import de.nedelosk.forestmods.common.multiblocks.charcoal.CharcoalKilnHelper;
import de.nedelosk.forestmods.library.utils.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class ItemCharcoalKilnRenderer implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		ItemStack woodStack = CharcoalKilnHelper.getFromKiln(item);
		IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(woodStack, type);
		if (customRenderer != null) {
			return customRenderer.handleRenderType(woodStack, type);
		} else {
			return true;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		ItemStack woodStack = CharcoalKilnHelper.getFromKiln(item);
		IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(woodStack, type);
		if (customRenderer != null) {
			return customRenderer.shouldUseRenderHelper(type, woodStack, helper);
		} else {
			return true;
		}
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		RenderUtil.bindBlockTexture();
		ItemStack woodStack = CharcoalKilnHelper.getFromKiln(item);
		if (woodStack != null) {
			IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(woodStack, type);
			if (customRenderer != null) {
				customRenderer.renderItem(type, woodStack, data);
			} else {
				RenderBlocks.getInstance().renderBlockAsItem(Block.getBlockFromItem(woodStack.getItem()), woodStack.getItemDamage(), 1.0F);
			}
		}
	}
}
