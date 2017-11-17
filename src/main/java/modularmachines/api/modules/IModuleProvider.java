package modularmachines.api.modules;

import javax.annotation.Nullable;

import net.minecraft.util.math.RayTraceResult;

public interface IModuleProvider {
	
	IModuleHandler getHandler();
	
	@Nullable
	IModulePosition getPosition(RayTraceResult hit);
	
	IModuleContainer getContainer();
}
