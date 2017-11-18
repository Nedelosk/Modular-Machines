package modularmachines.common.modules.storages.modules;

import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;

import modularmachines.api.modules.EnumCasingPositions;
import modularmachines.api.modules.IModulePosition;
import modularmachines.common.modules.storages.ModuleContainer;

public class ModuleCasing extends ModuleContainer {
	
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.9375F, 0.9375F);
	
	public ModuleCasing() {
		super(EnumCasingPositions.LEFT, EnumCasingPositions.RIGHT, EnumCasingPositions.FRONT, EnumCasingPositions.BACK);
	}
	
	@Override
	protected AxisAlignedBB getBoundingBox() {
		return BOUNDING_BOX;
	}
	
	@Nullable
	@Override
	public IModulePosition getPosition(RayTraceResult hit) {
		EnumFacing sideHit = hit.sideHit;
		EnumFacing facing = container.getFacing();
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
