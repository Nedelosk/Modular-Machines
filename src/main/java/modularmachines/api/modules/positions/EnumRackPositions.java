package modularmachines.api.modules.positions;

import net.minecraft.util.math.Vec3d;

public enum EnumRackPositions implements IModulePosition {
	UP(0.0F),
	CENTER(-0.25F),
	DOWN(-0.5F);
	
	private float yOffset;
	
	EnumRackPositions(float yOffset) {
		this.yOffset = yOffset;
	}
	
	@Override
	public Vec3d getOffset() {
		return new Vec3d(0.0F, yOffset, 0.0F);
	}
}
