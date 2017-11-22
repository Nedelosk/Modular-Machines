package modularmachines.common.modules;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.IModule;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.data.IModuleDataContainer;

public interface IModuleHelper {
	
	List<IModule> getModulesWithComponents(@Nullable IModuleContainer provider);
	
	void registerContainer(IModuleDataContainer container);
	
	@Nullable
	IModuleDataContainer getContainerFromItem(ItemStack stack);
	
	Collection<IModuleDataContainer> getContainers();
}
