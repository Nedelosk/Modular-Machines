package modularmachines.common.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import modularmachines.common.blocks.IBlockWithMeta;
import modularmachines.common.core.Registry;

public class ItemBlockForest extends ItemBlock {

	public ItemBlockForest(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int i) {
		return i;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		String prefix = Integer.toString(itemstack.getItemDamage());
		if (block instanceof IBlockWithMeta) {
			IBlockWithMeta metaBlock = (IBlockWithMeta) block;
			int meta = itemstack.getMetadata();
			prefix = metaBlock.getNameFromMeta(meta);
		}
		return Registry.setUnlocalizedItemName(getBlock().getUnlocalizedName() + "." + prefix);
	}
}
