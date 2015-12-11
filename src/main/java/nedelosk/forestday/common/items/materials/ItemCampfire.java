package nedelosk.forestday.common.items.materials;

import java.util.List;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.blocks.tiles.TileCampfire;
import nedelosk.forestday.common.core.registry.FRegistry;
import nedelosk.forestday.common.items.base.ItemForest;
import nedelosk.forestday.common.modules.ModuleCore;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCampfire extends ItemForest {

	public String[] textures;
	public String itemName;

	public ItemCampfire(String[] textures, String itemName) {
		super(null, Tabs.tabForestday);
		setHasSubtypes(true);
		setUnlocalizedName("campfire." + itemName);
		this.textures = textures;
		this.itemName = itemName;
		setTextureName("forestday:ash");
	}

	@Override
	public void getSubItems(Item id, CreativeTabs tab, List list) {
		for (int i = 0; i < textures.length; i++)
			list.add(new ItemStack(id, 1, i));
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ) {
		if (this == ModuleCore.ItemManager.Curb.getObject()) {
			Block block = world.getBlock(x, y, z);
			Block blockC = ModuleCore.BlockManager.Machine.getObject();

			if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1) {
				side = 1;
			} else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush
					&& !block.isReplaceable(world, x, y, z)) {
				if (side == 0) {
					--y;
				}

				if (side == 1) {
					++y;
				}

				if (side == 2) {
					--z;
				}

				if (side == 3) {
					++z;
				}

				if (side == 4) {
					--x;
				}

				if (side == 5) {
					++x;
				}
			}

			if (stack.stackSize == 0) {
				return false;
			} else if (!player.canPlayerEdit(x, y, z, side, stack)) {
				return false;
			} else if (y == 255 && blockC.getMaterial().isSolid()) {
				return false;
			} else if (world.canPlaceEntityOnSide(blockC, x, y, z, false, side, player, stack)) {
				int i1 = 0;
				int j1 = blockC.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, i1);

				if (placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, j1)) {
					world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, blockC.stepSound.func_150496_b(),
							(blockC.stepSound.getVolume() + 1.0F) / 2.0F, blockC.stepSound.getPitch() * 0.8F);
					--stack.stackSize;
				}

				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ, int metadata) {

		Block blockC = ModuleCore.BlockManager.Machine.getObject();

		if (!world.setBlock(x, y, z, blockC, metadata, 3)) {
			return false;
		}

		TileCampfire kiln = (TileCampfire) world.getTileEntity(x, y, z);
		kiln.setCampfireItem(stack);

		if (world.getBlock(x, y, z) == blockC) {
			blockC.onBlockPlacedBy(world, x, y, z, player, stack);
			blockC.onPostBlockPlaced(world, x, y, z, metadata);
		}

		return true;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return FRegistry.setUnlocalizedItemName("campfire." + itemName + "." + itemstack.getItemDamage(), "fd");
	}

}
