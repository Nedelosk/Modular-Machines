package nedelosk.forestday.common.items.blocks;

import nedelosk.forestday.common.core.managers.FBlockManager;
import nedelosk.forestday.common.core.registry.FRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockMultiblock extends ItemBlockForest {

	public ItemBlockMultiblock(Block block) {
		super(block);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return FRegistry.setUnlocalizedItemName("multiblock" + (Block.getBlockFromItem(itemstack.getItem()) == FBlockManager.Multiblock_Valve.block() ? "_valve" : ""), "fd");
	}

}
