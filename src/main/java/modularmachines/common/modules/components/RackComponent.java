package modularmachines.common.modules.components;

import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import modularmachines.api.modules.positions.IModulePosition;
import modularmachines.api.modules.positions.RackPosition;

public class RackComponent extends ModuleProviderComponent {
	public RackComponent() {
		super(RackPosition.values());
	}
	
	@Nullable
	@Override
	public IModulePosition getPosition(RayTraceResult hit) {
		EnumFacing facing = hit.sideHit;
		BlockPos blockPos = provider.getContainer().getLocatable().getCoordinates();
		Vec3d vec = hit.hitVec.subtract((double) blockPos.getX(), (double) blockPos.getY(), (double) blockPos.getZ());
		double x = vec.x;
		double y = vec.y;
		double z = vec.z;
		if (y < 0.1875 || y > 0.8125F) {
			return null;
		}
		boolean left = false;
		boolean top = y > 0.5F;
		switch (facing) {
			case NORTH: {
				if (x < 0.1875 || x > 0.8125F) {
					return null;
				}
				left = x > 0.5F;
				break;
			}
			case SOUTH: {
				if (x < 0.1875 || x > 0.8125F) {
					return null;
				}
				left = x < 0.5F;
				break;
			}
			case EAST: {
				if (z < 0.1875 || z > 0.8125F) {
					return null;
				}
				left = z > 0.5F;
				break;
			}
			case WEST: {
				if (z < 0.1875 || z > 0.8125F) {
					return null;
				}
				left = z < 0.5F;
				break;
			}
		}
		return left ? top ? RackPosition.UP_LEFT : RackPosition.DOWN_LEFT : top ? RackPosition.UP_RIGHT : RackPosition.DOWN_RIGHT;
	}
}
