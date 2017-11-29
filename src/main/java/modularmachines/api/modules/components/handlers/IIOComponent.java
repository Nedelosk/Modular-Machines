package modularmachines.api.modules.components.handlers;

import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;

import modularmachines.api.IIOConfigurable;
import modularmachines.api.IOMode;
import modularmachines.api.modules.components.IModuleComponent;

/**
 * This component can be used to add a IO (Input / Output) configuration to your module.
 */
public interface IIOComponent extends IModuleComponent, IIOConfigurable {
	
	/**
	 * Sets the io mode of the given facing to the given io mode.
	 */
	void setMode(@Nullable EnumFacing facing, IOMode mode);
	
	/**
	 * Sets all io modes of this component to there default mode.
	 */
	void clearAllModes();
}
