package modularmachines.common.modules.storages.modules;

import net.minecraft.util.math.AxisAlignedBB;

import modularmachines.api.modules.Module;

public class ModuleCasing extends Module {

	public static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625F, 0.0625F, 0.0625F, 0.9375F, 0.9375F, 0.9375F);
	
	public ModuleCasing() {
		super();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox() {
		return BOUNDING_BOX;
	}
}
