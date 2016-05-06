package de.nedelosk.forestmods.common.core;

import cpw.mods.fml.common.registry.GameRegistry;
import de.nedelosk.forestmods.common.blocks.BlockBlastFurnace;
import de.nedelosk.forestmods.common.blocks.BlockCampfire;
import de.nedelosk.forestmods.common.blocks.BlockCasing;
import de.nedelosk.forestmods.common.blocks.BlockCharcoalKiln;
import de.nedelosk.forestmods.common.blocks.BlockComponent;
import de.nedelosk.forestmods.common.blocks.BlockCowper;
import de.nedelosk.forestmods.common.blocks.BlockGravel;
import de.nedelosk.forestmods.common.blocks.BlockModularAssembler;
import de.nedelosk.forestmods.common.blocks.BlockModularMachine;
import de.nedelosk.forestmods.common.blocks.BlockOre;
import de.nedelosk.forestmods.common.blocks.BlockTransport;
import de.nedelosk.forestmods.common.blocks.tile.TileCampfire;
import de.nedelosk.forestmods.common.blocks.tile.TileCharcoalKiln;
import de.nedelosk.forestmods.common.blocks.tile.TileCowperAccessPort;
import de.nedelosk.forestmods.common.blocks.tile.TileCowperBase;
import de.nedelosk.forestmods.common.blocks.tile.TileCowperFluidPort;
import de.nedelosk.forestmods.common.blocks.tile.TileModular;
import de.nedelosk.forestmods.common.blocks.tile.TileModularAssembler;
import de.nedelosk.forestmods.common.items.block.ItemBlockCharcoalKiln;
import de.nedelosk.forestmods.common.items.block.ItemBlockMachines;
import de.nedelosk.forestmods.common.items.block.ItemBlockModularMachine;
import de.nedelosk.forestmods.library.core.Registry;
import de.nedelosk.forestmods.library.items.ItemBlockForest;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class BlockManager {

	public static BlockCasing blockCasings;
	public static BlockModularMachine blockModular;
	public static BlockGravel blockGravel;
	public static BlockCampfire blockCampfire;
	public static BlockCharcoalKiln blockCharcoalKiln;
	public static BlockOre blockOres;
	public static BlockComponent blockMetalBlocks;
	public static BlockBlastFurnace blockBlastFurnace;
	public static BlockCowper blockCowper;
	public static BlockTransport blockTransport;
	public static BlockModularAssembler blockAssembler;

	public static void registerBlocks() {
		blockGravel = register(new BlockGravel(), ItemBlockForest.class);
		blockCharcoalKiln = register(new BlockCharcoalKiln(), ItemBlockCharcoalKiln.class);
		blockCampfire = register(new BlockCampfire(), ItemBlockMachines.class);
		blockOres = register(new BlockOre(), ItemBlockForest.class);
		blockMetalBlocks = register(new BlockComponent(Material.iron, "metal_block"), ItemBlockForest.class);
		// blockTransport = register(new BlockTransport(),
		// ItemBlockForest.class);
		blockMetalBlocks.addMetaData(0xCACECF, "tin", "Tin");
		blockMetalBlocks.addMetaData(0xCC6410, "copper", "Copper");
		blockMetalBlocks.addMetaData(0xCA9956, "bronze", "Bronze");
		blockMetalBlocks.addMetaData(0xA0A0A0, "steel", "Steel");
		blockMetalBlocks.addMetaData(0xA1A48C, "invar", "Invar");
		blockBlastFurnace = register(new BlockBlastFurnace(), ItemBlockForest.class);
		blockCowper = register(new BlockCowper(), ItemBlockForest.class);
		blockCasings = register(new BlockCasing(new String[] { "stone", "stone_brick", "iron", "bronze" }), ItemBlockForest.class);
		blockModular = register(new BlockModularMachine(), ItemBlockModularMachine.class);
		blockAssembler = register(new BlockModularAssembler(), ItemBlockForest.class);
	}

	public static void registerTiles() {
		GameRegistry.registerTileEntity(TileCharcoalKiln.class, "forestmods.machine.multi.kiln.charcoal");
		GameRegistry.registerTileEntity(TileCampfire.class, "forestmods.machine.wood.campfire");
		GameRegistry.registerTileEntity(TileModular.class, "forestmods.modular");
		GameRegistry.registerTileEntity(TileModularAssembler.class, "forestmods.modular.assembler");
		GameRegistry.registerTileEntity(TileCowperBase.class, "forestmods.cowper.base");
		GameRegistry.registerTileEntity(TileCowperAccessPort.class, "forestmods.cowper.access");
		GameRegistry.registerTileEntity(TileCowperFluidPort.class, "forestmods.cowper.fluid");
		// GameRegistry.registerTileEntity(TileBlastFurnaceAccessPort.class,
		// "forestmods.tile.blastfurnace.accessport");
		// GameRegistry.registerTileEntity(TileBlastFurnaceFluidPort.class,
		// "forestmods.tile.blastfurnace.fluidport");
		// GameRegistry.registerTileEntity(TileBlastFurnaceBase.class,
		// "forestmods.tile.blastfurnace.base");
		// GameRegistry.registerTileEntity(TileEntityTransport.class,
		// "forestmods.tile.transport.base");
		// GameRegistry.registerTileEntity(TileEntityTransportNode.class,
		// "forestmods.tile.transport.node");
	}

	public static <B extends Block> B register(B block, Class<? extends ItemBlock> item, Object... objects) {
		Registry.registerBlock(block, item, block.getUnlocalizedName().replace("tile.", "").replace("forest.tile.", ""), objects);
		return block;
	}
}
