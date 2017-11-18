package modularmachines.api.modules;

import com.google.common.collect.Lists;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;

import modularmachines.api.modules.containers.IModuleDataContainer;

public class ModuleHelper {
	
	/**
	 * @return All modules of an IModular that have a page.
	 */
	public static List<Module> getModulesWithComponents(@Nullable IModuleContainer provider) {
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
	public static IModuleDataContainer getContainerFromItem(ItemStack stack) {
		if (stack == null || stack.isEmpty()) {
			return null;
		}
		for (IModuleDataContainer container : ModuleRegistry.getContainers()) {
			if (container.matches(stack)) {
				return container;
			}
		}
		return null;
	}
	
}
