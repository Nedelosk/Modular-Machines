package nedelosk.modularmachines.common.core.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestday.common.blocks.BlockOre;
import nedelosk.forestday.common.items.blocks.ItemBlockForestDay;
import nedelosk.modularmachines.common.blocks.BlockCasing;
import nedelosk.modularmachines.common.blocks.BlockComponent;
import nedelosk.modularmachines.common.blocks.ModularAssemblerBlock;
import nedelosk.modularmachines.common.blocks.ModularMachineBlock;
import nedelosk.modularmachines.common.blocks.item.ItemBlockModularAssembler;
import nedelosk.modularmachines.common.blocks.item.ItemBlockModular;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.blocks.tile.TileModularAssembler;
import nedelosk.modularmachines.common.core.manager.MMBlockManager;
import nedelosk.modularmachines.common.multiblocks.MultiblockAirHeatingPlant;
import nedelosk.modularmachines.common.multiblocks.MultiblockBlastFurnace;
import nedelosk.modularmachines.common.multiblocks.MultiblockCokeOven;
import nedelosk.modularmachines.common.multiblocks.MultiblockFermenter;
import net.minecraft.block.material.Material;

public class BlockRegistry {

	public static String[] oreOtherOres = new String[] { "Columbite", "Aluminum" };

	public static void preInit() {
		// Blocks
		MMBlockManager.Ore_Others.registerBlock(new BlockOre(oreOtherOres, "modularmachines"), ItemBlockForestDay.class);
		MMBlockManager.Casings.registerBlock(new BlockCasing(new String[]{ "stone", "stone_brick", "iron", "bronze" }), ItemBlockForestDay.class);
		MMBlockManager.Component_Metal_Blocks.registerBlock(new BlockComponent(Material.iron, "metal_block"), ItemBlockForestDay.class);
		MMBlockManager.Modular_Assembler.registerBlock(new ModularAssemblerBlock(), ItemBlockModularAssembler.class);
		MMBlockManager.Modular_Machine.registerBlock(new ModularMachineBlock(), ItemBlockModular.class, new Object[] { "modular.machine" });
		
		MMBlockManager.Component_Metal_Blocks.addMetaData(0xCACECF, "tin", "Tin");
		MMBlockManager.Component_Metal_Blocks.addMetaData(0xCC6410, "copper", "Copper");
		MMBlockManager.Component_Metal_Blocks.addMetaData(0xCA9956, "bronze", "Bronze");
		MMBlockManager.Component_Metal_Blocks.addMetaData(0xA0A0A0, "steel", "Steel");
		
		registerTile();

		new MultiblockAirHeatingPlant();
		new MultiblockBlastFurnace();
		new MultiblockCokeOven();
		new MultiblockFermenter();

	}

	public static void registerTile() {
		GameRegistry.registerTileEntity(TileModularAssembler.class, "tile.modular.assenbler");
		GameRegistry.registerTileEntity(TileModular.class, "tile.modular");

	}

}
