package modularmachines.common.core.managers;

import modularmachines.common.blocks.BlockMetalBlock;
import modularmachines.common.blocks.BlockModular;
import modularmachines.common.blocks.BlockModuleCrafter;
import modularmachines.common.blocks.BlockOre;
import modularmachines.common.blocks.tile.TileModular;
import modularmachines.common.blocks.tile.TileModuleCrafter;
import modularmachines.common.core.Registry;
import modularmachines.common.items.blocks.ItemBlockForest;
import modularmachines.common.items.blocks.ItemBlockMetalBlock;
import modularmachines.common.items.blocks.ItemBlockModular;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockManager {

	public static BlockModular blockModular;
	public static BlockOre blockOres;
	public static BlockMetalBlock blockMetalBlocks;
	public static BlockModuleCrafter blockModuleCrafter;

	public static void registerBlocks() {
		blockOres = new BlockOre();
		register(blockOres, new ItemBlockForest(blockOres));
		blockMetalBlocks = new BlockMetalBlock();
		register(blockMetalBlocks, new ItemBlockMetalBlock(blockMetalBlocks));
		blockModular = new BlockModular();
		register(blockModular, new ItemBlockModular(blockModular));
		blockModuleCrafter = new BlockModuleCrafter();
		register(blockModuleCrafter, new ItemBlockForest(blockModuleCrafter));
	}

	public static void registerTiles() {
		GameRegistry.registerTileEntity(TileModular.class, "forestmods.modular");
		GameRegistry.registerTileEntity(TileModuleCrafter.class, "forestmods.module.crafter");
	}

	public static <B extends Block> B register(B block) {
		String name = block.getUnlocalizedName().replace("tile.", "").replace("forest.tile.", "");
		Registry.register(block, name);
		return block;
	}

	public static <B extends Block> B register(B block, ItemBlock item) {
		String name = block.getUnlocalizedName().replace("tile.", "").replace("forest.tile.", "");
		Registry.register(block, name);
		Registry.register(item, name);
		return block;
	}
}
