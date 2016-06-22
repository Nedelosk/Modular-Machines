package de.nedelosk.modularmachines.common.items;

import de.nedelosk.modularmachines.common.core.Registry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

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
		return Registry.setUnlocalizedItemName(getBlock().getUnlocalizedName() + "." + itemstack.getItemDamage());
	}
}
