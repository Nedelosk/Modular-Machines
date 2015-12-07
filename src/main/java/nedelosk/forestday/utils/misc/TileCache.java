package nedelosk.forestday.utils.misc;

import nedelosk.forestday.utils.WorldUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public final class TileCache {
	private final TileEntity[] cache = new TileEntity[6];
	private final TileEntity source;

	public TileCache(TileEntity tile) {
		this.source = tile;
	}

	public TileEntity searchSide(ForgeDirection side) {
		return WorldUtils.getTileEntityOnSide(source.getWorldObj(), source.xCoord, source.yCoord, source.zCoord, side);
	}

	public void refresh() {
		for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
			getTileOnSide(side);
		}
	}

	public void setTile(int side, TileEntity tile) {
		if (tile == null)
			return;
		if (cache[side] != tile) {
			cache[side] = tile;
		}
	}

	public TileEntity getTileOnSide(ForgeDirection side) {
		int s = side.ordinal();
		if (cache[s] != null)
			if (cache[s].isInvalid() || !WorldUtils.areCoordinatesOnSide(source.xCoord, source.yCoord, source.zCoord,
					side, cache[s].xCoord, cache[s].yCoord, cache[s].zCoord))
				setTile(s, null);
			else
				return cache[s];
		setTile(s, searchSide(side));

		return cache[s];
	}

}
