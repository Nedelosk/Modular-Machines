package de.nedelosk.modularmachines.common.items.blocks;

import de.nedelosk.modularmachines.common.blocks.BlockMetalBlock.ComponentTypes;
import de.nedelosk.modularmachines.common.utils.IColoredItem;
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
