package nedelosk.forestday.common.registrys;

import nedelosk.forestbotany.common.genetics.templates.crop.CropDefinition;
import nedelosk.forestday.common.items.materials.ItemDust;
import nedelosk.forestday.common.items.materials.ItemGearWood;
import nedelosk.forestday.common.items.materials.ItemGears;
import nedelosk.forestday.common.items.materials.ItemIngot;
import nedelosk.forestday.common.items.materials.ItemNature;
import nedelosk.forestday.common.items.materials.ItemNugget;
import nedelosk.forestday.common.items.materials.ItemPowder;
import nedelosk.forestday.common.items.tools.ItemAdze;
import nedelosk.forestday.common.items.tools.ItemCutter;
import nedelosk.forestday.common.items.tools.ItemFile;
import nedelosk.forestday.common.items.tools.ItemFlintAxe;
import nedelosk.forestday.common.items.tools.ItemKnife;
import nedelosk.forestday.common.items.tools.ItemToolForestday.Material;
import nedelosk.forestday.common.items.tools.ItemToolParts;
import nedelosk.forestday.common.items.tools.ItemWoodBucket;
import nedelosk.forestday.module.lumberjack.items.ItemCharconia;
import nedelosk.forestday.structure.base.items.ItemBuildMaterials;
import nedelosk.forestday.structure.base.items.ItemBus;
import nedelosk.forestday.structure.base.items.ItemCoilGrinding;
import nedelosk.forestday.structure.base.items.ItemCoilHeat;
import nedelosk.forestday.structure.base.items.ItemMetallurgy;
import nedelosk.forestday.structure.base.items.ItemMortal;
import nedelosk.forestday.structure.base.items.ItemPlates;
import nedelosk.forestday.structure.base.items.ItemRod;
import nedelosk.nedeloskcore.common.book.BookManager;
import nedelosk.nedeloskcore.common.items.ItemBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class ForestdayItemRegistry {

	public static ForestdayBlockRegistry coreBlocks;
	
	public static void preInit()
	{
		
		
		ToolMaterial flintMaterial = EnumHelper.addToolMaterial("flint", 1, 50, 1, 0, 1);
		
		//Tools
		file = new ItemFile(50, 5, "file.stone", "file_stone", 1, Material.Stone);
		ForestdayRegistry.registerItem(file, "tool.file.stone");
		fileIron = new ItemFile(100, 2, "file.iron", "file", 2, Material.Iron);
		ForestdayRegistry.registerItem(fileIron, "tool.file.iron");
		fileDiamond = new ItemFile(150, 1, "file.diamond", "file_diamond", 3, Material.Diamond);
		ForestdayRegistry.registerItem(fileDiamond, "tool.file.diamond");
		cutter= new ItemCutter(250, 10, "cutter.iron", "cutter_iron", 1, Material.Iron);
		ForestdayRegistry.registerItem(cutter, "tool.cutter.iron");
		toolparts = new ItemToolParts();
		ForestdayRegistry.registerItem(toolparts, "tool.parts");
		toolKnife = new ItemKnife("knife.iron", 200, 1, Material.Iron);
		ForestdayRegistry.registerItem(toolKnife, "knife.resin.iron");
		flintAxe = new ItemFlintAxe(flintMaterial);
		ForestdayRegistry.registerItem(flintAxe, "axe.flint");
		adze = new ItemAdze("adze", 175, 1, Material.Stone, 3);
		ForestdayRegistry.registerItem(adze, "adze");
		adzeLong = new ItemAdze("adze.long", 175, 1, Material.Stone, 3);
		ForestdayRegistry.registerItem(adzeLong, "adze.long");
		
		ingots = new ItemIngot();
		ForestdayRegistry.registerItem(ingots, "ingot");
		dust = new ItemDust();
		ForestdayRegistry.registerItem(dust, "dust");
		powder = new ItemPowder();
		ForestdayRegistry.registerItem(powder, "powder");
		nuggets = new ItemNugget();
		ForestdayRegistry.registerItem(nuggets, "nuggets");
		
		nature = new ItemNature();
		ForestdayRegistry.registerItem(nature, "nature");
		buildBrickItems = new ItemBuildMaterials();
		ForestdayRegistry.registerItem(buildBrickItems, "build.materials");
		buildMortalItems = new ItemMortal();
		ForestdayRegistry.registerItem(buildMortalItems, "build.materials.mortal");
		buildPlates = new ItemPlates();
		ForestdayRegistry.registerItem(buildPlates, "build.materials.plate");
		
		coilHeat = new ItemCoilHeat();
		ForestdayRegistry.registerItem(coilHeat, "coil");
		coilGrinding = new ItemCoilGrinding();
		ForestdayRegistry.registerItem(coilGrinding, "coil.grinding");
		hatch = new ItemBus();
		ForestdayRegistry.registerItem(hatch, "busHatch");
		rods = new ItemRod();
		ForestdayRegistry.registerItem(rods, "rod");
		metallurgy = new ItemMetallurgy();
		ForestdayRegistry.registerItem(metallurgy, "metallurgy");
		
		gear = new ItemGears();
		ForestdayRegistry.registerItem(gear, "gear");
		gearWood = new ItemGearWood();
		ForestdayRegistry.registerItem(gearWood, "gear.wood");
		
		bookPlants = new ItemBook("plants", ForestdayEntryRegistry.plantData){
			@Override
			public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX,float hitY, float hitZ) {
				BookManager.unlockPlants(player.getGameProfile(), CropDefinition.Wheat.getPlant(), world);
				BookManager.unlockPlants(player.getGameProfile(), CropDefinition.Carrot.getPlant(), world);
				BookManager.unlockPlants(player.getGameProfile(), CropDefinition.Potatoe.getPlant(), world);
				return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
				
			}
		};
		bookPlants.setTextureName("forestbotany:book_plant");
		ForestdayRegistry.registerItem(bookPlants, "book.plants");
		
		woodBucket = new ItemWoodBucket(Blocks.air, "bucket.wood");
		ForestdayRegistry.registerItem(woodBucket, "bucket.wood");
		
		woodBucketWater = new ItemWoodBucket(Blocks.water, "bucket.wood.water");
		ForestdayRegistry.registerItem(woodBucketWater, "bucket.wood.water");
		
		}
	
	public static Item ingots;
	public static Item charcoal;
	public static Item dust;
	public static Item powder;
	public static Item nuggets;
	public static Item toolparts;
	public static Item machineItems;
	public static Item nature;
	public static Item buildBrickItems;
	public static Item buildMortalItems;
	public static Item buildPlates;
	
	public static Item coilHeat;
	public static Item coilGrinding;
	public static Item rods;
	public static Item hatch;
	public static Item metallurgy;
	
	public static Item toolKnife;
	public static Item file;
	public static Item cutter;
	public static Item fileIron;
	public static Item fileDiamond;
	public static Item flintAxe;
	public static Item adze;
	public static Item adzeLong;
	
	public static Item gear;
	public static Item gearWood;
	
	public static ItemCharconia charconia;
	public static ItemBook bookPlants;
	
	public static Item woodBucket;
	public static Item woodBucketWater;
	
}
