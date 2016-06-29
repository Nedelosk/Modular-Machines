package de.nedelosk.modularmachines.common.items;

import de.nedelosk.modularmachines.common.blocks.IBlockWithMeta;
import de.nedelosk.modularmachines.common.core.Registry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

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
		if(block instanceof IBlockWithMeta){
			IBlockWithMeta metaBlock = (IBlockWithMeta) block;
			int meta = itemstack.getMetadata();
			prefix =  metaBlock.getNameFromMeta(meta);
		}
		return Registry.setUnlocalizedItemName(getBlock().getUnlocalizedName() + "." + prefix);
	}
}
