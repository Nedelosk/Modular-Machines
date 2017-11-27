package modularmachines.api.modules.components.process;

import net.minecraft.util.ITickable;

import modularmachines.api.modules.components.IActivatableComponent;
import modularmachines.api.modules.components.IModuleComponent;

public interface IProcessComponent extends IModuleComponent, IActivatableComponent, ITickable {
	boolean canWork();
	
	boolean canProgress();
}
