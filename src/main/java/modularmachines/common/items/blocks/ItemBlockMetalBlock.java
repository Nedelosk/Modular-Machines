package modularmachines.common.items.blocks;

import modularmachines.common.blocks.BlockMetalBlock.ComponentTypes;
import modularmachines.common.utils.content.IColoredItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockMetalBlock extends ItemBlockForest implements IColoredItem {

	public ItemBlockMetalBlock(Block block) {
		super(block);
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		return ComponentTypes.values()[stack.getItemDamage()].color;
	}
}
