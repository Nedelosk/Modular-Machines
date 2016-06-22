package de.nedelosk.modularmachines.common.items.blocks;

import de.nedelosk.modularmachines.common.blocks.tile.TileModular;
import de.nedelosk.modularmachines.common.modular.Modular;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlockModularMachine extends ItemBlock {

	public ItemBlockModularMachine(Block p_i45328_1_) {
		super(p_i45328_1_);
	}
	
    @Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState){
        if (!world.setBlockState(pos, newState, 3)) return false;

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == this.block){
			TileEntity tile = world.getTileEntity(pos);
			if (!(tile instanceof TileModular)) {
				world.setBlockToAir(pos);
				return false;
			}
			TileModular machine = (TileModular) tile;
			machine.setModular(new Modular(stack.getTagCompound(), machine));
            this.block.onBlockPlacedBy(world, pos, state, player, stack);
        }

        return true;
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
