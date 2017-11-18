package modularmachines.api.modules;

import javax.annotation.Nullable;

import net.minecraft.util.math.RayTraceResult;

import modularmachines.api.modules.containers.IModuleDataContainer;

public interface IModuleProvider {
	
	IModuleHandler getHandler();
	
	@Nullable
	IModulePosition getPosition(RayTraceResult hit);
	
	IModuleContainer getContainer();
	
	default boolean isValidModule(IModulePosition position, IModuleDataContainer dataContainer) {
		return dataContainer.getData().isValidPosition(position);
	}
}
