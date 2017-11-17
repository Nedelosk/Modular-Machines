package modularmachines.common.core.managers;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

import net.minecraftforge.fml.common.registry.GameRegistry;

import modularmachines.common.blocks.BlockMachine;
import modularmachines.common.blocks.BlockMetalBlock;
import modularmachines.common.blocks.BlockModuleStorage;
import modularmachines.common.blocks.BlockOre;
import modularmachines.common.blocks.tile.TileEntityMachine;
import modularmachines.common.blocks.tile.TileModuleStorage;
import modularmachines.common.core.Registry;
import modularmachines.common.items.blocks.ItemBlockForest;
import modularmachines.common.items.blocks.ItemBlockMachine;
import modularmachines.common.items.blocks.ItemBlockMetalBlock;

public class BlockManager {

	public static BlockMachine blockMachine;
	public static BlockModuleStorage moduleStorage;
	public static BlockOre blockOres;
	public static BlockMetalBlock blockMetalBlocks;

	public static void registerBlocks() {
		blockOres = new BlockOre();
		register(blockOres, new ItemBlockForest(blockOres));
		blockMetalBlocks = new BlockMetalBlock();
		register(blockMetalBlocks, new ItemBlockMetalBlock(blockMetalBlocks));
		blockMachine = new BlockMachine();
		register(blockMachine, new ItemBlockMachine(blockMachine));
		moduleStorage = new BlockModuleStorage();
		register(moduleStorage, new ItemBlockForest(moduleStorage));
	}

	public static void registerTiles() {
		GameRegistry.registerTileEntity(TileEntityMachine.class, "modularmachines.machine");
		GameRegistry.registerTileEntity(TileModuleStorage.class, "modularmachines.module");
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
