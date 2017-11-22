package modularmachines.common.modules.components;

import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import modularmachines.api.modules.positions.EnumRackPositions;
import modularmachines.api.modules.positions.IModulePosition;

public class RackComponent extends ModuleProviderComponent {
	public RackComponent() {
		super(EnumRackPositions.UP, EnumRackPositions.CENTER, EnumRackPositions.DOWN);
	}
	
	@Nullable
	@Override
	public IModulePosition getPosition(RayTraceResult hit) {
		EnumFacing facing = hit.sideHit;
		BlockPos blockPos = provider.getContainer().getLocatable().getCoordinates();
		Vec3d vec = hit.hitVec.subtract((double) blockPos.getX(), (double) blockPos.getY(), (double) blockPos.getZ());
		switch (facing) {
			case NORTH:
			case SOUTH: {
				if (vec.x < 0.1875 || vec.x > 0.8125F) {
					return null;
				}
				break;
			}
			case WEST:
			case EAST: {
				if (vec.z < 0.1875 || vec.z > 0.8125F) {
					return null;
				}
				break;
			}
		}
		if (vec.y < 0.125F || vec.y > 0.8125F) {
			return null;
		}
		if (vec.y > 0.625F) {
			return EnumRackPositions.UP;
		} else if (vec.y > 0.375F) {
			return EnumRackPositions.CENTER;
		}
		return EnumRackPositions.DOWN;
	}
}
