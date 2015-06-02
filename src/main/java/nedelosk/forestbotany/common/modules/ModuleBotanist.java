package nedelosk.forestbotany.common.modules;

import java.io.File;

import nedelosk.forestbotany.api.genetics.allele.AlleleManager;
import nedelosk.forestbotany.common.blocks.BlockInfuserBase;
import nedelosk.forestbotany.common.blocks.tile.TileInfuser;
import nedelosk.forestbotany.common.blocks.tile.TileInfuserChamber;
import nedelosk.forestbotany.common.core.config.Config;
import nedelosk.forestbotany.common.core.registry.BlockRegistry;
import nedelosk.forestbotany.common.core.registry.ItemRegistry;
import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.allele.AlleleRegistry;
import nedelosk.forestbotany.common.genetics.templates.crop.CropManager;
import nedelosk.forestbotany.common.genetics.templates.crop.CropModWriter;
import nedelosk.forestbotany.common.genetics.templates.tree.TreeManager;
import nedelosk.forestbotany.common.items.ItemFruit;
import nedelosk.forestbotany.common.items.ItemSeed;
import nedelosk.forestbotany.common.items.blocks.ItemBlockForestBotany;
import nedelosk.forestbotany.common.plugins.waila.PluginWaila;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModuleBotanist extends Module {
	
	public static File configBotanist = new File(configFolder, "Crops");
	
	@Override
	public void preInit()
	{
		PlantManager.treeManager = new TreeManager();
		PlantManager.cropManager = new CropManager();
		
		AlleleManager.alleleRegistry = new AlleleRegistry();
		
		//ItemRegistry.sapling.registerItem(new ItemSapling());
		ItemRegistry.seed.registerItem(new ItemSeed());
		ItemRegistry.fruit.registerItem(new ItemFruit());
		BlockRegistry.infuser.registerBlock(new BlockInfuserBase("base"), ItemBlockForestBotany.class);
		
		GameRegistry.registerTileEntity(TileInfuserChamber.class, "infuser.chamber");
		GameRegistry.registerTileEntity(TileInfuser.class, "infuser.mutation");
		
		Config.loadCropConfig(configBotanist);
		
	}
	
	@Override
	public void init()
	{
	PluginWaila.init();	
	//CropModWriter.writeHarvestcraft();
	}

 }
