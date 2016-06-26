package de.nedelosk.modularmachines.common.core;

import de.nedelosk.modularmachines.common.blocks.BlockCasing;
import de.nedelosk.modularmachines.common.blocks.BlockMetalBlock;
import de.nedelosk.modularmachines.common.blocks.BlockModularAssembler;
import de.nedelosk.modularmachines.common.blocks.BlockModularMachine;
import de.nedelosk.modularmachines.common.blocks.BlockOre;
import de.nedelosk.modularmachines.common.blocks.BlockTransport;
import de.nedelosk.modularmachines.common.blocks.tile.TileModular;
import de.nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import de.nedelosk.modularmachines.common.items.ItemBlockForest;
import de.nedelosk.modularmachines.common.items.blocks.ItemBlockMetalBlock;
import de.nedelosk.modularmachines.common.items.blocks.ItemBlockModularMachine;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockManager {

	public static BlockCasing blockCasings;
	public static BlockModularMachine blockModular;
	public static BlockOre blockOres;
	public static BlockMetalBlock blockMetalBlocks;
	public static BlockTransport blockTransport;
	public static BlockModularAssembler blockAssembler;

	public static void registerBlocks() {
		blockOres = new BlockOre();
		register(blockOres, new ItemBlockForest(blockOres));
		blockMetalBlocks = new BlockMetalBlock();
		register(blockMetalBlocks, new  ItemBlockMetalBlock(blockMetalBlocks));
		// blockTransport = register(new BlockTransport(),
		blockCasings = new BlockCasing();
		register(blockCasings, new ItemBlockForest(blockCasings));
		blockModular = new BlockModularMachine();
		register(blockModular, new ItemBlockModularMachine(blockModular));
		blockAssembler = new BlockModularAssembler();
		register(blockAssembler,new ItemBlockForest(blockAssembler));
	}

	public static void registerTiles() {
		GameRegistry.registerTileEntity(TileModular.class, "forestmods.modular");
		GameRegistry.registerTileEntity(TileModularAssembler.class, "forestmods.modular.assembler");
	}

	public static <B extends Block> B register(B block, ItemBlock item) {
		String name = block.getUnlocalizedName().replace("tile.", "").replace("forest.tile.", "");
		Registry.register(block, name);
		Registry.register(item, name);
		return block;
	}
}
