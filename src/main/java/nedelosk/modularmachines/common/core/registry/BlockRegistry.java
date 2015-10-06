package nedelosk.modularmachines.common.core.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.modularmachines.common.blocks.BlockMetal;
import nedelosk.modularmachines.common.blocks.ModularAssemblerBlock;
import nedelosk.modularmachines.common.blocks.ModularMachineBlock;
import nedelosk.modularmachines.common.blocks.item.ItemBlockMaterial;
import nedelosk.modularmachines.common.blocks.item.ItemBlockModularAssembler;
import nedelosk.modularmachines.common.blocks.item.ItemBlockModularMachine;
import nedelosk.modularmachines.common.blocks.tile.TileMaterial;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.core.MMBlocks;
import nedelosk.modularmachines.common.multiblocks.MultiblockAirHeatingPlant;
import nedelosk.modularmachines.common.multiblocks.MultiblockBlastFurnace;
import nedelosk.modularmachines.common.multiblocks.MultiblockCokeOven;
import nedelosk.modularmachines.common.multiblocks.MultiblockFermenter;
import nedelosk.nedeloskcore.common.blocks.BlockOre;
import nedelosk.nedeloskcore.common.items.ItemBlockForest;

public class BlockRegistry {
	
	public static String[] oreOtherOres = new String[] { "Columbite", "Aluminum" };
	
	public static void preInit()
	{
		//Blocks
		MMBlocks.Ore_Others.registerBlock(new BlockOre(oreOtherOres, "modularmachines"), ItemBlockForest.class);
		MMBlocks.Modular_Assembler.registerBlock(new ModularAssemblerBlock(), ItemBlockModularAssembler.class);
		MMBlocks.Modular_Machine.registerBlock(new ModularMachineBlock(), ItemBlockModularMachine.class);
		
		MMBlocks.Metal_Blocks.registerBlock(new BlockMetal(), ItemBlockMaterial.class);
		
		registerTile();
		
		new MultiblockAirHeatingPlant();
		new MultiblockBlastFurnace();
		new MultiblockCokeOven();
		new MultiblockFermenter();
		
	}
	
	public static void registerTile()
	{
		GameRegistry.registerTileEntity(TileMaterial.class, "tile.material");
		GameRegistry.registerTileEntity(TileModularAssembler.class, "tile.modular.assenbler");
		GameRegistry.registerTileEntity(TileModular.class, "tile.modular");
		
	}
	
}
