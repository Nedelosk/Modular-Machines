package de.nedelosk.forestmods.common.core.modules;

import static de.nedelosk.forestmods.common.core.modules.ModuleCore.ItemManager.Component_Rods;
import static de.nedelosk.forestmods.common.core.modules.ModuleCore.ItemManager.Dusts;
import static de.nedelosk.forestmods.common.core.modules.ModuleCore.ItemManager.Ingots;
import static de.nedelosk.forestmods.common.core.modules.ModuleCore.ItemManager.Nuggets;
import static net.minecraftforge.oredict.OreDictionary.registerOre;

import cpw.mods.fml.common.registry.GameRegistry;
import de.nedelosk.forestcore.core.Registry;
import de.nedelosk.forestcore.items.ItemBlockForest;
import de.nedelosk.forestcore.modules.AModule;
import de.nedelosk.forestcore.modules.AModuleManager;
import de.nedelosk.forestcore.modules.IModuleManager;
import de.nedelosk.forestcore.modules.manager.IBlockManager;
import de.nedelosk.forestcore.modules.manager.IItemManager;
import de.nedelosk.forestcore.utils.OreStack;
import de.nedelosk.forestmods.api.crafting.ForestDayCrafting;
import de.nedelosk.forestmods.api.crafting.IWorkbenchRecipe;
import de.nedelosk.forestmods.api.modules.machines.recipes.RecipeLathe.LatheModes;
import de.nedelosk.forestmods.api.recipes.RecipeItem;
import de.nedelosk.forestmods.common.blocks.BlockBlastFurnace;
import de.nedelosk.forestmods.common.blocks.BlockComponent;
import de.nedelosk.forestmods.common.blocks.BlockCowper;
import de.nedelosk.forestmods.common.blocks.BlockOre;
import de.nedelosk.forestmods.common.config.Config;
import de.nedelosk.forestmods.common.core.Constants;
import de.nedelosk.forestmods.common.core.ForestMods;
import de.nedelosk.forestmods.common.events.EventHandler;
import de.nedelosk.forestmods.common.items.ItemComponent;
import de.nedelosk.forestmods.common.items.ItemMetal;
import de.nedelosk.forestmods.common.items.ItemWoodBucket;
import de.nedelosk.forestmods.common.network.PacketHandler;
import de.nedelosk.forestmods.common.utils.RecipeUtils;
import de.nedelosk.forestmods.common.world.WorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleCore extends AModule {

	public static Fluid White_Pig_Iron;
	public static Fluid Gray_Pig_Iron;
	public static Fluid Steel;
	public static Fluid Slag;
	public static Fluid Gas_Blastfurnace;
	public static Fluid Air_Hot;
	public static Fluid Air;
	public static Config config;
	private static String[] alloys = new String[] { "Bronze", "Invar" };
	private static String[] steels = new String[] { "Steel", "White_Steel", "Gray_Steel" };
	private static String[] default_metals = new String[] { "Copper", "Tin", "Silver", "Lead", "Nickel", "Aluminum", "Niobium", "Tantalum" };
	private static String[] only_dust = new String[] { "Columbite", "Ruby" };
	private static String[] vanilla = new String[] { "Coal", "Obsidian", "Iron", "Gold", "Diamond" };
	/* metal for ingots, nuggets, blocks */
	public static String[][] metals = new String[][] { default_metals, alloys, steels };
	public static String[][] dusts = new String[][] { vanilla, only_dust, default_metals, alloys, steels };

	public ModuleCore() {
		super("Core");
	}

	@Override
	public void preInit(IModuleManager manager) {
		registerFluids();
		config = new Config();
		Config.config = new Configuration(ForestMods.configFile, Constants.VERSION);
		Config.syncConfig(false);
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		PacketHandler.preInit();
		manager.register(BlockManager.Ore, new BlockOre(), ItemBlockForest.class);
		manager.register(BlockManager.Metal_Blocks, new BlockComponent(Material.iron, "metal_block"), ItemBlockForest.class);
		BlockManager.Metal_Blocks.addMetaData(0xCACECF, "tin", "Tin");
		BlockManager.Metal_Blocks.addMetaData(0xCC6410, "copper", "Copper");
		BlockManager.Metal_Blocks.addMetaData(0xCA9956, "bronze", "Bronze");
		BlockManager.Metal_Blocks.addMetaData(0xA0A0A0, "steel", "Steel");
		BlockManager.Metal_Blocks.addMetaData(0xA1A48C, "invar", "Invar");
		manager.register(BlockManager.Blast_Furnace, new BlockBlastFurnace(), ItemBlockForest.class);
		manager.register(BlockManager.Cowper, new BlockCowper(), ItemBlockForest.class);
		manager.register(ItemManager.Dusts, new ItemMetal("dusts", "dust", dusts));
		manager.register(ItemManager.Ingots, new ItemMetal("ingots", "ingot", metals));
		manager.register(ItemManager.Nuggets, new ItemMetal("nuggets", "nugget", metals));
		manager.register(ItemManager.Component_Wire, new ItemComponent("wires"));
		manager.register(ItemManager.Component_Rods, new ItemComponent("rods"));
		manager.register(ItemManager.Component_Screws, new ItemComponent("screws"));
		manager.register(ItemManager.Component_Gears, new ItemComponent("gears"));
		manager.register(ItemManager.Component_Plates, new ItemComponent("plates"));
		manager.register(ItemManager.Component_Saw_Blades, new ItemComponent("saw_blades"));
		manager.register(ItemManager.Bucket_Wood, new ItemWoodBucket(Blocks.air, "bucket.wood").setMaxStackSize(16));
		manager.register(ItemManager.Bucket_Wood_Water,
				new ItemWoodBucket(Blocks.water, "bucket.wood.water").setContainerItem(ItemManager.Bucket_Wood.getObject()));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER, new ItemStack(ItemManager.Bucket_Wood_Water.getObject()),
				new ItemStack(ItemManager.Bucket_Wood.getObject()));
		ItemManager.Component_Wire.addMetaData(0xDADADA, "iron", "Iron");
		ItemManager.Component_Wire.addMetaData(0xCACECF, "tin", "Tin");
		ItemManager.Component_Wire.addMetaData(0xCC6410, "copper", "Copper");
		ItemManager.Component_Wire.addMetaData(0xCA9956, "bronze", "Bronze");
		ItemManager.Component_Wire.addMetaData(0xA0A0A0, "steel", "Steel");
		ItemManager.Component_Rods.addMetaData(0x7F7F7F, "stone");
		ItemManager.Component_Rods.addMetaData(0xDADADA, "iron", "Iron");
		ItemManager.Component_Rods.addMetaData(0xCACECF, "tin", "Tin");
		ItemManager.Component_Rods.addMetaData(0xCC6410, "copper", "Copper");
		ItemManager.Component_Rods.addMetaData(0xCA9956, "bronze", "Bronze");
		ItemManager.Component_Rods.addMetaData(0xA0A0A0, "steel", "Steel");
		ItemManager.Component_Rods.addMetaData(0xD4E3E6, "plastic", "Plastic");
		ItemManager.Component_Screws.addMetaData(0xDADADA, "iron", "Iron");
		ItemManager.Component_Screws.addMetaData(0xCACECF, "tin", "Tin");
		ItemManager.Component_Screws.addMetaData(0xCC6410, "copper", "Copper");
		ItemManager.Component_Screws.addMetaData(0xCA9956, "bronze", "Bronze");
		ItemManager.Component_Screws.addMetaData(0xA0A0A0, "steel", "Steel");
		ItemManager.Component_Gears.addMetaData(0x7F7F7F, "stone");
		ItemManager.Component_Gears.addMetaData(0xDADADA, "iron", "Iron");
		ItemManager.Component_Gears.addMetaData(0xCACECF, "tin", "Tin");
		ItemManager.Component_Gears.addMetaData(0xCC6410, "copper", "Copper");
		ItemManager.Component_Gears.addMetaData(0xCA9956, "bronze", "Bronze");
		ItemManager.Component_Gears.addMetaData(0xA0A0A0, "steel", "Steel");
		ItemManager.Component_Plates.addMetaData(0x7F7F7F, "stone");
		ItemManager.Component_Plates.addMetaData(0xDADADA, "iron", "Iron");
		ItemManager.Component_Plates.addMetaData(0xCACECF, "tin", "Tin");
		ItemManager.Component_Plates.addMetaData(0xCC6410, "copper", "Copper");
		ItemManager.Component_Plates.addMetaData(0xCA9956, "bronze", "Bronze");
		ItemManager.Component_Plates.addMetaData(0xA0A0A0, "steel", "Steel");
		ItemManager.Component_Plates.addMetaData(0xD4E3E6, "plastic", "Plastic");
		ItemManager.Component_Plates.addMetaData(0xA1A48C, "invar", "Invar");
		ItemManager.Component_Plates.addMetaData(0xA1A48C, "aluminum", "Aluminum", "Aluminium");
		ItemManager.Component_Saw_Blades.addMetaData(0x7F7F7F, "stone");
		ItemManager.Component_Saw_Blades.addMetaData(0xDADADA, "iron", "Iron");
		ItemManager.Component_Saw_Blades.addMetaData(0xCA9956, "bronze", "Bronze");
		ItemManager.Component_Saw_Blades.addMetaData(0xA0A0A0, "steel", "Steel");
	}

	@Override
	public void init(IModuleManager manager) {
		for ( int m = 0; m < metals.length; m++ ) {
			String[] metal = metals[m];
			for ( int i = 0; i < metal.length; ++i ) {
				registerOre("ingot" + metal[i], new ItemStack(Ingots.item(), 1, m * 10 + i));
				registerOre("nugget" + metal[i], new ItemStack(Nuggets.item(), 1, m * 10 + i));
			}
		}
		for ( int d = 0; d < dusts.length; d++ ) {
			String[] dust = dusts[d];
			for ( int i = 0; i < dust.length; ++i ) {
				registerOre("dust" + dust[i], new ItemStack(Dusts.item(), 1, d * 10 + i));
			}
		}
		registerOre("ingotAluminium", new ItemStack(Ingots.item(), 1, 5));
		registerOre("nuggetAluminium", new ItemStack(Nuggets.item(), 1, 5));
		registerOre("dustAluminium", new ItemStack(Dusts.item(), 1, 25));
		registerOre("oreCopper", new ItemStack(BlockManager.Ore.getObject(), 1, 0));
		registerOre("oreTin", new ItemStack(BlockManager.Ore.getObject(), 1, 1));
		registerOre("oreSilver", new ItemStack(BlockManager.Ore.getObject(), 1, 2));
		registerOre("oreLead", new ItemStack(BlockManager.Ore.getObject(), 1, 3));
		registerOre("oreNickel", new ItemStack(BlockManager.Ore.getObject(), 1, 4));
		registerOre("oreRuby", new ItemStack(BlockManager.Ore.getObject(), 1, 5));
		registerOre("oreColumbite", new ItemStack(BlockManager.Ore.item(), 1, 6));
		registerOre("oreAluminium", new ItemStack(BlockManager.Ore.item(), 1, 7));
		registerOre("oreAluminum", new ItemStack(BlockManager.Ore.item(), 1, 7));
		registerOre("blockObsidian", Blocks.obsidian);
		registerOre("plateStone", new ItemStack(ItemManager.Component_Plates.item()));
		registerOre("sawBladeStone", new ItemStack(ItemManager.Component_Saw_Blades.item()));
		registerOre("gearStone", new ItemStack(ItemManager.Component_Gears.item()));
		registerOre("rodStone", new ItemStack(ItemManager.Component_Rods.item()));
		registerOre("itemCoal", new ItemStack(Items.coal));
		for ( int i = 0; i < ((ItemComponent) ItemManager.Component_Plates.item()).metas.size(); i++ ) {
			ItemStack stack = new ItemStack(ItemManager.Component_Plates.item(), 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			for ( String oreDict : (String[]) component.metas.get(i).get(2) ) {
				registerOre("plate" + oreDict, stack);
			}
		}
		for ( int i = 0; i < ((ItemComponent) ItemManager.Component_Rods.item()).metas.size(); i++ ) {
			ItemStack stack = new ItemStack(ItemManager.Component_Rods.item(), 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.metas.get(i).get(2) != null) {
				for ( String oreDict : (String[]) component.metas.get(i).get(2) ) {
					registerOre("rod" + oreDict, stack);
				}
			}
		}
		for ( int i = 0; i < ((ItemComponent) ItemManager.Component_Screws.item()).metas.size(); i++ ) {
			ItemStack stack = new ItemStack(ItemManager.Component_Screws.item(), 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.metas.get(i).get(2) != null) {
				for ( String oreDict : (String[]) component.metas.get(i).get(2) ) {
					registerOre("screw" + oreDict, stack);
				}
			}
		}
		for ( int i = 0; i < ((ItemComponent) ItemManager.Component_Gears.item()).metas.size(); i++ ) {
			ItemStack stack = new ItemStack(ItemManager.Component_Gears.item(), 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.metas.get(i).get(2) != null) {
				for ( String oreDict : (String[]) component.metas.get(i).get(2) ) {
					registerOre("gear" + oreDict, stack);
				}
			}
		}
		for ( int i = 0; i < ((ItemComponent) ItemManager.Component_Wire.item()).metas.size(); i++ ) {
			ItemStack stack = new ItemStack(ItemManager.Component_Wire.item(), 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.metas.get(i).get(2) != null) {
				for ( String oreDict : (String[]) component.metas.get(i).get(2) ) {
					registerOre("wire" + oreDict, stack);
				}
			}
		}
		for ( int i = 0; i < ((ItemComponent) ItemManager.Component_Saw_Blades.item()).metas.size(); i++ ) {
			ItemStack stack = new ItemStack(ItemManager.Component_Saw_Blades.item(), 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.metas.get(i).get(2) != null) {
				for ( String oreDict : (String[]) component.metas.get(i).get(2) ) {
					registerOre("sawBlade" + oreDict, stack);
				}
			}
		}
		for ( int i = 0; i < ((BlockComponent) BlockManager.Metal_Blocks.block()).metas.size(); i++ ) {
			ItemStack stack = new ItemStack(BlockManager.Metal_Blocks.block(), 1, i);
			if (((BlockComponent) BlockManager.Metal_Blocks.block()).metas.get(i).get(2) != null) {
				for ( String oreDict : (String[]) ((BlockComponent) BlockManager.Metal_Blocks.block()).metas.get(i).get(2) ) {
					registerOre("block" + oreDict, stack);
				}
			}
		}
		registerComponentRecipes();
		registerMetalRecipes();
	}

	@Override
	public void postInit(IModuleManager manager) {
		Config.init();
		GameRegistry.registerWorldGenerator(new WorldGenerator(), 0);
	}

	@Override
	public boolean isActive() {
		return true;
	}

	public static void registerFluids() {
		White_Pig_Iron = Registry.registerFluid("white_pig_iron", 1500, Material.lava, true, false).setDensity(3000).setViscosity(6000);
		Gray_Pig_Iron = Registry.registerFluid("gray_pig_iron", 1500, Material.lava, true, false).setDensity(3000).setViscosity(6000);
		Steel = Registry.registerFluid("steel", 1500, Material.lava, true, false).setDensity(3000).setViscosity(6000);
		Slag = Registry.registerFluid("slag", 100, Material.lava, true, false).setDensity(3000).setViscosity(6000);
		Gas_Blastfurnace = Registry.registerFluid("gas_blastfurnace", 200, Material.water, true, true);
		Air_Hot = Registry.registerFluid("air_hot", 750, Material.lava, true, true);
		Air = Registry.registerFluid("air", 0, Material.water, true, true);
	}

	public static enum BlockManager implements IBlockManager {
		Ore, Metal_Blocks, Blast_Furnace, Cowper;

		private Block block;

		@Override
		public void register(Block block, Class<? extends ItemBlock> item, Object... objects) {
			this.block = block;
			Registry.registerBlock(block, item, block.getUnlocalizedName().replace("tile.", "").replace("forest.mm.", ""), objects);
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

		public void addMetaData(int color, String name, String... oreDict) {
			if (block instanceof BlockComponent) {
				BlockComponent.addMetaData(this, color, name, oreDict);
			}
		}
	}

	public static enum ItemManager implements IItemManager {
		// Metals
		Dusts, Ingots, Bucket_Wood, Bucket_Wood_Water, Nuggets,
		// Crafting
		Metallic,
		// Components
		Component_Wire, Component_Rods, Component_Gears, Component_Plates, Component_Screws, Component_Saw_Blades;

		private Item item;

		@Override
		public void register(Item item, Object... objects) {
			this.item = item;
			Registry.registerItem(item, item.getUnlocalizedName().replace("forest.mm.item.", "").replace("item.", ""));
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

		public void addMetaData(int color, String name, String... oreDict) {
			if (item instanceof ItemComponent) {
				ItemComponent.addMetaData(this, color, name, oreDict);
			}
		}
	}

	public static void registerMetalRecipes() {
		for ( int m = 0; m < metals.length; m++ ) {
			String[] metal = metals[m];
			for ( int i = 0; i < metal.length; ++i ) {
				addShapedRecipe(new ItemStack(Ingots.item(), 1, m * 10 + i), "+++", "+++", "+++", '+', "nugget" + metal[i]);
				addShapelessRecipe(new ItemStack(Nuggets.item(), 9, m * 10 + i), "ingot" + metal[i]);
			}
		}
		GameRegistry.addSmelting(new ItemStack(Dusts.item(), 1, 2), new ItemStack(Items.iron_ingot), 0.5F);
		GameRegistry.addSmelting(new ItemStack(Dusts.item(), 1, 3), new ItemStack(Items.gold_ingot), 0.5F);
		GameRegistry.addSmelting(new ItemStack(Dusts.item(), 1, 20), new ItemStack(Ingots.item(), 1, 0), 0.5F);
		GameRegistry.addSmelting(new ItemStack(Dusts.item(), 1, 21), new ItemStack(Ingots.item(), 1, 1), 0.5F);
		GameRegistry.addSmelting(new ItemStack(Dusts.item(), 1, 22), new ItemStack(Ingots.item(), 1, 2), 0.5F);
		GameRegistry.addSmelting(new ItemStack(Dusts.item(), 1, 23), new ItemStack(Ingots.item(), 1, 3), 0.5F);
		GameRegistry.addSmelting(new ItemStack(Dusts.item(), 1, 24), new ItemStack(Ingots.item(), 1, 4), 0.5F);
		GameRegistry.addSmelting(new ItemStack(Dusts.item(), 1, 25), new ItemStack(Ingots.item(), 1, 5), 0.5F);
		GameRegistry.addSmelting(new ItemStack(Dusts.item(), 1, 26), new ItemStack(Ingots.item(), 1, 6), 0.5F);
		GameRegistry.addSmelting(new ItemStack(Dusts.item(), 1, 27), new ItemStack(Ingots.item(), 1, 7), 0.5F);
	}

	public static void registerComponentRecipes() {
		if (!AModuleManager.isModuleLoaded("ForestDay")) {
			addShapedRecipe(new ItemStack(ItemManager.Bucket_Wood.item()), "   ", "W W", " W ", 'W', "logWood");
		}
		if (AModuleManager.isModuleLoaded("ForestDay")) {
			IWorkbenchRecipe manager = ForestDayCrafting.workbenchRecipe;
			manager.addRecipe(new OreStack("cobblestone", 2), new OreStack("toolHammer"), new ItemStack(ItemManager.Component_Plates.item()), 100);
			for ( int i = 0; i < ((ItemComponent) ItemManager.Component_Plates.item()).metas.size(); i++ ) {
				ItemStack stack = new ItemStack(ItemManager.Component_Plates.item(), 1, i);
				ItemComponent component = (ItemComponent) stack.getItem();
				if (component.metas.get(i).get(2) != null) {
					for ( String oreDict : (String[]) component.metas.get(i).get(2) ) {
						manager.addRecipe(new OreStack("ingot" + oreDict, 2), new OreStack("toolHammer"), stack, 100);
					}
				}
			}
			for ( int i = 0; i < ((ItemComponent) ItemManager.Component_Wire.item()).metas.size(); i++ ) {
				ItemStack stack = new ItemStack(ItemManager.Component_Wire.item(), 4, i);
				ItemComponent component = (ItemComponent) stack.getItem();
				if (component.metas.get(i).get(2) != null) {
					for ( String oreDict : (String[]) component.metas.get(i).get(2) ) {
						if (!oreDict.equals("Bronze") && !oreDict.equals("Steel")) {
							manager.addRecipe(new OreStack("plate" + oreDict, 1), new OreStack("toolCutter"), stack, 100);
						} else {
							RecipeUtils.addLathe("plate" + oreDict + "ToWire", new RecipeItem(new OreStack("plate" + oreDict)),
									new RecipeItem(new ItemStack(ItemManager.Component_Wire.item(), 8, i)), 1, 250, LatheModes.WIRE);
						}
					}
				}
			}
		}
		for ( int i = 0; i < ((ItemComponent) ItemManager.Component_Screws.item()).metas.size(); i++ ) {
			ItemStack stack = new ItemStack(ItemManager.Component_Screws.item(), 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.metas.get(i).get(2) != null) {
				for ( String oreDict : (String[]) component.metas.get(i).get(2) ) {
					RecipeUtils.addLathe("wire" + oreDict + "ToScrew", new RecipeItem(new OreStack("wire" + oreDict, 2)), new RecipeItem(stack), 1, 250,
							LatheModes.SCREW);
				}
			}
		}
		for ( int i = 0; i < ((BlockComponent) BlockManager.Metal_Blocks.block()).metas.size(); i++ ) {
			ItemStack stack = new ItemStack(BlockManager.Metal_Blocks.item(), 1, i);
			if (((BlockComponent) BlockManager.Metal_Blocks.block()).metas.get(i).get(2) != null) {
				for ( String oreDict : (String[]) ((BlockComponent) BlockManager.Metal_Blocks.block()).metas.get(i).get(2) ) {
					addShapedRecipe(stack, "+++", "+++", "+++", '+', "ingot" + oreDict);
				}
			}
		}
		for ( int i = 0; i < ((BlockComponent) BlockManager.Metal_Blocks.block()).metas.size(); i++ ) {
			ItemStack stack = new ItemStack(BlockManager.Metal_Blocks.item(), 1, i);
			if (((BlockComponent) BlockManager.Metal_Blocks.block()).metas.get(i).get(2) != null) {
				for ( String oreDict : (String[]) ((BlockComponent) BlockManager.Metal_Blocks.block()).metas.get(i).get(2) ) {
					if (OreDictionary.getOres("ingot" + oreDict) != null && !OreDictionary.getOres("ingot" + oreDict).isEmpty()) {
						ItemStack ore = OreDictionary.getOres("ingot" + oreDict).get(0);
						ore.stackSize = 9;
						addShapelessRecipe(ore, stack);
					}
				}
			}
		}
		addShapelessRecipe(new ItemStack(ItemManager.Component_Rods.item()), "cobblestone", "cobblestone", "cobblestone");
		addShapedRecipe(new ItemStack(ItemManager.Component_Saw_Blades.item()), " + ", "+-+", " + ", '+', new ItemStack(Component_Rods.item()), '-',
				"cobblestone");
		addShapedRecipe(new ItemStack(ItemManager.Component_Gears.item()), " + ", "+-+", " + ", '+', "plateStone", '-', "cobblestone");
	}
}
