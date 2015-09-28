package nedelosk.modularmachines.common.core.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.modularmachines.common.blocks.ModularAssemblerBlock;
import nedelosk.modularmachines.common.blocks.ModularMachineBlock;
import nedelosk.modularmachines.common.blocks.ModularWorkbench;
import nedelosk.modularmachines.common.blocks.item.ItemBlockModularAssembler;
import nedelosk.modularmachines.common.blocks.item.ItemBlockModularMachine;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.blocks.tile.TileModularWorkbench;
import nedelosk.modularmachines.common.core.MMBlocks;
import nedelosk.nedeloskcore.common.blocks.BlockOre;
import nedelosk.nedeloskcore.common.items.ItemBlockForest;
import net.minecraft.item.ItemBlock;

public class BlockRegistry {
	
	public static String[] oreOtherOres = new String[] { "Columbite", "Aluminum" };
	
	public static void preInit()
	{
		//Blocks
		MMBlocks.Ore_Others.registerBlock(new BlockOre(oreOtherOres, "modularmachines"), ItemBlockForest.class);
		MMBlocks.Modular_Assembler.registerBlock(new ModularAssemblerBlock(), ItemBlockModularAssembler.class);
		MMBlocks.Modular_Machine.registerBlock(new ModularMachineBlock(), ItemBlockModularMachine.class);
		MMBlocks.Modular_Workbench.registerBlock(new ModularWorkbench(), ItemBlock.class);
		
		registerTile();
		
	}
	
	public static void registerTile()
	{
		GameRegistry.registerTileEntity(TileModularAssembler.class, "tile.modular.assenbler");
		GameRegistry.registerTileEntity(TileModular.class, "tile.modular");
		GameRegistry.registerTileEntity(TileModularWorkbench.class, "tile.modular.workbench");
		
	}
	
}
