package nedelosk.modularmachines.common.blocks.item;

import nedelosk.modularmachines.api.materials.Material;
import nedelosk.modularmachines.api.materials.MaterialType;
import nedelosk.modularmachines.api.modular.utils.ModuleStack;
import nedelosk.modularmachines.api.parts.IMachinePart;
import nedelosk.modularmachines.api.parts.PartType;
import nedelosk.modularmachines.common.blocks.tile.TileModular;
import nedelosk.modularmachines.common.core.manager.MMBlockManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

public class ItemBlockModular extends ItemBlock implements IMachinePart {

	public ItemBlockModular(Block p_i45328_1_) {
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
			boolean placed = world.setBlock(x, y, z, MMBlockManager.Modular_Machine.block(), 0, 2);
			if (!placed) {
				return false;
			}

			TileEntity tile = world.getTileEntity(x, y, z);
			if (!(tile instanceof TileModular)) {
				world.setBlockToAir(x, y, z);
				return false;
			}

			TileModular machine = (TileModular) tile;
			machine.setMachine(stack.getTagCompound());
			world.markBlockForUpdate(x, y, z);
			world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F, this.field_150939_a.stepSound.getPitch() * 0.8F);
			stack.stackSize--;

			return true;
	    }
		return false;
	}

	@Override
	public MaterialType getMaterialType() {
		return null;
	}

	@Override
	public Material getMaterial(ItemStack stack) {
		return null;
	}

	@Override
	public String getTagKey() {
		return null;
	}

	@Override
	public ItemStack buildItemFromStacks(ItemStack[] stacks) {
		return null;
	}

	@Override
	public boolean validComponent(int slot, ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack getMachine(ItemStack stack) {
		return null;
	}

	@Override
	public PartType[] getMachineComponents() {
		return null;
	}

	@Override
	public String getPartName() {
		return "modular.machines";
	}

	@Override
	public IItemRenderer getPartRenderer() {
		return null;
	}

	@Override
	public Material[] getMaterials(ItemStack stack) {
		return null;
	}

	@Override
	public ModuleStack buildModule(ItemStack stack) {
		return null;
	}

}
