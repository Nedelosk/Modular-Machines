package nedelosk.forestday.common.registrys;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestday.common.blocks.BlockCropCorn;
import nedelosk.forestday.common.blocks.BlockGravel;
import nedelosk.forestday.common.items.blocks.ItemBlockForestday;
import nedelosk.forestday.common.machines.base.block.BlockMachinesWood;
import nedelosk.forestday.common.machines.base.block.item.ItemBlockMachines;
import nedelosk.forestday.common.machines.base.wood.campfire.TileCampfire;
import nedelosk.forestday.common.machines.base.wood.kiln.TileKiln;
import nedelosk.forestday.common.machines.base.wood.workbench.TileWorkbench;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.BlockCharcoalKiln;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.TileCharcoalAsh;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.TileCharcoalKiln;
import net.minecraft.item.ItemBlock;

public class BlockRegistry {
	
	public static void preInit()
	{
		
		//Blocks
		FBlocks.Crop_Corn.registerBlock(new BlockCropCorn(), ItemBlock.class);
		FBlocks.Gravel.registerBlock(new BlockGravel(), ItemBlockForestday.class);
		
		FBlocks.Machine_Wood_Base.registerBlock(new BlockMachinesWood("wood_base", TileCampfire.class, TileWorkbench.class, TileWorkbench.class, TileKiln.class), ItemBlockMachines.class);
		
		FBlocks.Multiblock_Charcoal_Kiln.registerBlock(new BlockCharcoalKiln(), ItemBlockForestday.class);
		
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
