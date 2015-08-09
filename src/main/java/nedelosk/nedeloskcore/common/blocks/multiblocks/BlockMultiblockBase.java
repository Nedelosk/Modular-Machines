package nedelosk.nedeloskcore.common.blocks.multiblocks;

import java.lang.reflect.Constructor;

import nedelosk.nedeloskcore.common.blocks.BlockContainerForest;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.LoaderException;

public class BlockMultiblockBase extends BlockContainerForest {

	public Class<? extends TileMultiblockBase> tile;
	
	public BlockMultiblockBase(Material mat, Class<? extends TileMultiblockBase> tile) {
		super(mat);
		this.tile = tile;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileMultiblockBase)
		{
			((TileMultiblockBase) tile).onBlockActivated(world, x, y, z, player, side);
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

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
        try
        {
        	TileMultiblockBase i = null;
            if (tile != null)
            {
                Constructor<? extends TileMultiblockBase> itemCtor = tile.getConstructor();
                i = itemCtor.newInstance();
            }
            if (i != null)
            {
                return i;
            }
            return null;
        }
        catch (Exception e)
        {
            FMLLog.log(Level.ERROR, e, "Caught an exception during TileEntity registration in Forestday:BlockMultiblockBase " + tile.getName());
            throw new LoaderException(e);
        }
	}
	
}
