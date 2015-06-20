package nedelosk.nedeloskcore.utils;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldUtils {

    public static TileEntity getTileEntityOnSide(World world, int x, int y, int z, ForgeDirection side) {
        int sx = getXOnSide(x, side);
        int sy = getYOnSide(y, side);
        int sz = getZOnSide(z, side);
        if (isBlockExists(world, sx, sy, sz) && getBlock(world, sx, sy, sz) != Blocks.air)
            return getBlockTile(world, sx, sy, sz);
        return null;
    }
    
    public static TileEntity getBlockTile(IBlockAccess world, int x, int y, int z) {
        return world.getTileEntity(x, y, z);
    }
    
    public static Block getBlock(IBlockAccess world, int x, int y, int z) {
        return world.getBlock(x, y, z);
    }
    
    public static boolean areCoordinatesOnSide(int x, int y, int z, ForgeDirection side, int xCoord, int yCoord, int zCoord) {
        return x + side.offsetX == xCoord && y + side.offsetY == yCoord && z + side.offsetZ == zCoord;
    }

    public static boolean isBlockExists(World world, int x, int y, int z) {
        return world.blockExists(x, y, z);
    }
	
    public static int getXOnSide(int x, ForgeDirection side) {
        return x + side.offsetX;
    }

    public static int getYOnSide(int y, ForgeDirection side) {
        return y + side.offsetY;
    }

    public static int getZOnSide(int z, ForgeDirection side) {
        return z + side.offsetZ;
    }
	
}
