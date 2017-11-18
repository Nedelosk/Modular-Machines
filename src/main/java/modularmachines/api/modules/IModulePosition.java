package modularmachines.api.modules;

import net.minecraft.util.EnumFacing;

/**
 * Describes a possible position of a module in a {@link IModuleHandler}.
 */
public interface IModulePosition {
	
	default float getRotationAngle(){
		return 0.0F;
	}
	
	default EnumFacing getFacing(){
		return EnumFacing.fromAngle(Math.toDegrees(getRotationAngle()));
	}
}
