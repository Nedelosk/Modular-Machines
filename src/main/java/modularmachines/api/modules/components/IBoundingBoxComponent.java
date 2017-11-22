package modularmachines.api.modules.components;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public interface IBoundingBoxComponent extends IModuleComponent {
	
	RayTraceResult collisionRayTrace(Vec3d start, Vec3d end);
	
	AxisAlignedBB getCollisionBox();
	
	AxisAlignedBB getBoundingBox();
}
