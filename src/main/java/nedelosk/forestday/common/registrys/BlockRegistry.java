package nedelosk.forestday.common.registrys;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestday.common.blocks.BlockCharcoalKiln;
import nedelosk.forestday.common.blocks.BlockCropCorn;
import nedelosk.forestday.common.blocks.BlockGravel;
import nedelosk.forestday.common.blocks.BlockMachinesWood;
import nedelosk.forestday.common.blocks.items.ItemBlockMachines;
import nedelosk.forestday.common.blocks.tiles.TileCampfire;
import nedelosk.forestday.common.blocks.tiles.TileCharcoalAsh;
import nedelosk.forestday.common.blocks.tiles.TileCharcoalKiln;
import nedelosk.forestday.common.blocks.tiles.TileKiln;
import nedelosk.forestday.common.blocks.tiles.TileWorkbench;
import nedelosk.forestday.common.items.blocks.ItemBlockForestday;
import nedelosk.forestday.common.managers.BlockManager;
import net.minecraft.item.ItemBlock;

public class BlockRegistry {
	
	public static void preInit()
	{
		
		//Blocks
		BlockManager.Crop_Corn.registerBlock(new BlockCropCorn(), ItemBlock.class);
		BlockManager.Gravel.registerBlock(new BlockGravel(), ItemBlockForestday.class);
		
		BlockManager.Machine_Wood_Base.registerBlock(new BlockMachinesWood("wood_base", TileCampfire.class, TileWorkbench.class, TileWorkbench.class, TileKiln.class), ItemBlockMachines.class);
		
		BlockManager.Multiblock_Charcoal_Kiln.registerBlock(new BlockCharcoalKiln(), ItemBlockForestday.class);
		
		registerTile();
		
	}
	
	public static void registerTile()
	{
		GameRegistry.registerTileEntity(TileKiln.class, "machine.wood.kiln");
		
		GameRegistry.registerTileEntity(TileWorkbench.class, "machine.wood.workbench");
		GameRegistry.registerTileEntity(TileCampfire.class, "machine.wood.campfire");
		GameRegistry.registerTileEntity(TileCharcoalKiln.class, "machien.multi.kiln.charcoal");
		GameRegistry.registerTileEntity(TileCharcoalAsh.class, "machien.multi.kiln.charcoal.ash");
		
	}
	
}
