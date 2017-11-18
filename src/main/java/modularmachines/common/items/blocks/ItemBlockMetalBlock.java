package modularmachines.common.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import modularmachines.common.blocks.BlockMetalBlock.Metals;
import modularmachines.common.utils.content.IColoredItem;

public class ItemBlockMetalBlock extends ItemBlockForest implements IColoredItem {
	
	public ItemBlockMetalBlock(Block block) {
		super(block);
	}
	
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		return Metals.values()[stack.getItemDamage()].color;
	}
}
