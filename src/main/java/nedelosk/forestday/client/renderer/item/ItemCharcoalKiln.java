package nedelosk.forestday.client.renderer.item;

import nedelosk.forestcore.library.utils.RenderUtil;
import nedelosk.forestday.api.crafting.WoodType;
import nedelosk.forestday.modules.ModuleCoal;
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
		WoodType typeWood = ModuleCoal.readFromStack(item);
		if (typeWood != null) {
			RenderBlocks.getInstance().renderBlockAsItem(Block.getBlockFromItem(typeWood.getWood().getItem()), typeWood.getWood().getItemDamage(), 1.0F);
		}
	}
}
