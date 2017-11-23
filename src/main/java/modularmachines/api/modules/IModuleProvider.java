package modularmachines.api.modules;

import javax.annotation.Nullable;

import net.minecraft.util.math.RayTraceResult;

import modularmachines.api.modules.components.IModuleComponent;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.data.IModuleDataContainer;
import modularmachines.api.modules.positions.IModulePosition;

/**
 * A interface that can be implemented by {@link IModuleComponent}s and {@link IModuleContainer}s to provide a
 * {@link IModuleHandler} and module positions.
 */
public interface IModuleProvider {
	
	IModuleHandler getHandler();
	
	@Nullable
	IModulePosition getPosition(RayTraceResult hit);
	
	IModuleContainer getContainer();
	
	default boolean isValidModule(IModulePosition position, IModuleDataContainer dataContainer) {
		return dataContainer.getData().isValidPosition(position);
	}
}
