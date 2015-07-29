package nedelosk.modularmachines.common.core;

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
import nedelosk.modularmachines.common.blocks.ModularAssemblerBlock;
import nedelosk.modularmachines.common.blocks.ModularMachineBlock;
import nedelosk.modularmachines.common.blocks.item.ItemBlockModularAssembler;
import nedelosk.modularmachines.common.blocks.item.ItemBlockModularMachine;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.blocks.tile.TileModularMachine;
import nedelosk.modularmachines.common.modular.ModularMachine;
import nedelosk.nedeloskcore.common.blocks.BlockOre;
import net.minecraft.item.ItemBlock;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockRegistry {
	
	public static void preInit()
	{
		
		//Blocks
		MMBlocks.Modular_Assembler.registerBlock(new ModularAssemblerBlock(){
			@Override
			public boolean isOpaqueCube() {
				return false;
			}
			
			@Override
			public int getRenderType() {
				return -1;
			}
			
			@Override
			public boolean renderAsNormalBlock() {
				return false;
			}
		}, ItemBlockModularAssembler.class);
		MMBlocks.Modular_Machine.registerBlock(new ModularMachineBlock(), ItemBlockModularMachine.class);
		
		registerTile();
		
	}
	
	public static void registerTile()
	{
		GameRegistry.registerTileEntity(TileModularAssembler.class, "tile.modular.assenbler");
		GameRegistry.registerTileEntity(TileModularMachine.class, "tile.modular.machine");
		
	}
	
}