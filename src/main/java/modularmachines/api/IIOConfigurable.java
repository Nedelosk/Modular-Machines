package modularmachines.api;

import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;

public interface IIOConfigurable {
	
	boolean supportsMode(EnumIOMode ioMode, @Nullable EnumFacing facing);
	
	EnumIOMode getMode(@Nullable EnumFacing facing);
}
