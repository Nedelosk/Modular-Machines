package modularmachines.common.modules.storages.modules;

import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;

import modularmachines.api.modules.EnumModulePositions;
import modularmachines.api.modules.IModulePosition;
import modularmachines.common.modules.storages.ModuleContainer;

public class ModuleCasing extends ModuleContainer {

	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.9375F, 0.9375F);
	
	public ModuleCasing() {
		super(EnumModulePositions.LEFT, EnumModulePositions.RIGHT);
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
		if(facing == null){
			facing = EnumFacing.NORTH;
		}
		if(sideHit.getAxis() == EnumFacing.Axis.Y){
			return null;
		}
		if(facing.rotateY() == sideHit){
			return EnumModulePositions.LEFT;
		} else if(sideHit.rotateY() == facing){
			return EnumModulePositions.RIGHT;
		}
		return null;
	}
}
