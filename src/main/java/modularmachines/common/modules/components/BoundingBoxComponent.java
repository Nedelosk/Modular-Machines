package modularmachines.common.modules.components;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.IModuleHandler;
import modularmachines.api.modules.IModuleProvider;
import modularmachines.api.modules.components.IBoundingBoxComponent;
import modularmachines.common.modules.ModuleComponent;
import modularmachines.common.utils.BoundingBoxHelper;

public class BoundingBoxComponent extends ModuleComponent implements IBoundingBoxComponent {
	private final AxisAlignedBB boundingBox;
	
	public BoundingBoxComponent(AxisAlignedBB boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	@Nullable
	public RayTraceResult collisionRayTrace(Vec3d start, Vec3d end) {
		RayTraceResult traceResult = rayTrace(start, end, provider, getCollisionBox());
		if (traceResult == null) {
			return null;
		}
		double distance = traceResult.hitVec.squareDistanceTo(start);
		if (this instanceof IModuleProvider) {
			IModuleHandler handler = ((IModuleProvider) this).getHandler();
			Optional<RayTraceResult> result = handler.getModules().stream().map(m -> m.getInterfaces(IBoundingBoxComponent.class)).flatMap(Collection::stream).map(c -> c.collisionRayTrace(start, end)).filter(Objects::nonNull).min(Comparator.comparingDouble(hit -> hit.hitVec.squareDistanceTo(start)));
			if (result.isPresent()) {
				RayTraceResult rayTraceResult = result.get();
				double secondD = rayTraceResult.hitVec.squareDistanceTo(start);
				if (secondD <= distance) {
					return rayTraceResult;
				}
			}
		}
		return traceResult;
	}
	
	@Nullable
	protected RayTraceResult rayTrace(Vec3d start, Vec3d end, IModule module, @Nullable AxisAlignedBB boundingBox) {
		if (boundingBox == null) {
			return null;
		}
		RayTraceResult rayTrace = boundingBox.calculateIntercept(start, end);
		if (rayTrace == null) {
			return null;
		}
		RayTraceResult result = new RayTraceResult(rayTrace.hitVec, rayTrace.sideHit);
		result.subHit = module.getIndex();
		result.hitInfo = rayTrace;
		return result;
	}
	
	public final AxisAlignedBB getCollisionBox() {
		AxisAlignedBB boundingBox = getBoundingBox();
		IModuleProvider provider = this.provider.getParent().getProvider();
		if (boundingBox != null && provider instanceof IModule) {
			BoundingBoxHelper helper = new BoundingBoxHelper(this.provider.getFacing());
			return helper.rotateBox(boundingBox).offset(this.provider.getPosition().getOffset());
		}
		return boundingBox;
	}
	
	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}
}
