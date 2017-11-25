package modularmachines.api.modules.components;

import net.minecraft.util.ITickable;

public interface IProcessComponent extends IModuleComponent, IActivatableComponent, ITickable {
	boolean canWork();
	
	boolean canProgress();
}
