package nedelosk.modularmachines.modules;

import static nedelosk.modularmachines.api.recipes.RecipeRegistry.registerRecipe;
import static nedelosk.modularmachines.modules.ModuleCore.ItemManager.Component_Rods;
import static net.minecraftforge.oredict.OreDictionary.registerOre;

import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestcore.library.core.Registry;
import nedelosk.forestcore.library.items.ItemBlockForest;
import nedelosk.forestcore.library.modules.AModule;
import nedelosk.forestcore.library.modules.IModuleManager;
import nedelosk.forestcore.library.modules.manager.IBlockManager;
import nedelosk.forestcore.library.modules.manager.IItemManager;
import nedelosk.forestday.api.crafting.ForestDayCrafting;
import nedelosk.forestday.api.crafting.IWorkbenchRecipe;
import nedelosk.forestday.api.crafting.OreStack;
import nedelosk.forestday.common.blocks.BlockOre;
import nedelosk.forestday.common.items.materials.ItemIngot;
import nedelosk.forestday.common.items.materials.ItemNugget;
import nedelosk.modularmachines.api.modules.machines.recipes.RecipeLathe;
import nedelosk.modularmachines.api.modules.machines.recipes.RecipeLathe.LatheModes;
import nedelosk.modularmachines.api.recipes.RecipeItem;
import nedelosk.modularmachines.common.blocks.BlockComponent;
import nedelosk.modularmachines.common.config.Config;
import nedelosk.modularmachines.common.events.EventHandler;
import nedelosk.modularmachines.common.items.ItemComponent;
import nedelosk.modularmachines.common.items.materials.ItemAlloyIngot;
import nedelosk.modularmachines.common.items.materials.ItemAlloyNugget;
import nedelosk.modularmachines.common.items.materials.ItemDusts;
import nedelosk.modularmachines.common.multiblock.blastfurnace.BlockBlastFurnace;
import nedelosk.modularmachines.common.multiblock.cowper.BlockCowper;
import nedelosk.modularmachines.common.world.WorldGeneratorModularMachines;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.OreDictionary;

public class ModuleCore extends AModule {

	public static Fluid White_Pig_Iron;
	public static Fluid Gray_Pig_Iron;
	public static Fluid Steel;
	public static Fluid Slag;
	public static Fluid Gas_Blastfurnace;
	public static Fluid Air_Hot;
	public static Fluid Air;
	public static String[] oreOtherOres = new String[] { "Columbite", "Aluminum" };
	public static String[] ingotsOther = new String[] { "Niobium", "Tantalum", "Aluminum", "Steel", "White_Steel", "Gray_Steel" };

	public ModuleCore() {
		super("MCore");
	}

	@Override
	public void preInit(IModuleManager manager) {
		registerFluids();
		Config.preInit();
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		manager.register(BlockManager.Ore_Others, new BlockOre(oreOtherOres, "modularmachines"), ItemBlockForest.class);
		manager.register(BlockManager.Component_Metal_Blocks, new BlockComponent(Material.iron, "metal_block"), ItemBlockForest.class);
		BlockManager.Component_Metal_Blocks.addMetaData(0xCACECF, "tin", "Tin");
		BlockManager.Component_Metal_Blocks.addMetaData(0xCC6410, "copper", "Copper");
		BlockManager.Component_Metal_Blocks.addMetaData(0xCA9956, "bronze", "Bronze");
		BlockManager.Component_Metal_Blocks.addMetaData(0xA0A0A0, "steel", "Steel");
		BlockManager.Component_Metal_Blocks.addMetaData(0xA1A48C, "invar", "Invar");
		manager.register(BlockManager.Blast_Furnace, new BlockBlastFurnace(), ItemBlockForest.class);
		manager.register(BlockManager.Cowper, new BlockCowper(), ItemBlockForest.class);
		manager.register(ItemManager.Dusts, new ItemDusts(ItemDusts.dusts, ""));
		manager.register(ItemManager.Dusts_Others, new ItemDusts(ItemDusts.dustsOtherOres, ".other"));
		manager.register(ItemManager.Alloy_Ingots, new ItemAlloyIngot());
		manager.register(ItemManager.Ingots_Others, new ItemIngot(ingotsOther, "modularmachines"));
		manager.register(ItemManager.Alloy_Nuggets, new ItemAlloyNugget());
		manager.register(ItemManager.Nuggets_Others, new ItemNugget(ingotsOther, "modularmachines"));
		manager.register(ItemManager.Component_Wire, new ItemComponent("wires"));
		manager.register(ItemManager.Component_Rods, new ItemComponent("rods"));
		manager.register(ItemManager.Component_Screws, new ItemComponent("screws"));
		manager.register(ItemManager.Component_Gears, new ItemComponent("gears"));
		manager.register(ItemManager.Component_Plates, new ItemComponent("plates"));
		manager.register(ItemManager.Component_Saw_Blades, new ItemComponent("saw_blades"));
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
		registerOre("dustCoal", new ItemStack(ItemManager.Dusts.item(), 1, 0));
		registerOre("dustObsidian", new ItemStack(ItemManager.Dusts.item(), 1, 1));
		registerOre("dustIron", new ItemStack(ItemManager.Dusts.item(), 1, 2));
		registerOre("dustGold", new ItemStack(ItemManager.Dusts.item(), 1, 3));
		registerOre("dustDiamond", new ItemStack(ItemManager.Dusts.item(), 1, 4));
		registerOre("dustCopper", new ItemStack(ItemManager.Dusts.item(), 1, 5));
		registerOre("dustTin", new ItemStack(ItemManager.Dusts.item(), 1, 6));
		registerOre("dustSilver", new ItemStack(ItemManager.Dusts.item(), 1, 7));
		registerOre("dustLead", new ItemStack(ItemManager.Dusts.item(), 1, 8));
		registerOre("dustNickel", new ItemStack(ItemManager.Dusts.item(), 1, 9));
		registerOre("dustBronze", new ItemStack(ItemManager.Dusts.item(), 1, 10));
		registerOre("dustInvar", new ItemStack(ItemManager.Dusts.item(), 1, 11));
		registerOre("dustRuby", new ItemStack(ItemManager.Dusts.item(), 1, 12));
		registerOre("ingotBronze", new ItemStack(ItemManager.Alloy_Ingots.item(), 1, 0));
		registerOre("ingotInvar", new ItemStack(ItemManager.Alloy_Ingots.item(), 1, 1));
		registerOre("nuggetBronze", new ItemStack(ItemManager.Alloy_Nuggets.item(), 1, 0));
		registerOre("nuggetInvar", new ItemStack(ItemManager.Alloy_Nuggets.item(), 1, 1));
		registerOre("oreColumbite", new ItemStack(BlockManager.Ore_Others.item(), 1, 0));
		registerOre("oreAluminium", new ItemStack(BlockManager.Ore_Others.item(), 1, 1));
		registerOre("oreAluminum", new ItemStack(BlockManager.Ore_Others.item(), 1, 1));
		registerOre("ingotNiobium", new ItemStack(ItemManager.Ingots_Others.item(), 1, 0));
		registerOre("nuggetNiobium", new ItemStack(ItemManager.Nuggets_Others.item(), 1, 0));
		registerOre("ingotTantalum", new ItemStack(ItemManager.Ingots_Others.item(), 1, 1));
		registerOre("nuggetTantalum", new ItemStack(ItemManager.Nuggets_Others.item(), 1, 1));
		registerOre("ingotAluminium", new ItemStack(ItemManager.Ingots_Others.item(), 1, 2));
		registerOre("nuggetAluminium", new ItemStack(ItemManager.Nuggets_Others.item(), 1, 2));
		registerOre("ingotAluminum", new ItemStack(ItemManager.Ingots_Others.item(), 1, 2));
		registerOre("nuggetAluminum", new ItemStack(ItemManager.Nuggets_Others.item(), 1, 2));
		registerOre("dustColumbite", new ItemStack(ItemManager.Dusts_Others.item(), 1, 0));
		registerOre("dustNiobium", new ItemStack(ItemManager.Dusts_Others.item(), 1, 1));
		registerOre("dustTantalum", new ItemStack(ItemManager.Dusts_Others.item(), 1, 2));
		registerOre("dustAluminium", new ItemStack(ItemManager.Dusts_Others.item(), 1, 3));
		registerOre("dustAlumium", new ItemStack(ItemManager.Dusts_Others.item(), 1, 3));
		registerOre("blockObsidian", Blocks.obsidian);
		registerOre("plateStone", new ItemStack(ItemManager.Component_Plates.item()));
		registerOre("sawBladeStone", new ItemStack(ItemManager.Component_Saw_Blades.item()));
		registerOre("gearStone", new ItemStack(ItemManager.Component_Gears.item()));
		registerOre("rodStone", new ItemStack(ItemManager.Component_Rods.item()));
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
		for ( int i = 0; i < ((BlockComponent) BlockManager.Component_Metal_Blocks.block()).metas.size(); i++ ) {
			ItemStack stack = new ItemStack(BlockManager.Component_Metal_Blocks.block(), 1, i);
			if (((BlockComponent) BlockManager.Component_Metal_Blocks.block()).metas.get(i).get(2) != null) {
				for ( String oreDict : (String[]) ((BlockComponent) BlockManager.Component_Metal_Blocks.block()).metas.get(i).get(2) ) {
					registerOre("block" + oreDict, stack);
				}
			}
		}
		registerComponentRecipes();
	}

	@Override
	public void postInit(IModuleManager manager) {
		Config.init();
		GameRegistry.registerWorldGenerator(new WorldGeneratorModularMachines(), 0);
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
		Ore_Others, Component_Metal_Blocks, Blast_Furnace, Cowper;

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
		Dusts, Dusts_Others, Ingots_Others, Nuggets_Others, Alloy_Ingots, Alloy_Nuggets,
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

	public static void registerComponentRecipes() {
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
						registerRecipe(new RecipeLathe(new RecipeItem(new OreStack("plate" + oreDict)),
								new RecipeItem(new ItemStack(ItemManager.Component_Wire.item(), 8, i)), 1, 250, LatheModes.WIRE));
					}
				}
			}
		}
		for ( int i = 0; i < ((ItemComponent) ItemManager.Component_Screws.item()).metas.size(); i++ ) {
			ItemStack stack = new ItemStack(ItemManager.Component_Screws.item(), 1, i);
			ItemComponent component = (ItemComponent) stack.getItem();
			if (component.metas.get(i).get(2) != null) {
				for ( String oreDict : (String[]) component.metas.get(i).get(2) ) {
					registerRecipe(new RecipeLathe(new RecipeItem(new OreStack("wire" + oreDict, 2)), new RecipeItem(stack), 1, 250, LatheModes.SCREW));
				}
			}
		}
		for ( int i = 0; i < ((BlockComponent) BlockManager.Component_Metal_Blocks.block()).metas.size(); i++ ) {
			ItemStack stack = new ItemStack(BlockManager.Component_Metal_Blocks.item(), 1, i);
			if (((BlockComponent) BlockManager.Component_Metal_Blocks.block()).metas.get(i).get(2) != null) {
				for ( String oreDict : (String[]) ((BlockComponent) BlockManager.Component_Metal_Blocks.block()).metas.get(i).get(2) ) {
					addShapedRecipe(stack, "+++", "+++", "+++", '+', "ingot" + oreDict);
				}
			}
		}
		for ( int i = 0; i < ((BlockComponent) BlockManager.Component_Metal_Blocks.block()).metas.size(); i++ ) {
			ItemStack stack = new ItemStack(BlockManager.Component_Metal_Blocks.item(), 1, i);
			if (((BlockComponent) BlockManager.Component_Metal_Blocks.block()).metas.get(i).get(2) != null) {
				for ( String oreDict : (String[]) ((BlockComponent) BlockManager.Component_Metal_Blocks.block()).metas.get(i).get(2) ) {
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
