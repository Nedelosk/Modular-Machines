package modularmachines.common.modules.components;

import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;

import modularmachines.api.modules.positions.CasingPosition;
import modularmachines.api.modules.positions.IModulePosition;

public class CasingComponent extends ModuleProviderComponent {
	public CasingComponent() {
		super(CasingPosition.LEFT, CasingPosition.RIGHT, CasingPosition.FRONT, CasingPosition.BACK);
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
			return CasingPosition.LEFT;
		} else if (sideHit.rotateY() == facing) {
			return CasingPosition.RIGHT;
		} else if (sideHit.rotateY().rotateY() == facing) {
			return CasingPosition.BACK;
		} else if (sideHit == facing) {
			return CasingPosition.FRONT;
		}
		return null;
	}
}
