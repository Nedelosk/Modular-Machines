package nedelosk.nedeloskcore.common.blocks.multiblocks;

import nedelosk.nedeloskcore.common.blocks.BlockContainerForest;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockMultiblockBase extends BlockContainerForest {
	
	public BlockMultiblockBase(Material mat) {
		super(mat);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileMultiblockBase)
		{
			TileMultiblockBase multiblock = (TileMultiblockBase) tile;
			if(multiblock.master != null && multiblock.master.isMultiblock && multiblock.master.multiblock != null)
			{
				multiblock.master.multiblock.onBlockActivated(world, x, y, z, player, side);
				if(((TileMultiblockBase) tile).getContainer(player.inventory) != null)
					return true;
			}
		}
		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block p_149749_5_, int p_149749_6_) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileMultiblockBase)
		{
			((TileMultiblockBase)tile).onBlockRemoval();
		}
		super.breakBlock(world, x, y, z, p_149749_5_, p_149749_6_);
	}
	
    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileMultiblockBase)
            ((TileMultiblockBase) tile).onBlockAdded();
    }
}
