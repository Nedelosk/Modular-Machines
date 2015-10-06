package nedelosk.forestday.common.blocks.items;

import nedelosk.forestday.common.blocks.BlockMachines;
import nedelosk.forestday.common.items.blocks.ItemBlockForestday;
import nedelosk.nedeloskcore.common.core.registry.NCRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockMachines extends ItemBlockForestday {

	public ItemBlockMachines(Block block) {
		super(block);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return NCRegistry.setUnlocalizedBlockName(((BlockMachines)getBlock()).blockName + "." + stack.getItemDamage(), "fd");
	}

}
