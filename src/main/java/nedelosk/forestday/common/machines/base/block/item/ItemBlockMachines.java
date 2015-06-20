package nedelosk.forestday.common.machines.base.block.item;

import nedelosk.forestday.common.items.blocks.ItemBlockForestday;
import nedelosk.forestday.common.machines.base.block.BlockMachines;
import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockMachines extends ItemBlockForestday {

	public ItemBlockMachines(Block block) {
		super(block);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return NRegistry.setUnlocalizedBlockName(((BlockMachines)getBlock()).blockName + "." + stack.getItemDamage(), "fd");
	}

}
