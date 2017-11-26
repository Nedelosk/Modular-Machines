package modularmachines.api.modules.components;

import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;

import modularmachines.api.EnumIOMode;

public interface IIOComponent extends IModuleComponent {
	
	EnumIOMode getMode(@Nullable EnumFacing facing);
	
	void setMode(@Nullable EnumFacing facing, EnumIOMode mode);
	
	void clearAllModes();
	
	boolean supportsMode(@Nullable EnumFacing facing, EnumIOMode mode);
}
