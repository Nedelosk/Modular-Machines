package nedelosk.forestday.common.items.blocks;

import nedelosk.forestday.common.core.registry.FRegistry;
import nedelosk.forestday.common.modules.ModuleCore;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockMultiblock extends ItemBlockForestDay {

	public ItemBlockMultiblock(Block block) {
		super(block);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return FRegistry.setUnlocalizedItemName(
				"multiblock" + (Block.getBlockFromItem(itemstack.getItem()) == ModuleCore.BlockManager.Multiblock_Valve.block()
						? "_valve" : ""),
				"fd");
	}

}
