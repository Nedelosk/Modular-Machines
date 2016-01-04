package nedelosk.forestday.common.items.blocks;

import nedelosk.forestcore.library.core.Registry;
import nedelosk.forestday.modules.ModuleCore;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockMultiblock extends ItemBlockForestDay {

	public ItemBlockMultiblock(Block block) {
		super(block);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return Registry.setUnlocalizedItemName("multiblock"
				+ (Block.getBlockFromItem(itemstack.getItem()) == ModuleCore.BlockManager.Multiblock_Valve.block()
						? "_valve" : ""),
				"fd");
	}

}
