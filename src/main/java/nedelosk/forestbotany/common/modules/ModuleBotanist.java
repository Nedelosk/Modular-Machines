package nedelosk.forestbotany.common.modules;

import java.io.File;

import nedelosk.forestbotany.api.botany.book.BookPlantEntry;
import nedelosk.forestbotany.api.genetics.allele.AlleleManager;
import nedelosk.forestbotany.api.genetics.plants.IPlant;
import nedelosk.forestbotany.common.blocks.BlockInfuserBase;
import nedelosk.forestbotany.common.blocks.tile.TileInfuser;
import nedelosk.forestbotany.common.blocks.tile.TileInfuserChamber;
import nedelosk.forestbotany.common.book.PlantBookManager;
import nedelosk.forestbotany.common.core.config.Config;
import nedelosk.forestbotany.common.core.registrys.BlockRegistry;
import nedelosk.forestbotany.common.core.registrys.ItemRegistry;
import nedelosk.forestbotany.common.genetics.PlantManager;
import nedelosk.forestbotany.common.genetics.allele.AlleleRegistry;
import nedelosk.forestbotany.common.genetics.templates.crop.CropChromosome;
import nedelosk.forestbotany.common.genetics.templates.crop.CropDefinition;
import nedelosk.forestbotany.common.genetics.templates.crop.CropManager;
import nedelosk.forestbotany.common.genetics.templates.crop.CropModWriter;
import nedelosk.forestbotany.common.genetics.templates.tree.TreeManager;
import nedelosk.forestbotany.common.items.ItemFruit;
import nedelosk.forestbotany.common.items.ItemSeed;
import nedelosk.forestbotany.common.items.blocks.ItemBlockForestBotany;
import nedelosk.forestbotany.common.plugins.waila.PluginWaila;
import nedelosk.forestday.common.registrys.FRegistry;
import nedelosk.nedeloskcore.api.NCoreApi;
import nedelosk.nedeloskcore.api.book.BookEntry;
import nedelosk.nedeloskcore.api.crafting.IPlanRecipe;
import nedelosk.nedeloskcore.common.book.BookDatas;
import nedelosk.nedeloskcore.common.book.BookManager;
import nedelosk.nedeloskcore.common.core.registry.EntryRegistry;
import nedelosk.nedeloskcore.common.items.ItemBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModuleBotanist extends Module {
	
	public static File configBotanist = new File(configFolder, "Crops");
	
	@Override
	public void preInit()
	{
		PlantManager.treeManager = new TreeManager();
		PlantManager.cropManager = new CropManager();
		
		AlleleManager.alleleRegistry = new AlleleRegistry();
		
		ItemRegistry.seed.registerItem(new ItemSeed());
		ItemRegistry.fruit.registerItem(new ItemFruit());
		BlockRegistry.infuser.registerBlock(new BlockInfuserBase("base"), ItemBlockForestBotany.class);
		ItemRegistry.plant_book.registerItem(new ItemBook("plants", EntryRegistry.plantData){
			@Override
			public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX,float hitY, float hitZ) {
				PlantBookManager.unlockPlants(player.getGameProfile(), CropDefinition.Wheat.getPlant(), world);
				PlantBookManager.unlockPlants(player.getGameProfile(), CropDefinition.Carrot.getPlant(), world);
				PlantBookManager.unlockPlants(player.getGameProfile(), CropDefinition.Potatoe.getPlant(), world);
				return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
				
			}
		});
		ItemRegistry.plant_book.item().setTextureName("forestbotany:book_plant");
		
		GameRegistry.registerTileEntity(TileInfuserChamber.class, "infuser.chamber");
		GameRegistry.registerTileEntity(TileInfuser.class, "infuser.mutation");
		
		Config.loadCropConfig(configBotanist);
		
		
		for(IPlant plant : PlantManager.cropManager.getTemplates())
		{
			BookEntry entry = new BookPlantEntry("plant" + plant.getGenome().getActiveAllele(CropChromosome.PLANT).getUID().replace("fb.plant.crop", ""), "plants", "plants", plant, BookDatas.crops).registerEntry();
		}
		IPlanRecipe planRecipe = NCoreApi.planRecipe;
		planRecipe.add(new ItemStack(BlockRegistry.infuser.block()), 1, new ItemStack[]{ new ItemStack(Blocks.glass_pane, 32), new ItemStack(Items.iron_ingot, 32), new ItemStack(Blocks.iron_block, 8)});
		
	}
	
	@Override
	public void init()
	{
	PluginWaila.init();	
	//CropModWriter.writeHarvestcraft();
	}

 }
