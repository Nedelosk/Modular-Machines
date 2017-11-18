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
				this.x = EnumFacing.SOUTH;
				this.y = EnumFacing.UP;
				this.z = EnumFacing.EAST;
				break;
			case WEST:
				this.x = EnumFacing.NORTH;
				this.y = EnumFacing.UP;
				this.z = EnumFacing.WEST;
				break;
			case NORTH:
				this.x = EnumFacing.WEST;
				this.y = EnumFacing.UP;
				this.z = EnumFacing.NORTH;
				break;
			case SOUTH:
				this.x = EnumFacing.EAST;
				this.y = EnumFacing.UP;
				this.z = EnumFacing.SOUTH;
				break;
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
		double aX = minX * this.x.getFrontOffsetX() + minY * this.y.getFrontOffsetX() + minZ * this.z.getFrontOffsetX();
		double aY = minX * this.x.getFrontOffsetY() + minY * this.y.getFrontOffsetY() + minZ * this.z.getFrontOffsetY();
		double aZ = minX * this.x.getFrontOffsetZ() + minY * this.y.getFrontOffsetZ() + minZ * this.z.getFrontOffsetZ();
		
		double bX = maxX * this.x.getFrontOffsetX() + maxY * this.y.getFrontOffsetX() + maxZ * this.z.getFrontOffsetX();
		double bY = maxX * this.x.getFrontOffsetY() + maxY * this.y.getFrontOffsetY() + maxZ * this.z.getFrontOffsetY();
		double bZ = maxX * this.x.getFrontOffsetZ() + maxY * this.y.getFrontOffsetZ() + maxZ * this.z.getFrontOffsetZ();
		
		if (this.x.getFrontOffsetX() + this.y.getFrontOffsetX() + this.z.getFrontOffsetX() < 0) {
			aX += 1;
			bX += 1;
		}
		
		if (this.x.getFrontOffsetY() + this.y.getFrontOffsetY() + this.z.getFrontOffsetY() < 0) {
			aY += 1;
			bY += 1;
		}
		
		if (this.x.getFrontOffsetZ() + this.y.getFrontOffsetZ() + this.z.getFrontOffsetZ() < 0) {
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
