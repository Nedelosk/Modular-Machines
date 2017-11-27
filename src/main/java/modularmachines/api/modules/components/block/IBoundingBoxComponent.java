package modularmachines.api.modules.components.block;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.components.IModuleComponent;

/**
 * This component can be used to give the module a bounding box.
 * <p>
 * {@link modularmachines.api.modules.components.IModuleComponentFactory#addBoundingBox(IModule, AxisAlignedBB)} can be
 * used to add this component to a module.
 */
public interface IBoundingBoxComponent extends IModuleComponent {
	
	RayTraceResult collisionRayTrace(Vec3d start, Vec3d end);
	
	AxisAlignedBB getCollisionBox();
	
	AxisAlignedBB getBoundingBox();
}
