package nedelosk.forestday.common.machines.mutiblock.charcoalkiln;

import java.util.List;

import nedelosk.forestday.api.Tabs;
import nedelosk.forestday.common.items.base.ItemForestday;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemCharcoalKiln extends ItemForestday {
	
	public ItemCharcoalKiln() {
		super("charcoal.kiln.", Tabs.tabForestdayBlocks, true);
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(WoodType typ : WoodTypeManager.woodTypes)
		{
			ItemStack stack = new ItemStack(item);
			stack.setTagCompound(new NBTTagCompound());
			typ.writeToNBT(stack.getTagCompound());
			list.add(stack);
		}
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
			//boolean placed = world.setBlock(x, y, z, FBlocks.Multiblock_Charcoal_Kiln.block(), 0, 2);
			//if (!placed) {
			//	return false;
			//}

			TileEntity tile = world.getTileEntity(x, y, z);
			if (!(tile instanceof TileCharcoalKiln)) {
				world.setBlockToAir(x, y, z);
				return false;
			}

			TileCharcoalKiln kiln = (TileCharcoalKiln) tile;
			kiln.setType(WoodType.loadFromNBT(stack.getTagCompound()));
			world.markBlockForUpdate(x, y, z);
			
			stack.stackSize--;

			return true;
	    }
		return false;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return super.getItemStackDisplayName(stack) + " " + WoodType.loadFromNBT(stack.getTagCompound()).wood.getDisplayName();
	}
}
