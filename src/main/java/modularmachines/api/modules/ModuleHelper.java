package modularmachines.api.modules;

import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.data.IModuleDataContainer;
import modularmachines.common.modules.IModuleHelper;

public enum ModuleHelper implements IModuleHelper {
	INSTANCE;
	
	private final List<IModuleDataContainer> containers = new ArrayList<>();
	
	/**
	 * @return All modules of an IModular that have a page.
	 */
	public List<Module> getModulesWithComponents(@Nullable IModuleContainer provider) {
		if (provider == null) {
			return Collections.emptyList();
		}
		List<Module> validModules = Lists.newArrayList();
		for (Module module : provider.getModules()) {
			if (module != null && !module.getComponents().isEmpty()) {
				validModules.add(module);
			}
		}
		return validModules;
	}
	
	/**
	 * @return The matching module container for the stack.
	 */
	@Nullable
	public IModuleDataContainer getContainerFromItem(ItemStack stack) {
		if (stack.isEmpty()) {
			return null;
		}
		for (IModuleDataContainer container : containers) {
			if (container.matches(stack)) {
				return container;
			}
		}
		return null;
	}
	
	public void registerContainer(IModuleDataContainer container) {
		containers.add(container);
	}
	
	public List<IModuleDataContainer> getContainers() {
		return Collections.unmodifiableList(containers);
	}
	
}
