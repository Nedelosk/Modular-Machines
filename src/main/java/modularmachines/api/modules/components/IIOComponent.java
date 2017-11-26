package modularmachines.api.modules.components;

import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;

import modularmachines.api.EnumIOMode;
import modularmachines.api.IIOConfigurable;

public interface IIOComponent extends IModuleComponent, IIOConfigurable {
	
	void setMode(@Nullable EnumFacing facing, EnumIOMode mode);
	
	void clearAllModes();
}
