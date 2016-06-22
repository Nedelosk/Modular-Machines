package de.nedelosk.modularmachines.common.core;

import de.nedelosk.modularmachines.common.blocks.BlockCasing;
import de.nedelosk.modularmachines.common.blocks.BlockComponent;
import de.nedelosk.modularmachines.common.blocks.BlockModularAssembler;
import de.nedelosk.modularmachines.common.blocks.BlockModularMachine;
import de.nedelosk.modularmachines.common.blocks.BlockOre;
import de.nedelosk.modularmachines.common.blocks.BlockTransport;
import de.nedelosk.modularmachines.common.blocks.tile.TileModular;
import de.nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import de.nedelosk.modularmachines.common.items.ItemBlockForest;
import de.nedelosk.modularmachines.common.items.blocks.ItemBlockCampfire;
import de.nedelosk.modularmachines.common.items.blocks.ItemBlockModularMachine;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockManager {

	public static BlockCasing blockCasings;
	public static BlockModularMachine blockModular;
	public static BlockOre blockOres;
	public static BlockComponent blockMetalBlocks;
	public static BlockTransport blockTransport;
	public static BlockModularAssembler blockAssembler;

	public static void registerBlocks() {
		blockOres = new BlockOre();
		register(blockOres, new ItemBlockForest(blockOres));
		blockMetalBlocks = new BlockComponent(Material.IRON, "metal_block");
		register(blockMetalBlocks, new ItemBlockForest(blockMetalBlocks));
		// blockTransport = register(new BlockTransport(),
		blockMetalBlocks.addMetaData(0xCACECF, "tin", "Tin");
		blockMetalBlocks.addMetaData(0xCC6410, "copper", "Copper");
		blockMetalBlocks.addMetaData(0xCA9956, "bronze", "Bronze");
		blockMetalBlocks.addMetaData(0xA0A0A0, "steel", "Steel");
		blockMetalBlocks.addMetaData(0xA1A48C, "invar", "Invar");
		blockCasings = new BlockCasing(new String[] { "stone", "iron", "bronze" });
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
