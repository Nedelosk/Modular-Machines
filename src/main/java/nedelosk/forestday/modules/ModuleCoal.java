package nedelosk.forestday.modules;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestcore.library.core.Registry;
import nedelosk.forestcore.library.modules.AModule;
import nedelosk.forestcore.library.modules.IModuleManager;
import nedelosk.forestcore.library.modules.manager.IBlockManager;
import nedelosk.forestday.api.crafting.ForestdayCrafting;
import nedelosk.forestday.api.crafting.IWoodTypeManager;
import nedelosk.forestday.common.blocks.BlockCharcoalKiln;
import nedelosk.forestday.common.blocks.tiles.TileCharcoalAsh;
import nedelosk.forestday.common.blocks.tiles.TileCharcoalKiln;
import nedelosk.forestday.common.configs.ForestDayConfig;
import nedelosk.forestday.common.items.blocks.ItemBlockForestDay;
import nedelosk.forestday.common.multiblocks.MultiblockCharcoalKiln;
import nedelosk.forestday.common.types.WoodType;
import nedelosk.forestday.common.types.WoodTypeManager;
import nedelosk.forestday.common.types.WoodTypeManager.WoodTypeStack;
import nedelosk.forestday.modules.ModuleCore.ItemManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ModuleCoal extends AModule {

	public ModuleCoal() {
		super("Coal");
	}

	public static IWoodTypeManager woodManager;

	@Override
	public void preInit(IModuleManager manager) {

		MinecraftForge.EVENT_BUS.register(this);

		new MultiblockCharcoalKiln();

		manager.register(BlockManager.Multiblock_Charcoal_Kiln, new BlockCharcoalKiln(), ItemBlockForestDay.class);

		GameRegistry.registerTileEntity(TileCharcoalKiln.class, "machien.multi.kiln.charcoal");
		GameRegistry.registerTileEntity(TileCharcoalAsh.class, "machien.multi.kiln.charcoal.ash");

	}

	@Override
	public void init(IModuleManager manager) {
		woodManager = ForestdayCrafting.woodManager = new WoodTypeManager();

		woodManager.add(new ItemStack(Blocks.log, 1, 0), new ItemStack(Items.coal, 2, 1));
		woodManager.add(new ItemStack(Blocks.log, 1, 1), new ItemStack(Items.coal, 1, 1),
				new ItemStack(ItemManager.Nature.item(), 1, 3));
		woodManager.add(new ItemStack(Blocks.log, 1, 2), new ItemStack(Items.coal, 2, 1));
		woodManager.add(new ItemStack(Blocks.log, 1, 3), new ItemStack(Items.coal, 2, 1));
	}

	public static enum BlockManager implements IBlockManager {

		Multiblock_Charcoal_Kiln;

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
		public Block getObject() {
			return block;
		}

		@Override
		public Block block() {
			return block;
		}

	}

	@Override
	public boolean isActive() {
		return ForestDayConfig.isModuleCoalActive;
	}

	@SubscribeEvent
	public void onPlayerUse(PlayerInteractEvent event) {
		if (!event.world.isRemote) {
			EntityPlayer player = event.entityPlayer;
			World world = event.world;
			int x = event.x;
			int y = event.y;
			int z = event.z;

			if (player.getCurrentEquippedItem() != null
					&& (player.getCurrentEquippedItem().getItem() == Items.flint_and_steel
							|| player.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(Blocks.torch))) {
				ItemStack stack = player.getCurrentEquippedItem();
				for (int xPos = 0; xPos < 3; xPos++) {
					for (int zPos = 0; zPos < 3; zPos++) {
						for (int yPos = 0; yPos < 2; yPos++) {
							Block block = world.getBlock(x + xPos - 1, y + yPos - 1, z + zPos - 1);
							if (!block.isWood(world, x + xPos - 1, y + yPos - 1, z + zPos - 1))
								return;
						}
					}
				}
				for (int xPos = 0; xPos < 3; xPos++) {
					for (int zPos = 0; zPos < 3; zPos++) {
						for (int yPos = 0; yPos < 2; yPos++) {
							Block block = world.getBlock(x + xPos - 1, y + yPos - 1, z + zPos - 1);
							int damage = world.getBlockMetadata(x + xPos - 1, y + yPos - 1, z + zPos - 1);
							if (world.setBlock(x + xPos - 1, y + yPos - 1, z + zPos - 1,
									ModuleCoal.BlockManager.Multiblock_Charcoal_Kiln.getObject())) {
								TileCharcoalKiln kiln = (TileCharcoalKiln) world.getTileEntity(x + xPos - 1,
										y + yPos - 1, z + zPos - 1);
								WoodType type;
								if (WoodTypeManager.woodTypes.equals(new WoodTypeStack(block, damage)))
									type = WoodTypeManager.woodTypes.get(new WoodTypeStack(block, damage));
								else
									type = new WoodType(new ItemStack(block, 1, damage),
											new ItemStack(Items.coal, 1, 1));
								kiln.setType(type);
							}
						}
					}
				}
				TileCharcoalKiln kiln = (TileCharcoalKiln) world.getTileEntity(x, y, z);
				kiln.setWorking(true);
				stack.damageItem(1, player);
				kiln.testMultiblock();
				if (player.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(Blocks.torch)) {
					player.getCurrentEquippedItem().stackSize--;
					if (player.getCurrentEquippedItem().stackSize == 0)
						player.setCurrentItemOrArmor(0, null);
				}
			}
		}
	}

}
