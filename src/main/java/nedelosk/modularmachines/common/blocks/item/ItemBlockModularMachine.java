package nedelosk.modularmachines.common.blocks.item;

import nedelosk.modularmachines.common.blocks.tile.TileModularMachine;
import nedelosk.modularmachines.common.core.MMBlocks;
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
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_,float p_77648_10_) {
        Block block = world.getBlock(x, y, z);

        if (block == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1)
        {
            p_77648_7_ = 1;
        }
        else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush && !block.isReplaceable(world, x, y, z))
        {
            if (p_77648_7_ == 0)
            {
                --y;
            }

            if (p_77648_7_ == 1)
            {
                ++y;
            }

            if (p_77648_7_ == 2)
            {
                --z;
            }

            if (p_77648_7_ == 3)
            {
                ++z;
            }

            if (p_77648_7_ == 4)
            {
                --x;
            }

            if (p_77648_7_ == 5)
            {
                ++x;
            }
        }

        if (stack.stackSize == 0)
        {
            return false;
        }
	    if (!player.canPlayerEdit(x, z, y, p_77648_7_, stack))
	    {
	        return false;
	    }
	    else if(!world.isRemote)
	    {
			boolean placed = world.setBlock(x, y, z, MMBlocks.Modular_Machine.block(), 0, 2);
			if (!placed) {
				return false;
			}

			TileEntity tile = world.getTileEntity(x, y, z);
			if (!(tile instanceof TileModularMachine)) {
				world.setBlockToAir(x, y, z);
				return false;
			}

			TileModularMachine machine = (TileModularMachine) tile;
			machine.setMachine(stack.getTagCompound());
			world.markBlockForUpdate(x, y, z);
			world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F, this.field_150939_a.stepSound.getPitch() * 0.8F);
			stack.stackSize--;

			return true;
	    }
		return false;
	}

}
