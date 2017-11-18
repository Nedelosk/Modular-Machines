package modularmachines.common.modules.storages.modules;

import javax.annotation.Nullable;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;

import modularmachines.api.modules.IModulePosition;
import modularmachines.common.modules.storages.ModuleContainer;

public class ModuleModuleRack extends ModuleContainer {
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.125F, 0.0625F, 0.5625F, 0.875F, 0.875F, 1F);
	
	public ModuleModuleRack() {
		super();
	}
	
	@Override
	protected AxisAlignedBB getBoundingBox() {
		return BOUNDING_BOX;
	}
	
	@Nullable
	@Override
	public IModulePosition getPosition(RayTraceResult hit) {
		return null;
	}
}
