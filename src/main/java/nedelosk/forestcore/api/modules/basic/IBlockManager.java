package nedelosk.forestcore.api.modules.basic;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IBlockManager extends IObjectManager<Block> {
	
	Item item();
	
	IBlockManager register(Block block, Class<? extends ItemBlock> itemClass);

	boolean isItemEqual(ItemStack stack);

	boolean isBlockEqual(Block i);

	boolean isBlockEqual(World world, int x, int y, int z);
	
}
