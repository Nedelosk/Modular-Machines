package de.nedelosk.modularmachines.common.items.blocks;

import de.nedelosk.modularmachines.common.core.Registry;
import de.nedelosk.modularmachines.common.items.ItemBlockForest;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockCampfire extends ItemBlockForest {

	public ItemBlockCampfire(Block block) {
		super(block);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Registry.setUnlocalizedBlockName("wood_base" + "." + stack.getItemDamage());
	}
}
