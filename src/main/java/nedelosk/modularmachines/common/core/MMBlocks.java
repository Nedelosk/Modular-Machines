package nedelosk.modularmachines.common.core;

import nedelosk.nedeloskcore.common.core.registry.NRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public enum MMBlocks {

	Ore_Others,
	Metal_Blocks,
	
	Modular_Assembler,
	Modular_Workbench,
	Modular_Machine;
	
	private Block block;

	public void registerBlock(Block block, Class<? extends ItemBlock> itemClass) {
		this.block = block;
		NRegistry.registerBlock(block, itemClass, block.getUnlocalizedName(), "fd");
	}

	public boolean isItemEqual(ItemStack stack) {
		return stack != null && isBlockEqual(Block.getBlockFromItem(stack.getItem()));
	}

	public boolean isBlockEqual(Block i) {
		return i != null && Block.isEqualTo(block, i);
	}

	public boolean isBlockEqual(World world, int x, int y, int z) {
		return isBlockEqual(world.getBlock(x, y, z));
	}

	public Item item() {
		return Item.getItemFromBlock(block);
	}

	public Block block() {
		return block;
	}

	public ItemStack getWildcard() {
		return getItemStack(1, OreDictionary.WILDCARD_VALUE);
	}

	public ItemStack getItemStack() {
		return getItemStack(1, 0);
	}

	public ItemStack getItemStack(int qty) {
		return getItemStack(qty, 0);
	}

	public ItemStack getItemStack(int qty, int meta) {
		if (block == null) {
			return null;
		}
		return new ItemStack(block, qty, meta);
	}

	public boolean setBlock(World world, int x, int y, int z, int meta, int flag) {
		return world.setBlock(x, y, z, block, meta, flag);
	}
}
