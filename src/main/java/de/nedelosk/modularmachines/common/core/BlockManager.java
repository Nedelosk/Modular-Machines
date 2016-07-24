package de.nedelosk.modularmachines.common.core;

import de.nedelosk.modularmachines.common.blocks.BlockMetalBlock;
import de.nedelosk.modularmachines.common.blocks.BlockModular;
import de.nedelosk.modularmachines.common.blocks.BlockOre;
import de.nedelosk.modularmachines.common.blocks.tile.TileModular;
import de.nedelosk.modularmachines.common.items.blocks.ItemBlockForest;
import de.nedelosk.modularmachines.common.items.blocks.ItemBlockMetalBlock;
import de.nedelosk.modularmachines.common.items.blocks.ItemBlockModular;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockManager {

	public static BlockModular blockModular;
	public static BlockOre blockOres;
	public static BlockMetalBlock blockMetalBlocks;

	public static void registerBlocks() {
		blockOres = new BlockOre();
		register(blockOres, new ItemBlockForest(blockOres));
		blockMetalBlocks = new BlockMetalBlock();
		register(blockMetalBlocks, new  ItemBlockMetalBlock(blockMetalBlocks));
		blockModular = new BlockModular();
		register(blockModular, new ItemBlockModular(blockModular));
	}

	public static void registerTiles() {
		GameRegistry.registerTileEntity(TileModular.class, "forestmods.modular");
	}

	public static <B extends Block> B register(B block, ItemBlock item) {
		String name = block.getUnlocalizedName().replace("tile.", "").replace("forest.tile.", "");
		Registry.register(block, name);
		Registry.register(item, name);
		return block;
	}
}
