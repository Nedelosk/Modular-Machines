package modularmachines.common.utils;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

public class BoundingBoxHelper {
	private final EnumFacing x;
	private final EnumFacing y;
	private final EnumFacing z;
	
	public BoundingBoxHelper(EnumFacing facing) {
		switch (facing) {
			case DOWN:
				this.x = EnumFacing.EAST;
				this.y = EnumFacing.NORTH;
				this.z = EnumFacing.DOWN;
				break;
			case UP:
				this.x = EnumFacing.EAST;
				this.y = EnumFacing.SOUTH;
				this.z = EnumFacing.UP;
				break;
			case EAST:
				this.x = EnumFacing.NORTH;
				this.y = EnumFacing.UP;
				this.z = EnumFacing.EAST;
				break;
			case WEST:
				this.x = EnumFacing.SOUTH;
				this.y = EnumFacing.UP;
				this.z = EnumFacing.WEST;
				break;
			case NORTH:
				this.x = EnumFacing.WEST;
				this.y = EnumFacing.UP;
				this.z = EnumFacing.NORTH;
				break;
			case SOUTH:
			default:
				this.x = EnumFacing.EAST;
				this.y = EnumFacing.UP;
				this.z = EnumFacing.SOUTH;
				break;
		}
	}
	
	public AxisAlignedBB rotateBox(AxisAlignedBB axisAlignedBB) {
		return rotateBox(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
	}
	
	public AxisAlignedBB rotateBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		double aX = minX * this.x.getXOffset() + minY * this.y.getXOffset() + minZ * this.z.getXOffset();
		double aY = minX * this.x.getYOffset() + minY * this.y.getYOffset() + minZ * this.z.getYOffset();
		double aZ = minX * this.x.getZOffset() + minY * this.y.getZOffset() + minZ * this.z.getZOffset();
		
		double bX = maxX * this.x.getXOffset() + maxY * this.y.getXOffset() + maxZ * this.z.getXOffset();
		double bY = maxX * this.x.getYOffset() + maxY * this.y.getYOffset() + maxZ * this.z.getYOffset();
		double bZ = maxX * this.x.getZOffset() + maxY * this.y.getZOffset() + maxZ * this.z.getZOffset();
		
		if (this.x.getXOffset() + this.y.getXOffset() + this.z.getXOffset() < 0) {
			aX += 1;
			bX += 1;
		}
		
		if (this.x.getYOffset() + this.y.getYOffset() + this.z.getYOffset() < 0) {
			aY += 1;
			bY += 1;
		}
		
		if (this.x.getZOffset() + this.y.getZOffset() + this.z.getZOffset() < 0) {
			aZ += 1;
			bZ += 1;
		}
		
		minX = Math.min(aX, bX);
		minY = Math.min(aY, bY);
		minZ = Math.min(aZ, bZ);
		maxX = Math.max(aX, bX);
		maxY = Math.max(aY, bY);
		maxZ = Math.max(aZ, bZ);
		
		return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
	}
}
