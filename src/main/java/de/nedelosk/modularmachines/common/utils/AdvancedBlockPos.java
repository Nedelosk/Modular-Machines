package de.nedelosk.modularmachines.common.utils;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3i;

public class AdvancedBlockPos extends BlockPos {

	public int x, y, z;

	public AdvancedBlockPos(BlockPos pos) {
		super(0, 0, 0);
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
	}
	
	public AdvancedBlockPos(int x, int y, int z) {
		super(0, 0, 0);
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public int getX() {
		return x;
	}
	
	@Override
	public int getY() {
		return y;
	}
	
	@Override
	public int getZ() {
		return z;
	}

	public int getChunkX() {
		return x >> 4;
	}

	public int getChunkZ() {
		return z >> 4;
	}

	public long getChunkXZHash() {
		return ChunkPos.chunkXZ2Int(x >> 4, z >> 4);
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		} else if (other instanceof AdvancedBlockPos) {
			AdvancedBlockPos otherTriplet = (AdvancedBlockPos) other;
			return this.x == otherTriplet.x && this.y == otherTriplet.y && this.z == otherTriplet.z;
		} else {
			return false;
		}
	}

	public void translate(EnumFacing dir) {
		this.x += dir.getFrontOffsetX();
		this.y += dir.getFrontOffsetY();
		this.z += dir.getFrontOffsetZ();
	}

	public boolean equals(int x, int y, int z) {
		return this.x == x && this.y == y && this.z == z;
	}

	// Suggested implementation from NetBeans 7.1
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + this.x;
		hash = 71 * hash + this.y;
		hash = 71 * hash + this.z;
		return hash;
	}
	
    @Override
	public BlockPos toImmutable()
    {
        return new BlockPos(this);
    }

	public AdvancedBlockPos copy() {
		return new AdvancedBlockPos(this);
	}

	public void copy(AdvancedBlockPos other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}

	public AdvancedBlockPos[] getNeighbors() {
		return new AdvancedBlockPos[] { new AdvancedBlockPos(x + 1, y, z), new AdvancedBlockPos(x - 1, y, z), new AdvancedBlockPos(x, y + 1, z), new AdvancedBlockPos(x, y - 1, z),
				new AdvancedBlockPos(x, y, z + 1), new AdvancedBlockPos(x, y, z - 1) };
	}

	///// IComparable
	@Override
	public int compareTo(Vec3i pos) {
		if (pos instanceof AdvancedBlockPos) {
			AdvancedBlockPos other = (AdvancedBlockPos) pos;
			if (this.x < other.x) {
				return -1;
			} else if (this.x > other.x) {
				return 1;
			} else if (this.y < other.y) {
				return -1;
			} else if (this.y > other.y) {
				return 1;
			} else if (this.z < other.z) {
				return -1;
			} else if (this.z > other.z) {
				return 1;
			} else {
				return 0;
			}
		}
		return 0;
	}

	public EnumFacing getDirectionFromSourceCoords(int x, int y, int z) {
		if (this.x < x) {
			return EnumFacing.WEST;
		} else if (this.x > x) {
			return EnumFacing.EAST;
		} else if (this.y < y) {
			return EnumFacing.DOWN;
		} else if (this.y > y) {
			return EnumFacing.UP;
		} else if (this.z < z) {
			return EnumFacing.SOUTH;
		} else if (this.z > z) {
			return EnumFacing.NORTH;
		} else {
			return null;
		}
	}

	public EnumFacing getOppositeDirectionFromSourceCoords(int x, int y, int z) {
		if (this.x < x) {
			return EnumFacing.EAST;
		} else if (this.x > x) {
			return EnumFacing.WEST;
		} else if (this.y < y) {
			return EnumFacing.UP;
		} else if (this.y > y) {
			return EnumFacing.DOWN;
		} else if (this.z < z) {
			return EnumFacing.NORTH;
		} else if (this.z > z) {
			return EnumFacing.SOUTH;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return String.format("(%d, %d, %d)", this.x, this.y, this.z);
	}

	public int compareTo(int xCoord, int yCoord, int zCoord) {
		if (this.x < xCoord) {
			return -1;
		} else if (this.x > xCoord) {
			return 1;
		} else if (this.y < yCoord) {
			return -1;
		} else if (this.y > yCoord) {
			return 1;
		} else if (this.z < zCoord) {
			return -1;
		} else if (this.z > zCoord) {
			return 1;
		} else {
			return 0;
		}
	}

	public int getDistSq(AdvancedBlockPos other) {
		int xDiff = x - other.x;
		int yDiff = y - other.y;
		int zDiff = z - other.z;
		return xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
	}
}
