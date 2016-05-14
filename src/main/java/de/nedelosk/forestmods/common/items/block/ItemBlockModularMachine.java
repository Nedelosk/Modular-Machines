package de.nedelosk.forestmods.common.items.block;

import de.nedelosk.forestmods.common.blocks.tile.TileModular;
import de.nedelosk.forestmods.common.core.BlockManager;
import de.nedelosk.forestmods.common.modular.Modular;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemBlockModularMachine extends ItemBlock {

	public ItemBlockModularMachine(Block p_i45328_1_) {
		super(p_i45328_1_);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_,
			float p_77648_10_) {
		if (stack.getItemDamage() == 0) {
			Block block = world.getBlock(x, y, z);
			if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1) {
				p_77648_7_ = 1;
			} else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(world, x, y, z)) {
				if (p_77648_7_ == 0) {
					--y;
				}
				if (p_77648_7_ == 1) {
					++y;
				}
				if (p_77648_7_ == 2) {
					--z;
				}
				if (p_77648_7_ == 3) {
					++z;
				}
				if (p_77648_7_ == 4) {
					--x;
				}
				if (p_77648_7_ == 5) {
					++x;
				}
			}
			if (stack.stackSize == 0) {
				return false;
			}
			if (!player.canPlayerEdit(x, z, y, p_77648_7_, stack)) {
				return false;
			} else if (!world.isRemote) {
				boolean placed = world.setBlock(x, y, z, BlockManager.blockModular, 0, 2);
				if (!placed) {
					return false;
				}
				TileEntity tile = world.getTileEntity(x, y, z);
				if (!(tile instanceof TileModular)) {
					world.setBlockToAir(x, y, z);
					return false;
				}
				if (world.getBlock(x, y, z) == field_150939_a) {
					field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
					field_150939_a.onPostBlockPlaced(world, x, y, z, 0);
				}
				TileModular machine = (TileModular) tile;
				machine.setModular(new Modular(stack.getTagCompound(), machine));
				world.markBlockForUpdate(x, y, z);
				world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, this.field_150939_a.stepSound.func_150496_b(),
						(this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F, this.field_150939_a.stepSound.getPitch() * 0.8F);
				stack.stackSize--;
				return true;
			}
		} else {
			return super.onItemUse(stack, player, world, x, y, z, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
		}
		return false;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack).replace("tile.", "") + "." + stack.getItemDamage() + ".name";
	}

	@Override
	public int getMetadata(int i) {
		return i;
	}
}
