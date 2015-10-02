package nedelosk.modularmachines.client.renderers.item;

import org.lwjgl.opengl.GL11;

import nedelosk.modularmachines.common.blocks.BlockMetal;
import nedelosk.modularmachines.common.core.MMBlocks;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemMetal implements IItemRenderer {

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
		RenderBlocks.getInstance().useInventoryTint = false;
		int j = ((BlockMetal)MMBlocks.Metal_Blocks.block()).getRenderColor(item);
		float f1 = (j >> 16 & 255) / 255.0F;
		float f2 = (j >> 8 & 255) / 255.0F;
		float f3 = (j & 255) / 255.0F;
        GL11.glColor4f(f1, f2, f3, 1.0F);
		RenderBlocks.getInstance().renderBlockAsItem(MMBlocks.Metal_Blocks.block(), 0, 1.0F);
		RenderBlocks.getInstance().useInventoryTint = true;
	}

}
