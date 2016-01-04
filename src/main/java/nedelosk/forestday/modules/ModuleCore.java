package nedelosk.forestday.modules;

import static net.minecraftforge.oredict.OreDictionary.*;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestcore.library.core.Registry;
import nedelosk.forestcore.library.modules.AModule;
import nedelosk.forestcore.library.modules.IModuleManager;
import nedelosk.forestcore.library.modules.manager.IBlockManager;
import nedelosk.forestcore.library.modules.manager.IItemManager;
import nedelosk.forestcore.library.utils.CraftingUtil;
import nedelosk.forestday.api.crafting.ForestdayCrafting;
import nedelosk.forestday.api.crafting.ICampfireRecipe;
import nedelosk.forestday.api.crafting.IWorkbenchRecipe;
import nedelosk.forestday.api.crafting.OreStack;
import nedelosk.forestday.common.blocks.BlockCropCorn;
import nedelosk.forestday.common.blocks.BlockGravel;
import nedelosk.forestday.common.blocks.BlockMachinesWood;
import nedelosk.forestday.common.blocks.BlockOre;
import nedelosk.forestday.common.blocks.items.ItemBlockMachines;
import nedelosk.forestday.common.blocks.tiles.TileCampfire;
import nedelosk.forestday.common.blocks.tiles.TileWorkbench;
import nedelosk.forestday.common.configs.ForestDayConfig;
import nedelosk.forestday.common.crafting.CampfireRecipeManager;
import nedelosk.forestday.common.crafting.WorkbenchRecipeManager;
import nedelosk.forestday.common.items.base.ItemGearWood;
import nedelosk.forestday.common.items.base.ItemWoodBucket;
import nedelosk.forestday.common.items.blocks.ItemBlockForestDay;
import nedelosk.forestday.common.items.materials.ItemCampfire;
import nedelosk.forestday.common.items.materials.ItemGem;
import nedelosk.forestday.common.items.materials.ItemIngot;
import nedelosk.forestday.common.items.materials.ItemNature;
import nedelosk.forestday.common.items.materials.ItemNugget;
import nedelosk.forestday.common.items.tools.ItemCutter;
import nedelosk.forestday.common.items.tools.ItemFile;
import nedelosk.forestday.common.items.tools.ItemFlintAxe;
import nedelosk.forestday.common.items.tools.ItemToolCrafting;
import nedelosk.forestday.common.items.tools.ItemToolParts;
import nedelosk.forestday.common.items.tools.ItemToolForestday.Material;
import nedelosk.forestday.common.network.packets.PacketHandler;
import nedelosk.forestday.common.world.WorldGeneratorFD;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public class ModuleCore extends AModule {

	public ModuleCore() {
		super("FCore");
	}

	public static IWorkbenchRecipe workbench;
	public static ICampfireRecipe campfire;

	@Override
	public void preInit(IModuleManager manager) {

		MinecraftForge.EVENT_BUS.register(this);

		PacketHandler.preInit();

		manager.register(BlockManager.Crop_Corn, new BlockCropCorn(), ItemBlock.class);
		manager.register(BlockManager.Gravel, new BlockGravel(), ItemBlockForestDay.class);
		manager.register(BlockManager.Ore, new BlockOre(BlockOre.ores, "forestday"), ItemBlockForestDay.class);
		manager.register(BlockManager.Machine,
				new BlockMachinesWood("wood_base", TileCampfire.class, TileWorkbench.class, TileWorkbench.class),
				ItemBlockMachines.class);
		GameRegistry.registerTileEntity(TileWorkbench.class, "machine.wood.workbench");
		GameRegistry.registerTileEntity(TileCampfire.class, "machine.wood.campfire");
		BlockManager.Machine.block().setHarvestLevel("axe", 0, 1);
		BlockManager.Machine.block().setHarvestLevel("axe", 0, 2);

		/*
		 * manager.register(BlockManager.Multiblock, new BlockMultiblock(),
		 * ItemBlockMultiblock.class);
		 * GameRegistry.registerTileEntity(TileMultiblockBase.class,
		 * "tile.multiblock.base");
		 * manager.register(BlockManager.Multiblock_Valve, new
		 * BlockMultiblockValve(), ItemBlockMultiblock.class);
		 * GameRegistry.registerTileEntity(TileMultiblockValve.class,
		 * "tile.multiblock.fluid");
		 */

		manager.register(ItemManager.Gears_Wood, new ItemGearWood());
		manager.register(ItemManager.Nature, new ItemNature());
		manager.register(ItemManager.Crop_Corn,
				new ItemSeedFood(2, 0.4F, BlockManager.Crop_Corn.getObject(), Blocks.farmland) {
					@Override
					public void registerIcons(net.minecraft.client.renderer.texture.IIconRegister IIconRegister) {
						itemIcon = IIconRegister.registerIcon("forestday:corn");
					};
				}.setUnlocalizedName("corn"));
		MinecraftForge.addGrassSeed(new ItemStack(ItemManager.Crop_Corn.getObject()), 15);

		manager.register(ItemManager.Curb, new ItemCampfire(ForestDayConfig.campfireCurbs, "curb"));
		manager.register(ItemManager.Pot, new ItemCampfire(ForestDayConfig.campfirePots, "pot"));
		manager.register(ItemManager.Pot_Holder, new ItemCampfire(ForestDayConfig.campfirePotHolders, "pot_holder"));

		manager.register(ItemManager.Tool_Parts, new ItemToolParts());
		manager.register(ItemManager.File_Stone, new ItemFile(50, 5, "file.stone", "file_stone", 1, Material.Stone));
		manager.register(ItemManager.File_Iron, new ItemFile(150, 2, "file.iron", "file", 2, Material.Iron));
		manager.register(ItemManager.File_Diamond,
				new ItemFile(300, 1, "file.diamond", "file_diamond", 3, Material.Diamond));
		manager.register(ItemManager.Hammer, new ItemToolCrafting("hammer", 300, 0, Material.Iron, "hammer", 15));
		manager.register(ItemManager.Knife_Stone, new ItemToolCrafting("knife", 200, 1, Material.Iron, "knife", 5));
		manager.register(ItemManager.Cutter, new ItemCutter(250, 10, "cutter", "cutter", 1, Material.Iron));
		manager.register(ItemManager.Axe_Flint, new ItemFlintAxe(EnumHelper.addToolMaterial("flint", 1, 50, 1, 0, 1)));
		manager.register(ItemManager.Adze, new ItemToolCrafting("adze", 175, 1, Material.Stone, "adze", 3));
		manager.register(ItemManager.Adze_Long,
				new ItemToolCrafting("adze.long", 175, 1, Material.Stone, "adze.long", 3));

		manager.register(ItemManager.Bucket_Wood, new ItemWoodBucket(Blocks.air, "bucket.wood").setMaxStackSize(16));
		manager.register(ItemManager.Bucket_Wood_Water, new ItemWoodBucket(Blocks.water, "bucket.wood.water")
				.setContainerItem(ItemManager.Bucket_Wood.getObject()));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER,
				new ItemStack(ItemManager.Bucket_Wood_Water.getObject()),
				new ItemStack(ItemManager.Bucket_Wood.getObject()));

		manager.register(ItemManager.Ingots, new ItemIngot(ItemIngot.ingots, "forestday"));
		manager.register(ItemManager.Nuggets, new ItemNugget(ItemNugget.nuggets, "forestday"));
		manager.register(ItemManager.Gems, new ItemGem());

		// Tools
		registerOre("toolFile", ItemManager.File_Stone.getObject());
		registerOre("toolFile", ItemManager.File_Iron.getObject());
		registerOre("toolHammer", ItemManager.Hammer.getObject());
		registerOre("toolFile", ItemManager.File_Diamond.getObject());
		registerOre("toolCutter", ItemManager.Cutter.getObject());
		registerOre("toolKnife", ItemManager.Knife_Stone.getObject());

		registerOre("hardenedStarch", new ItemStack(ItemManager.Nature.getObject(), 1, 11));
		registerOre("ingotPlastic", new ItemStack(ItemManager.Nature.getObject(), 1, 11));
		registerOre("gearWood", new ItemStack(ItemManager.Gears_Wood.getObject(), 1, 1));

		registerOre("ingotCopper", new ItemStack(ItemManager.Ingots.getObject(), 1, 0));
		registerOre("ingotTin", new ItemStack(ItemManager.Ingots.getObject(), 1, 1));
		registerOre("ingotSilver", new ItemStack(ItemManager.Ingots.getObject(), 1, 2));
		registerOre("ingotLead", new ItemStack(ItemManager.Ingots.getObject(), 1, 3));
		registerOre("ingotNickel", new ItemStack(ItemManager.Ingots.getObject(), 1, 4));
		registerOre("nuggetCopper", new ItemStack(ItemManager.Nuggets.getObject(), 1, 0));
		registerOre("nuggetTin", new ItemStack(ItemManager.Nuggets.getObject(), 1, 1));
		registerOre("nuggetSilver", new ItemStack(ItemManager.Nuggets.getObject(), 1, 2));
		registerOre("nuggetLead", new ItemStack(ItemManager.Nuggets.getObject(), 1, 3));
		registerOre("nuggetNickel", new ItemStack(ItemManager.Nuggets.getObject(), 1, 4));
		registerOre("nuggetIron", new ItemStack(ItemManager.Nuggets.getObject(), 1, 5));
		registerOre("oreCopper", new ItemStack(BlockManager.Ore.getObject(), 1, 0));
		registerOre("oreTin", new ItemStack(BlockManager.Ore.getObject(), 1, 1));
		registerOre("oreSilver", new ItemStack(BlockManager.Ore.getObject(), 1, 2));
		registerOre("oreLead", new ItemStack(BlockManager.Ore.getObject(), 1, 3));
		registerOre("oreNickel", new ItemStack(BlockManager.Ore.getObject(), 1, 4));
		registerOre("oreRuby", new ItemStack(BlockManager.Ore.getObject(), 1, 5));
		registerOre("gemRuby", new ItemStack(ItemManager.Gems.getObject(), 1, 0));
	}

	@Override
	public void init(IModuleManager manager) {
		registerRecipes();
	}

	@Override
	public void postInit(IModuleManager manager) {
		removeRecipes();

		GameRegistry.registerWorldGenerator(new WorldGeneratorFD(), 0);
	}

	public static void removeRecipes() {
		CraftingUtil.removeFurnaceRecipe(Items.brick);
	}

	public static void registerRecipes() {
		ForestdayCrafting.workbenchRecipe = new WorkbenchRecipeManager();
		ForestdayCrafting.campfireRecipe = new CampfireRecipeManager();

		workbench = ForestdayCrafting.workbenchRecipe;
		campfire = ForestdayCrafting.campfireRecipe;

		addMachineRecipes();
		addCampfireRecipes();
		addWorkbenchRecipes();
		addNormalRecipes();
	}

	public static void addNormalRecipes() {
		addShapelessRecipe(new ItemStack(BlockManager.Gravel.item()), Blocks.dirt, Blocks.dirt, Blocks.dirt,
				Blocks.dirt, Blocks.gravel, Blocks.gravel, Blocks.gravel, Blocks.gravel, Blocks.sand);

		addShapelessRecipe(new ItemStack(ItemManager.Axe_Flint.item()), Items.stick, Items.stick, Items.flint,
				Items.flint);

		addShapelessRecipe(new ItemStack(ItemManager.Tool_Parts.item()), Items.stick, Items.leather);
		addShapelessRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 1), new ItemStack(Blocks.stone_slab, 1, 3),
				new ItemStack(Blocks.stone_slab, 1, 3), Items.flint, Items.flint);
		addShapelessRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 2), Items.iron_ingot, Items.iron_ingot,
				Items.flint, Items.flint);
		addShapelessRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 3), Items.diamond, Items.diamond);
		addShapelessRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 4), Items.stick, Items.stick, Items.stick,
				Items.leather);
		addShapelessRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 5), new ItemStack(Blocks.stone_slab, 1, 3),
				new ItemStack(Blocks.stone_slab, 1, 3), new ItemStack(Blocks.stone_slab, 1, 3), Items.flint);
		addShapelessRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 6), Items.iron_ingot, Items.iron_ingot,
				Items.flint);
		addShapelessRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 7), Items.stick, Items.stick);
		addShapedRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 8), "   ", "+++", "  +", '+',
				Blocks.cobblestone);
		addShapedRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 9), "+++", "  +", "  +", '+',
				Blocks.cobblestone);
		addShapedRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 11), "  +", " + ", "+  ", '+', "plankWood");
		addShapedRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 10), "  +", " + ", "   ", '+', "plankWood");
		addShapedRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 10), "   ", " + ", "+  ", '+', "plankWood");

		addShapelessRecipe(new ItemStack(ItemManager.File_Stone.item()),
				new ItemStack(ItemManager.Tool_Parts.item(), 1, 0), new ItemStack(ItemManager.Tool_Parts.item(), 1, 1));
		addShapelessRecipe(new ItemStack(ItemManager.File_Iron.item()),
				new ItemStack(ItemManager.Tool_Parts.item(), 1, 0), new ItemStack(ItemManager.Tool_Parts.item(), 1, 2));
		addShapelessRecipe(new ItemStack(ItemManager.File_Diamond.item()),
				new ItemStack(ItemManager.Tool_Parts.item(), 1, 0), new ItemStack(ItemManager.Tool_Parts.item(), 1, 3));
		addShapelessRecipe(new ItemStack(ItemManager.Knife_Stone.item()),
				new ItemStack(ItemManager.Tool_Parts.item(), 1, 4), new ItemStack(ItemManager.Tool_Parts.item(), 1, 5));
		addShapelessRecipe(new ItemStack(ItemManager.Cutter.item()), new ItemStack(ItemManager.Tool_Parts.item(), 1, 6),
				new ItemStack(ItemManager.Tool_Parts.item(), 1, 7));
		addShapelessRecipe(new ItemStack(ItemManager.Adze.item()), new ItemStack(ItemManager.Tool_Parts.item(), 1, 8),
				new ItemStack(ItemManager.Tool_Parts.item(), 1, 10));
		addShapelessRecipe(new ItemStack(ItemManager.Adze_Long.item()),
				new ItemStack(ItemManager.Tool_Parts.item(), 1, 9),
				new ItemStack(ItemManager.Tool_Parts.item(), 1, 11));
		addShapedRecipe(new ItemStack(ItemManager.Hammer.item()), "+++", "+++", " - ", '+', "ingotIron", '-',
				"stickWood");

		addShapelessRecipe(new ItemStack(ItemManager.Nature.item(), 1, 8), Blocks.sand, Blocks.sand, Blocks.gravel,
				Blocks.gravel, Items.water_bucket);
		addShapelessRecipe(new ItemStack(ItemManager.Nature.item(), 1, 8), Blocks.sand, Blocks.sand, Blocks.gravel,
				Blocks.gravel, ItemManager.Bucket_Wood_Water.item());

		GameRegistry.addSmelting(new ItemStack(ItemManager.Crop_Corn.item()),
				new ItemStack(ItemManager.Nature.item(), 1, 9), 0.5F);
		GameRegistry.addSmelting(new ItemStack(ItemManager.Nature.item(), 1, 10),
				new ItemStack(ItemManager.Nature.item(), 1, 11), 0.5F);
		addShapedRecipe(new ItemStack(ItemManager.Nature.item(), 1, 10), "+++", "+++", "+++", '+',
				new ItemStack(ItemManager.Nature.item(), 1, 9));

		GameRegistry.addSmelting(new ItemStack(BlockManager.Ore.item(), 1, 0),
				new ItemStack(ItemManager.Ingots.item(), 1, 0), 0.5F);
		GameRegistry.addSmelting(new ItemStack(BlockManager.Ore.item(), 1, 1),
				new ItemStack(ItemManager.Ingots.item(), 1, 1), 0.5F);
		GameRegistry.addSmelting(new ItemStack(BlockManager.Ore.item(), 1, 2),
				new ItemStack(ItemManager.Ingots.item(), 1, 2), 0.5F);
		GameRegistry.addSmelting(new ItemStack(BlockManager.Ore.item(), 1, 3),
				new ItemStack(ItemManager.Ingots.item(), 1, 3), 0.5F);
		GameRegistry.addSmelting(new ItemStack(BlockManager.Ore.item(), 1, 4),
				new ItemStack(ItemManager.Ingots.item(), 1, 4), 0.5F);
		GameRegistry.addShapedRecipe(new ItemStack(ItemManager.Ingots.item(), 1, 0), "+++", "+++", "+++", '+',
				new ItemStack(ItemManager.Nuggets.item(), 1, 0));
		GameRegistry.addShapedRecipe(new ItemStack(ItemManager.Ingots.item(), 1, 1), "+++", "+++", "+++", '+',
				new ItemStack(ItemManager.Nuggets.item(), 1, 1));
		GameRegistry.addShapedRecipe(new ItemStack(ItemManager.Ingots.item(), 1, 2), "+++", "+++", "+++", '+',
				new ItemStack(ItemManager.Nuggets.item(), 1, 2));
		GameRegistry.addShapedRecipe(new ItemStack(ItemManager.Ingots.item(), 1, 3), "+++", "+++", "+++", '+',
				new ItemStack(ItemManager.Nuggets.item(), 1, 3));
		GameRegistry.addShapedRecipe(new ItemStack(ItemManager.Ingots.item(), 1, 4), "+++", "+++", "+++", '+',
				new ItemStack(ItemManager.Nuggets.item(), 1, 4));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemManager.Nuggets.item(), 9, 0),
				new ItemStack(ItemManager.Ingots.item(), 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemManager.Nuggets.item(), 9, 1),
				new ItemStack(ItemManager.Ingots.item(), 1, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemManager.Nuggets.item(), 9, 2),
				new ItemStack(ItemManager.Ingots.item(), 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemManager.Nuggets.item(), 9, 3),
				new ItemStack(ItemManager.Ingots.item(), 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemManager.Nuggets.item(), 9, 4),
				new ItemStack(ItemManager.Ingots.item(), 1, 4));
	}

	public static void addWorkbenchRecipes() {
		workbench.addRecipe(new OreStack("plankWood"), new OreStack("toolFile"),
				new ItemStack(ItemManager.Gears_Wood.item(), 1, 5), ForestDayConfig.worktableBurnTime);
		workbench.addRecipe(new ItemStack(ItemManager.Gears_Wood.item(), 1, 5), new OreStack("toolFile"),
				new ItemStack(ItemManager.Gears_Wood.item(), 1, 4), ForestDayConfig.worktableBurnTime);
		workbench.addRecipe(new ItemStack(ItemManager.Gears_Wood.item(), 1, 4), new OreStack("toolFile"),
				new ItemStack(ItemManager.Gears_Wood.item(), 1, 3), ForestDayConfig.worktableBurnTime);
		workbench.addRecipe(new ItemStack(ItemManager.Gears_Wood.item(), 1, 3), new OreStack("toolFile"),
				new ItemStack(ItemManager.Gears_Wood.item(), 1, 2), ForestDayConfig.worktableBurnTime);
		workbench.addRecipe(new ItemStack(ItemManager.Gears_Wood.item(), 1, 2), new OreStack("toolFile"),
				new ItemStack(ItemManager.Gears_Wood.item(), 1, 1), ForestDayConfig.worktableBurnTime);
		workbench.addRecipe(new OreStack("plankWood"), new OreStack("toolKnife"),
				new ItemStack(ItemManager.Bucket_Wood.item()), ForestDayConfig.worktableBurnTime);
		workbench.addOutput(new ItemStack(ItemManager.Bucket_Wood.item()));
		workbench.addOutput(new ItemStack(ItemManager.Gears_Wood.item(), 1, 1));
	}

	public static void addMachineRecipes() {

		// Furenace
		CraftingUtil.removeAnyRecipe(new ItemStack(Blocks.furnace));
		addShapedRecipe(new ItemStack(Blocks.furnace), "SSS", "BHB", "BBB", 'S', "stone", 'B', Blocks.stonebrick);

		// Campfire
		addShapedRecipe(new ItemStack(ItemManager.Curb.item(), 1, 0), "+++", "+ +", "+++", '+', "cobblestone");
		addShapedRecipe(new ItemStack(ItemManager.Curb.item(), 1, 1), "+++", "+ +", "+++", '+', "blockObsidian");

		addShapedRecipe(new ItemStack(ItemManager.Pot.item(), 1, 0), "   ", "+ +", "+++", '+', "stone");
		addShapedRecipe(new ItemStack(ItemManager.Pot.item(), 1, 1), "   ", "+ +", "+++", '+', "ingotBronze");
		addShapedRecipe(new ItemStack(ItemManager.Pot.item(), 1, 2), "   ", "+ +", "+++", '+', "ingotIron");
		addShapedRecipe(new ItemStack(ItemManager.Pot.item(), 1, 3), "   ", "+ +", "+++", '+', "ingotSteel");

		addShapedRecipe(new ItemStack(ItemManager.Pot_Holder.item()), "++ ", "+  ", "   ", '+', "logWood");
		addShapedRecipe(new ItemStack(ItemManager.Pot_Holder.item(), 1, 1), "++ ", "+  ", "+  ", '+', "stone");
		addShapedRecipe(new ItemStack(ItemManager.Pot_Holder.item(), 1, 2), "++ ", "+  ", "+  ", '+', "ingotBronze");
		addShapedRecipe(new ItemStack(ItemManager.Pot_Holder.item(), 1, 3), "++ ", "+  ", "+  ", '+', "ingotIron");

		addShapedRecipe(new ItemStack(BlockManager.Machine.item(), 1, 1), "---", "+++", "W W", '-',
				Blocks.crafting_table, '+', "slabWood", 'W', "logWood");
		addShapedRecipe(new ItemStack(BlockManager.Machine.item(), 1, 2), "---", "+++", "WCW", '-',
				Blocks.crafting_table, '+', "slabWood", 'W', "logWood", 'C', Blocks.chest);
		addShapelessRecipe(new ItemStack(BlockManager.Machine.item(), 1, 2),
				new ItemStack(BlockManager.Machine.item(), 1, 1), Blocks.chest);
		addShapedRecipe(new ItemStack(BlockManager.Machine.item(), 1, 3), "ILI", "ICI", "ILI", 'I', "ingotIron", 'C',
				Blocks.chest, 'L', BlockManager.Gravel.item());

	}

	public static void addCampfireRecipes() {
		campfire.addRecipe(new ItemStack(Blocks.red_mushroom), new ItemStack(Items.bowl),
				new ItemStack(Items.mushroom_stew), 0, 25);
		campfire.addRecipe(new ItemStack(Blocks.brown_mushroom), new ItemStack(Items.bowl),
				new ItemStack(Items.mushroom_stew), 0, 25);
		campfire.addRecipe(new ItemStack(Items.beef), new ItemStack(Items.cooked_beef), 0, 25);
		campfire.addRecipe(new ItemStack(Items.chicken), new ItemStack(Items.cooked_chicken), 0, 25);
		campfire.addRecipe(new ItemStack(Items.fish), new ItemStack(Items.cooked_fished), 0, 25);
		campfire.addRecipe(new ItemStack(Items.porkchop), new ItemStack(Items.cooked_porkchop), 0, 25);

		campfire.addRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.stone), 0, 10);
	}

	public static enum BlockManager implements IBlockManager {

		Ore, Gravel, Crop_Corn, Machine, Multiblock, Multiblock_Valve;

		private Block block;

		@Override
		public void register(Block block, Class<? extends ItemBlock> item, Object... objects) {
			this.block = block;
			Registry.registerBlock(block, item,
					block.getUnlocalizedName().replace("tile.", "").replace("forest.fd.", ""), objects);
		}

		@Override
		public boolean isItemEqual(ItemStack stack) {
			return stack != null && isBlockEqual(Block.getBlockFromItem(stack.getItem()));
		}

		@Override
		public boolean isBlockEqual(Block i) {
			return i != null && Block.isEqualTo(block, i);
		}

		@Override
		public boolean isBlockEqual(World world, int x, int y, int z) {
			return isBlockEqual(world.getBlock(x, y, z));
		}

		@Override
		public Item item() {
			return Item.getItemFromBlock(block);
		}

		@Override
		public Block block() {
			return block;
		}

		@Override
		public Block getObject() {
			return block;
		}

	}

	public static enum ItemManager implements IItemManager {
		Nature, Crop_Corn,

		Bucket_Wood, Bucket_Wood_Water, Ingots, Nuggets, Gems, Gears_Wood,

		// Campfire
		Curb, Pot, Pot_Holder,

		// Tools
		File_Stone, File_Iron, File_Diamond, Knife_Stone, Cutter, Hammer, Adze, Adze_Long, Axe_Flint, Tool_Parts;

		private Item item;

		@Override
		public void register(Item item, Object... objects) {
			this.item = item;
			Registry.registerItem(item, item.getUnlocalizedName().replace("forest.fd.item.", "").replace("item.", ""));
		}

		@Override
		public boolean isItemEqual(ItemStack stack) {
			return stack != null && this.item == stack.getItem();
		}

		@Override
		public boolean isItemEqual(Item i) {
			return i != null && this.item == i;
		}

		@Override
		public Item getObject() {
			return item;
		}

		@Override
		public Item item() {
			return item;
		}

	}

	@Override
	public boolean isActive() {
		return true;
	}

	@SubscribeEvent
	public void stickDrop(BlockEvent.HarvestDropsEvent event) {
		Random r = new Random();
		if (event.block == Blocks.leaves || event.block == Blocks.leaves2) {
			if (r.nextInt(16) == 0) {
				event.drops.add(new ItemStack(Items.stick));
			}
		}
	}

}
