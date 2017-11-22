package modularmachines.common.modules.components;

import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;

import modularmachines.api.modules.positions.EnumCasingPositions;
import modularmachines.api.modules.positions.IModulePosition;

public class CasingComponent extends ModuleProviderComponent {
	public CasingComponent() {
		super(EnumCasingPositions.LEFT, EnumCasingPositions.RIGHT, EnumCasingPositions.FRONT, EnumCasingPositions.BACK);
	}
	
	@Nullable
	@Override
	public IModulePosition getPosition(RayTraceResult hit) {
		EnumFacing sideHit = hit.sideHit;
		EnumFacing facing = provider.getContainer().getLocatable().getFacing();
		if (facing == null) {
			facing = EnumFacing.NORTH;
		}
		if (sideHit.getAxis() == EnumFacing.Axis.Y) {
			return null;
		}
		if (facing.rotateY() == sideHit) {
			return EnumCasingPositions.LEFT;
		} else if (sideHit.rotateY() == facing) {
			return EnumCasingPositions.RIGHT;
		} else if (sideHit.rotateY().rotateY() == facing) {
			return EnumCasingPositions.BACK;
		} else if (sideHit == facing) {
			return EnumCasingPositions.FRONT;
		}
		return null;
	}
}
