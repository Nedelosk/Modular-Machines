package nedelosk.forestday.common.blocks.items;

import nedelosk.forestcore.library.core.Registry;
import nedelosk.forestday.common.blocks.BlockMachines;
import nedelosk.forestday.common.items.blocks.ItemBlockForestDay;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockMachines extends ItemBlockForestDay {

	public ItemBlockMachines(Block block) {
		super(block);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Registry.setUnlocalizedBlockName(((BlockMachines) getBlock()).blockName + "." + stack.getItemDamage(),
				"fd");
	}

}
