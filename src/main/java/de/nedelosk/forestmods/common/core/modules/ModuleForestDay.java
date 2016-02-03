package de.nedelosk.forestmods.common.core.modules;

import static net.minecraftforge.oredict.OreDictionary.registerOre;

import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import de.nedelosk.forestcore.core.Registry;
import de.nedelosk.forestcore.items.ItemBlockForest;
import de.nedelosk.forestcore.modules.AModule;
import de.nedelosk.forestcore.modules.IModuleManager;
import de.nedelosk.forestcore.modules.manager.IBlockManager;
import de.nedelosk.forestcore.modules.manager.IItemManager;
import de.nedelosk.forestcore.utils.BlockPos;
import de.nedelosk.forestcore.utils.CraftingUtil;
import de.nedelosk.forestcore.utils.OreStack;
import de.nedelosk.forestmods.api.crafting.ForestDayCrafting;
import de.nedelosk.forestmods.api.crafting.ICampfireRecipe;
import de.nedelosk.forestmods.api.crafting.IWoodTypeManager;
import de.nedelosk.forestmods.api.crafting.IWorkbenchRecipe;
import de.nedelosk.forestmods.api.crafting.WoodType;
import de.nedelosk.forestmods.common.blocks.BlockCharcoalKiln;
import de.nedelosk.forestmods.common.blocks.BlockGravel;
import de.nedelosk.forestmods.common.blocks.BlockMachinesWood;
import de.nedelosk.forestmods.common.blocks.BlockOre;
import de.nedelosk.forestmods.common.blocks.tile.TileAsh;
import de.nedelosk.forestmods.common.blocks.tile.TileCampfire;
import de.nedelosk.forestmods.common.blocks.tile.TileCharcoalKiln;
import de.nedelosk.forestmods.common.blocks.tile.TileWorkbench;
import de.nedelosk.forestmods.common.config.Config;
import de.nedelosk.forestmods.common.crafting.CampfireRecipeManager;
import de.nedelosk.forestmods.common.crafting.WoodTypeManager;
import de.nedelosk.forestmods.common.crafting.WorkbenchRecipeManager;
import de.nedelosk.forestmods.common.items.ItemCampfire;
import de.nedelosk.forestmods.common.items.ItemCutter;
import de.nedelosk.forestmods.common.items.ItemFile;
import de.nedelosk.forestmods.common.items.ItemFlintAxe;
import de.nedelosk.forestmods.common.items.ItemGearWood;
import de.nedelosk.forestmods.common.items.ItemGem;
import de.nedelosk.forestmods.common.items.ItemIngot;
import de.nedelosk.forestmods.common.items.ItemNature;
import de.nedelosk.forestmods.common.items.ItemNugget;
import de.nedelosk.forestmods.common.items.ItemToolCrafting;
import de.nedelosk.forestmods.common.items.ItemToolForestday.Material;
import de.nedelosk.forestmods.common.items.ItemToolParts;
import de.nedelosk.forestmods.common.items.ItemWoodBucket;
import de.nedelosk.forestmods.common.items.block.ItemBlockCharcoalKiln;
import de.nedelosk.forestmods.common.items.block.ItemBlockMachines;
import de.nedelosk.forestmods.common.network.PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public class ModuleForestDay extends AModule {

	public static IWorkbenchRecipe workbench;
	public static ICampfireRecipe campfire;
	private IWoodTypeManager woodManager;

	public ModuleForestDay() {
		super("ForestDay");
	}

	@Override
	public void preInit(IModuleManager manager) {
		ForestDayCrafting.workbenchRecipe = new WorkbenchRecipeManager();
		ForestDayCrafting.campfireRecipe = new CampfireRecipeManager();
		workbench = ForestDayCrafting.workbenchRecipe;
		campfire = ForestDayCrafting.campfireRecipe;
		MinecraftForge.EVENT_BUS.register(this);
		PacketHandler.preInit();
		manager.register(BlockManager.Gravel, new BlockGravel(), ItemBlockForest.class);
		manager.register(BlockManager.Machine, new BlockMachinesWood("wood_base", TileCampfire.class, TileWorkbench.class, TileWorkbench.class), ItemBlockMachines.class);
		GameRegistry.registerTileEntity(TileWorkbench.class, "machine.wood.workbench");
		GameRegistry.registerTileEntity(TileCampfire.class, "machine.wood.campfire");
		GameRegistry.registerTileEntity(TileAsh.class, "machine.wood.ash");
		BlockManager.Machine.block().setHarvestLevel("axe", 0, 1);
		BlockManager.Machine.block().setHarvestLevel("axe", 0, 2);
		manager.register(ItemManager.Gears_Wood, new ItemGearWood());
		manager.register(ItemManager.Nature, new ItemNature());
		manager.register(ItemManager.Curb, new ItemCampfire(Config.campfireCurbs, "curb"));
		manager.register(ItemManager.Pot, new ItemCampfire(Config.campfirePots, "pot"));
		manager.register(ItemManager.Pot_Holder, new ItemCampfire(Config.campfirePotHolders, "pot_holder"));
		manager.register(ItemManager.Tool_Parts, new ItemToolParts());
		manager.register(ItemManager.File_Stone, new ItemFile(50, 5, "file.stone", "file_stone", 1, Material.Stone));
		manager.register(ItemManager.File_Iron, new ItemFile(150, 2, "file.iron", "file", 2, Material.Iron));
		manager.register(ItemManager.File_Diamond, new ItemFile(300, 1, "file.diamond", "file_diamond", 3, Material.Diamond));
		manager.register(ItemManager.Hammer, new ItemToolCrafting("hammer", 300, 0, Material.Iron, "hammer", 15));
		manager.register(ItemManager.Knife_Stone, new ItemToolCrafting("knife", 200, 1, Material.Iron, "knife", 5));
		manager.register(ItemManager.Cutter, new ItemCutter(250, 10, "cutter", "cutter", 1, Material.Iron));
		manager.register(ItemManager.Axe_Flint, new ItemFlintAxe(EnumHelper.addToolMaterial("flint", 1, 50, 1, 0, 1)));
		manager.register(ItemManager.Adze, new ItemToolCrafting("adze", 175, 1, Material.Stone, "adze", 3));
		manager.register(ItemManager.Adze_Long, new ItemToolCrafting("adze.long", 175, 1, Material.Stone, "adze.long", 3));
		manager.register(ItemManager.Gems, new ItemGem());
		manager.register(BlockManager.Multiblock_Charcoal_Kiln, new BlockCharcoalKiln(), ItemBlockCharcoalKiln.class);
		GameRegistry.registerTileEntity(TileCharcoalKiln.class, "machien.multi.kiln.charcoal");
		woodManager = ForestDayCrafting.woodManager = new WoodTypeManager();
		// Tools
		registerOre("toolFile", ItemManager.File_Stone.getObject());
		registerOre("toolFile", ItemManager.File_Iron.getObject());
		registerOre("toolHammer", ItemManager.Hammer.getObject());
		registerOre("toolFile", ItemManager.File_Diamond.getObject());
		registerOre("toolCutter", ItemManager.Cutter.getObject());
		registerOre("toolKnife", ItemManager.Knife_Stone.getObject());
		registerOre("gearWood", new ItemStack(ItemManager.Gears_Wood.getObject(), 1, 1));
		registerOre("gemRuby", new ItemStack(ItemManager.Gems.getObject(), 1, 0));
	}

	@Override
	public void init(IModuleManager manager) {
		registerRecipes();
	}

	@Override
	public void postInit(IModuleManager manager) {
		for ( Entry<ItemStack, ItemStack> recipes : (Set<Entry<ItemStack, ItemStack>>) FurnaceRecipes.smelting().getSmeltingList().entrySet() ) {
			if (recipes.getValue().getItem() == Items.coal && recipes.getValue().getItemDamage() == 1
					&& Block.getBlockFromItem(recipes.getKey().getItem()) != null) {
				woodManager.add(recipes.getKey().getUnlocalizedName(), recipes.getKey(), recipes.getValue());
			}
		}
		registerCharcoalKilnRecipes();
		CraftingUtil.removeFurnaceRecipe(Items.coal, 1);
	}

	public static void registerRecipes() {
		addMachineRecipes();
		addCampfireRecipes();
		addWorkbenchRecipes();
		addNormalRecipes();
	}

	public static void addNormalRecipes() {
		addShapelessRecipe(new ItemStack(BlockManager.Gravel.item(), 4), Blocks.dirt, Blocks.dirt, Blocks.dirt, Blocks.dirt, Blocks.gravel, Blocks.gravel,
				Blocks.gravel, Blocks.gravel, Blocks.sand);
		addShapelessRecipe(new ItemStack(ItemManager.Axe_Flint.item()), Items.stick, Items.stick, Items.flint, Items.flint);
		addShapelessRecipe(new ItemStack(ItemManager.Tool_Parts.item()), Items.stick, Items.leather);
		addShapelessRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 1), new ItemStack(Blocks.stone_slab, 1, 3), new ItemStack(Blocks.stone_slab, 1, 3),
				Items.flint, Items.flint);
		addShapelessRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 2), Items.iron_ingot, Items.iron_ingot, Items.flint, Items.flint);
		addShapelessRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 3), Items.diamond, Items.diamond);
		addShapelessRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 4), Items.stick, Items.stick, Items.stick, Items.leather);
		addShapelessRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 5), new ItemStack(Blocks.stone_slab, 1, 3), new ItemStack(Blocks.stone_slab, 1, 3),
				new ItemStack(Blocks.stone_slab, 1, 3), Items.flint);
		addShapelessRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 6), Items.iron_ingot, Items.iron_ingot, Items.flint);
		addShapelessRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 7), Items.stick, Items.stick);
		addShapedRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 8), "   ", "+++", "  +", '+', Blocks.cobblestone);
		addShapedRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 9), "+++", "  +", "  +", '+', Blocks.cobblestone);
		addShapedRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 11), "  +", " + ", "+  ", '+', "plankWood");
		addShapedRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 10), "  +", " + ", "   ", '+', "plankWood");
		addShapedRecipe(new ItemStack(ItemManager.Tool_Parts.item(), 1, 10), "   ", " + ", "+  ", '+', "plankWood");
		addShapelessRecipe(new ItemStack(ItemManager.File_Stone.item()), new ItemStack(ItemManager.Tool_Parts.item(), 1, 0),
				new ItemStack(ItemManager.Tool_Parts.item(), 1, 1));
		addShapelessRecipe(new ItemStack(ItemManager.File_Iron.item()), new ItemStack(ItemManager.Tool_Parts.item(), 1, 0),
				new ItemStack(ItemManager.Tool_Parts.item(), 1, 2));
		addShapelessRecipe(new ItemStack(ItemManager.File_Diamond.item()), new ItemStack(ItemManager.Tool_Parts.item(), 1, 0),
				new ItemStack(ItemManager.Tool_Parts.item(), 1, 3));
		addShapelessRecipe(new ItemStack(ItemManager.Knife_Stone.item()), new ItemStack(ItemManager.Tool_Parts.item(), 1, 4),
				new ItemStack(ItemManager.Tool_Parts.item(), 1, 5));
		addShapelessRecipe(new ItemStack(ItemManager.Cutter.item()), new ItemStack(ItemManager.Tool_Parts.item(), 1, 6),
				new ItemStack(ItemManager.Tool_Parts.item(), 1, 7));
		addShapelessRecipe(new ItemStack(ItemManager.Adze.item()), new ItemStack(ItemManager.Tool_Parts.item(), 1, 8),
				new ItemStack(ItemManager.Tool_Parts.item(), 1, 10));
		addShapelessRecipe(new ItemStack(ItemManager.Adze_Long.item()), new ItemStack(ItemManager.Tool_Parts.item(), 1, 9),
				new ItemStack(ItemManager.Tool_Parts.item(), 1, 11));
		addShapedRecipe(new ItemStack(ItemManager.Hammer.item()), "+++", "+++", " - ", '+', "ingotIron", '-', "stickWood");
		addShapelessRecipe(new ItemStack(ItemManager.Nature.item(), 1, 3), Blocks.sand, Blocks.sand, Blocks.gravel, Blocks.gravel, Items.water_bucket);
		addShapelessRecipe(new ItemStack(ItemManager.Nature.item(), 1, 3), Blocks.sand, Blocks.sand, Blocks.gravel, Blocks.gravel,
				ModuleCore.ItemManager.Bucket_Wood_Water.item());
	}

	public static void addWorkbenchRecipes() {
		workbench.addRecipe(new OreStack("plankWood"), new OreStack("toolFile"), new ItemStack(ItemManager.Gears_Wood.item(), 1, 5), Config.worktableBurnTime);
		workbench.addRecipe(new ItemStack(ItemManager.Gears_Wood.item(), 1, 5), new OreStack("toolFile"), new ItemStack(ItemManager.Gears_Wood.item(), 1, 4),
				Config.worktableBurnTime);
		workbench.addRecipe(new ItemStack(ItemManager.Gears_Wood.item(), 1, 4), new OreStack("toolFile"), new ItemStack(ItemManager.Gears_Wood.item(), 1, 3),
				Config.worktableBurnTime);
		workbench.addRecipe(new ItemStack(ItemManager.Gears_Wood.item(), 1, 3), new OreStack("toolFile"), new ItemStack(ItemManager.Gears_Wood.item(), 1, 2),
				Config.worktableBurnTime);
		workbench.addRecipe(new ItemStack(ItemManager.Gears_Wood.item(), 1, 2), new OreStack("toolFile"), new ItemStack(ItemManager.Gears_Wood.item(), 1, 1),
				Config.worktableBurnTime);
		workbench.addRecipe(new OreStack("plankWood"), new OreStack("toolKnife"), new ItemStack(ModuleCore.ItemManager.Bucket_Wood.item()), Config.worktableBurnTime);
		workbench.addOutput(new ItemStack(ModuleCore.ItemManager.Bucket_Wood.item()));
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
		addShapedRecipe(new ItemStack(BlockManager.Machine.item(), 1, 1), "---", "+++", "W W", '-', Blocks.crafting_table, '+', "slabWood", 'W', "logWood");
		addShapedRecipe(new ItemStack(BlockManager.Machine.item(), 1, 2), "---", "+++", "WCW", '-', Blocks.crafting_table, '+', "slabWood", 'W', "logWood", 'C',
				Blocks.chest);
		addShapelessRecipe(new ItemStack(BlockManager.Machine.item(), 1, 2), new ItemStack(BlockManager.Machine.item(), 1, 1), Blocks.chest);
		addShapedRecipe(new ItemStack(BlockManager.Machine.item(), 1, 3), "ILI", "ICI", "ILI", 'I', "ingotIron", 'C', Blocks.chest, 'L',
				BlockManager.Gravel.item());
	}

	public static void addCampfireRecipes() {
		campfire.addRecipe(new ItemStack(Blocks.red_mushroom), new ItemStack(Items.bowl), new ItemStack(Items.mushroom_stew), 0, 25);
		campfire.addRecipe(new ItemStack(Blocks.brown_mushroom), new ItemStack(Items.bowl), new ItemStack(Items.mushroom_stew), 0, 25);
		campfire.addRecipe(new ItemStack(Items.beef), new ItemStack(Items.cooked_beef), 0, 25);
		campfire.addRecipe(new ItemStack(Items.chicken), new ItemStack(Items.cooked_chicken), 0, 25);
		campfire.addRecipe(new ItemStack(Items.fish), new ItemStack(Items.cooked_fished), 0, 25);
		campfire.addRecipe(new ItemStack(Items.porkchop), new ItemStack(Items.cooked_porkchop), 0, 25);
		campfire.addRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.stone), 0, 10);
	}

	private void registerCharcoalKilnRecipes() {
		for ( WoodType type : woodManager.getWoodTypes().values() ) {
			if (type != null && type.getWood() != null) {
				addShapedRecipe(writeWoodType(type), "+++", "+-+", "+++", '+', type.getWood(), '-', ModuleForestDay.BlockManager.Gravel.block());
			}
		}
	}

	public static ItemStack writeWoodType(WoodType type) {
		if (type != null) {
			ItemStack stack = new ItemStack(BlockManager.Multiblock_Charcoal_Kiln.block());
			NBTTagCompound nbtTag = new NBTTagCompound();
			nbtTag.setString("WoodType", type.getName());
			stack.setTagCompound(nbtTag);
			return stack;
		}
		return null;
	}

	public static WoodType readFromStack(ItemStack stack) {
		if (stack != null && stack.getItem() != null && stack.hasTagCompound() && stack.getTagCompound().hasKey("WoodType")
				&& Block.getBlockFromItem(stack.getItem()) != null && Block.getBlockFromItem(stack.getItem()) instanceof BlockCharcoalKiln) {
			return ForestDayCrafting.woodManager.get(stack.getTagCompound().getString("WoodType"));
		}
		return null;
	}

	public static enum BlockManager implements IBlockManager {
		Gravel, Machine, Multiblock_Charcoal_Kiln;

		private Block block;

		@Override
		public void register(Block block, Class<? extends ItemBlock> item, Object... objects) {
			this.block = block;
			Registry.registerBlock(block, item, block.getUnlocalizedName().replace("tile.", "").replace("forest.fd.", ""), objects);
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
		Nature, Gems, Gears_Wood,
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

	@SubscribeEvent
	public void onPlayerUse(PlayerInteractEvent event) {
		if (!event.world.isRemote && event.action == Action.RIGHT_CLICK_BLOCK) {
			EntityPlayer player = event.entityPlayer;
			World world = event.world;
			int x = event.x;
			int y = event.y;
			int z = event.z;
			if (!(world.getTileEntity(x, y, z) instanceof TileCharcoalKiln)) {
				return;
			}
			ItemStack stack = player.getCurrentEquippedItem();
			if (stack != null && (stack.getItem() == Items.flint_and_steel || stack.getItem() == Item.getItemFromBlock(Blocks.torch))) {
				TileCharcoalKiln kiln = (TileCharcoalKiln) world.getTileEntity(x, y, z);
				if (kiln.isConnected() && kiln.getController().isAssembled() && !kiln.getController().isActive()) {
					kiln.getController().setIsActive();
					if (stack.getItem() == Items.flint_and_steel) {
						stack.damageItem(1, player);
					}
					BlockPos min = kiln.getController().getMinimumCoord();
					BlockPos max = kiln.getController().getMaximumCoord();
					for ( int xPos = min.x; xPos < max.x; xPos++ ) {
						for ( int zPos = min.z; zPos < max.z; zPos++ ) {
							for ( int yPos = min.y; yPos < max.y; yPos++ ) {
								world.markBlockForUpdate(xPos, yPos, zPos);
							}
						}
					}
				}
			}
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
