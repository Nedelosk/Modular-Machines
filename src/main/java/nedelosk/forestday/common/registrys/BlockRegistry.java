package nedelosk.forestday.common.registrys;

import nedelosk.forestday.common.blocks.BlockGravel;
import nedelosk.forestday.common.items.blocks.ItemBlockForestday;
import nedelosk.forestday.common.machines.base.block.BlockMachines;
import nedelosk.forestday.common.machines.base.block.BlockMachinesWood;
import nedelosk.forestday.common.machines.base.block.item.ItemBlockMachines;
import nedelosk.forestday.common.machines.base.fluid.heater.TileFluidHeater;
import nedelosk.forestday.common.machines.base.furnace.base.TileFurnace;
import nedelosk.forestday.common.machines.base.furnace.coke.TileCokeFurnace;
import nedelosk.forestday.common.machines.base.heater.generator.TileHeatGenerator;
import nedelosk.forestday.common.machines.base.saw.TileSaw;
import nedelosk.forestday.common.machines.base.wood.campfire.TileCampfire;
import nedelosk.forestday.common.machines.base.wood.kiln.TileKiln;
import nedelosk.forestday.common.machines.base.wood.workbench.TileWorkbench;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.BlockCharcoalKiln;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.TileCharcoalAsh;
import nedelosk.forestday.common.machines.mutiblock.charcoalkiln.TileCharcoalKiln;
import nedelosk.nedeloskcore.common.blocks.BlockOre;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockRegistry {
	
	public static void preInit()
	{
		
		//Blocks
		
		FBlocks.Gravel.registerBlock(new BlockGravel(), ItemBlockForestday.class);
		
		FBlocks.Machine_Furnace_Base.registerBlock(new BlockMachines("furnace", TileFurnace.class/*, TileFurnace.class, TileFurnace.class, TileFurnace.class, TileFurnace.class*/), ItemBlockMachines.class);
		FBlocks.Machine_Furnace_Coke.registerBlock(new BlockMachines("furnace_coke", TileCokeFurnace.class/*, TileCokeFurnace.class, TileCokeFurnace.class, TileCokeFurnace.class, TileCokeFurnace.class*/), ItemBlockMachines.class);
		FBlocks.Machine_Fluid_Heater.registerBlock(new BlockMachines("fluid_heater", TileFluidHeater.class/*, TileFluidHeater.class, TileFluidHeater.class, TileFluidHeater.class, TileFluidHeater.class*/), ItemBlockMachines.class);
		FBlocks.Machine_Generator_Heat.registerBlock(new BlockMachines("generator.heat", TileHeatGenerator.class/*,  TileHeatGenerator.class,  TileHeatGenerator.class,  TileHeatGenerator.class,  TileHeatGenerator.class*/), ItemBlockMachines.class);
		FBlocks.Machine_Wood_Base.registerBlock(new BlockMachinesWood("wood_base", TileCampfire.class, TileWorkbench.class, TileWorkbench.class, TileKiln.class), ItemBlockMachines.class);
		
		FBlocks.Multiblock_Charcoal_Kiln.registerBlock(new BlockCharcoalKiln(), ItemBlockForestday.class);
		
		registerTile();
		
	}
	
	public static void registerTile()
	{
		GameRegistry.registerTileEntity(TileKiln.class, "machine.wood.kiln");
		GameRegistry.registerTileEntity(TileSaw.class, "machine.file");
		GameRegistry.registerTileEntity(TileFurnace.class, "machine.furnace");
		GameRegistry.registerTileEntity(TileCokeFurnace.class, "machine.furnace.coke");
		GameRegistry.registerTileEntity(TileFluidHeater.class, "machine.furnace.fluidheater");
		GameRegistry.registerTileEntity(TileHeatGenerator.class, "machine.generator.burning");
		
		GameRegistry.registerTileEntity(TileWorkbench.class, "machine.wood.workbench");
		GameRegistry.registerTileEntity(TileCampfire.class, "machine.wood.campfire");
		GameRegistry.registerTileEntity(TileCharcoalKiln.class, "machien.multi.kiln.charcoal");
		GameRegistry.registerTileEntity(TileCharcoalAsh.class, "machien.multi.kiln.charcoal.ash");
		
	}
	
}
