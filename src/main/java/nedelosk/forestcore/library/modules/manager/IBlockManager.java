package nedelosk.forestcore.library.modules.manager;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IBlockManager extends IObjectManager<Block> {
	
	Item item();
	
	Block block();

	boolean isItemEqual(ItemStack stack);

	boolean isBlockEqual(Block i);

	boolean isBlockEqual(World world, int x, int y, int z);
	
}
