package modularmachines.common.modules.components.block;

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
import modularmachines.api.modules.components.block.IBoundingBoxComponent;
import modularmachines.api.modules.events.Events;
import modularmachines.common.modules.components.ModuleComponent;
import modularmachines.common.utils.BoundingBoxHelper;

public class BoundingBoxComponent extends ModuleComponent implements IBoundingBoxComponent {
	private final AxisAlignedBB boundingBox;
	@Nullable
	private AxisAlignedBB collisionBox = null;
	
	public BoundingBoxComponent(AxisAlignedBB boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	@Override
	public void onCreateModule() {
		provider.getContainer().registerListener(Events.FacingChangeEvent.class, e -> collisionBox = null);
	}
	
	@Nullable
	public RayTraceResult collisionRayTrace(Vec3d start, Vec3d end) {
		RayTraceResult trace = rayTrace(start, end, provider, getCollisionBox());
		if (trace == null) {
			return null;
		}
		double distance = trace.hitVec.squareDistanceTo(start);
		IModuleProvider moduleProvider = provider.getComponent(IModuleProvider.class);
		if (moduleProvider != null) {
			IModuleHandler handler = moduleProvider.getHandler();
			Optional<RayTraceResult> result = handler.getModules()
					.stream()
					.map(m -> m.getComponents(IBoundingBoxComponent.class))
					.flatMap(Collection::stream)
					.map(c -> c.collisionRayTrace(start, end))
					.filter(Objects::nonNull)
					.min(Comparator.comparingDouble(hit -> hit.hitVec.squareDistanceTo(start)));
			if (result.isPresent()) {
				RayTraceResult traceResult = result.get();
				double secondD = traceResult.hitVec.squareDistanceTo(start);
				if (secondD <= distance) {
					return traceResult;
				}
			}
		}
		return trace;
	}
	
	@Nullable
	private RayTraceResult rayTrace(Vec3d start, Vec3d end, IModule module, AxisAlignedBB boundingBox) {
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
		if (collisionBox == null) {
			AxisAlignedBB boundingBox = getBoundingBox();
			BoundingBoxHelper helper = new BoundingBoxHelper(provider.getFacing());
			collisionBox = helper.rotateBox(boundingBox).offset(this.provider.getPosition().getOffset());
		}
		return collisionBox;
	}
	
	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}
}
