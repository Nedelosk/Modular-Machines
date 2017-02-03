package modularmachines.common.utils;

import javax.annotation.Nullable;

import net.minecraft.block.BlockFlowerPot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class TileUtil {

	/**
	 * Returns the tile of the specified class, returns null if it is the wrong type or does not exist.
	 * Avoids creating new tile entities when using a ChunkCache (off the main thread).
	 * see {@link BlockFlowerPot#getActualState(IBlockState, IBlockAccess, BlockPos)}
	 */
	@Nullable
	public static <T extends TileEntity> T getTile(IBlockAccess world, BlockPos pos, Class<T> tileClass) {
		TileEntity tileEntity;

		if (world instanceof ChunkCache) {
			ChunkCache chunkCache = (ChunkCache) world;
			tileEntity = chunkCache.getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
		} else {
			tileEntity = world.getTileEntity(pos);
		}

		if (tileClass.isInstance(tileEntity)) {
			return tileClass.cast(tileEntity);
		} else {
			return null;
		}
	}
	
	public static boolean isUsableByPlayer(EntityPlayer player, TileEntity tile) {
		if(player.isSneaking()){
			return false;
		}
		BlockPos pos = tile.getPos();
		World world = tile.getWorld();

		return !tile.isInvalid() && world.getTileEntity(pos) == tile && player.getDistanceSqToCenter(pos) <= 64.0D;
	}
	
}
