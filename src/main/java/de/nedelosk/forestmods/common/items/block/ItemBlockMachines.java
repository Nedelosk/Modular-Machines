package de.nedelosk.forestmods.common.items.block;

import de.nedelosk.forestcore.core.Registry;
import de.nedelosk.forestcore.items.ItemBlockForest;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockMachines extends ItemBlockForest {

	public ItemBlockMachines(Block block) {
		super(block);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Registry.setUnlocalizedBlockName("wood_base" + "." + stack.getItemDamage(), "fd");
	}
}