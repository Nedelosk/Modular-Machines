package nedelosk.forestday.common.core.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestday.common.blocks.BlockCharcoalKiln;
import nedelosk.forestday.common.blocks.BlockCropCorn;
import nedelosk.forestday.common.blocks.BlockGravel;
import nedelosk.forestday.common.blocks.BlockMachinesWood;
import nedelosk.forestday.common.blocks.BlockOre;
import nedelosk.forestday.common.blocks.items.ItemBlockMachines;
import nedelosk.forestday.common.blocks.tiles.TileCampfire;
import nedelosk.forestday.common.blocks.tiles.TileCharcoalAsh;
import nedelosk.forestday.common.blocks.tiles.TileCharcoalKiln;
import nedelosk.forestday.common.blocks.tiles.TileKiln;
import nedelosk.forestday.common.blocks.tiles.TileWorkbench;
import nedelosk.forestday.common.core.managers.FBlockManager;
import nedelosk.forestday.common.items.blocks.ItemBlockForestDay;
import nedelosk.forestday.common.multiblocks.TileMultiblockBase;
import nedelosk.forestday.common.multiblocks.TileMultiblockValve;
import net.minecraft.item.ItemBlock;

public class BlockRegistry {

	public static void preInit() {

		// Blocks
		FBlockManager.Crop_Corn.registerBlock(new BlockCropCorn(), ItemBlock.class);
		FBlockManager.Gravel.registerBlock(new BlockGravel(), ItemBlockForestDay.class);

		FBlockManager.Ore.registerBlock(new BlockOre(BlockOre.ores, "forestday"), ItemBlockForestDay.class);

		FBlockManager.Machine_Wood_Base.registerBlock(new BlockMachinesWood("wood_base", TileCampfire.class,
				TileWorkbench.class, TileWorkbench.class, TileKiln.class), ItemBlockMachines.class);
		FBlockManager.Machine_Wood_Base.block().setHarvestLevel("Axe", 1, 1);
		FBlockManager.Machine_Wood_Base.block().setHarvestLevel("Axe", 1, 2);

		FBlockManager.Multiblock_Charcoal_Kiln.registerBlock(new BlockCharcoalKiln(), ItemBlockForestDay.class);

		// FBlockManager.Multiblock.registerBlock(new BlockMultiblock(),
		// ItemBlockMultiblock.class);
		// FBlockManager.Multiblock_Valve.registerBlock(new
		// BlockMultiblockValve(), ItemBlockMultiblock.class);

		registerTile();

	}

	public static void registerTile() {
		GameRegistry.registerTileEntity(TileKiln.class, "machine.wood.kiln");

		GameRegistry.registerTileEntity(TileWorkbench.class, "machine.wood.workbench");
		GameRegistry.registerTileEntity(TileCampfire.class, "machine.wood.campfire");

		GameRegistry.registerTileEntity(TileCharcoalKiln.class, "machien.multi.kiln.charcoal");
		GameRegistry.registerTileEntity(TileCharcoalAsh.class, "machien.multi.kiln.charcoal.ash");

		GameRegistry.registerTileEntity(TileMultiblockBase.class, "tile.multiblock.base");
		GameRegistry.registerTileEntity(TileMultiblockValve.class, "tile.multiblock.fluid");

	}

}
