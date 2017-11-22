package modularmachines.common.modules;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.Module;
import modularmachines.api.modules.container.IModuleContainer;
import modularmachines.api.modules.data.IModuleDataContainer;

public interface IModuleHelper {
	
	List<Module> getModulesWithComponents(@Nullable IModuleContainer provider);
	
	void registerContainer(IModuleDataContainer container);
	
	@Nullable
	IModuleDataContainer getContainerFromItem(ItemStack stack);
	
	Collection<IModuleDataContainer> getContainers();
}
