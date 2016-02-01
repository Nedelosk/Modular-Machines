package nedelosk.forestday.modules;

import java.util.Map.Entry;
import java.util.Set;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import nedelosk.forestcore.library.BlockPos;
import nedelosk.forestcore.library.core.Registry;
import nedelosk.forestcore.library.modules.AModule;
import nedelosk.forestcore.library.modules.IModuleManager;
import nedelosk.forestcore.library.modules.manager.IBlockManager;
import nedelosk.forestcore.library.utils.CraftingUtil;
import nedelosk.forestday.api.crafting.ForestDayCrafting;
import nedelosk.forestday.api.crafting.IWoodTypeManager;
import nedelosk.forestday.api.crafting.WoodType;
import nedelosk.forestday.common.blocks.items.ItemBlockCharcoalKiln;
import nedelosk.forestday.common.configs.ForestDayConfig;
import nedelosk.forestday.common.crafting.WoodTypeManager;
import nedelosk.forestday.common.multiblock.BlockCharcoalKiln;
import nedelosk.forestday.common.multiblock.TileCharcoalKiln;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class ModuleCoal extends AModule {

	public ModuleCoal() {
		super("Coal");
	}

	private IWoodTypeManager woodManager;

	@Override
	public void preInit(IModuleManager manager) {
		MinecraftForge.EVENT_BUS.register(this);
		manager.register(BlockManager.Multiblock_Charcoal_Kiln, new BlockCharcoalKiln(), ItemBlockCharcoalKiln.class);
		GameRegistry.registerTileEntity(TileCharcoalKiln.class, "machien.multi.kiln.charcoal");
		woodManager = ForestDayCrafting.woodManager = new WoodTypeManager();
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

	private void registerCharcoalKilnRecipes() {
		for ( WoodType type : woodManager.getWoodTypes().values() ) {
			if (type != null && type.getWood() != null) {
				addShapedRecipe(ModuleCoal.writeWoodType(type), "+++", "+-+", "+++", '+', type.getWood(), '-', ModuleCore.BlockManager.Gravel.block());
			}
		}
	}

	public static enum BlockManager implements IBlockManager {
		Multiblock_Charcoal_Kiln;

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
}
