package modularmachines.api.modules;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import modularmachines.api.modules.containers.IModuleContainer;
import net.minecraft.item.ItemStack;

public class ModuleHelper {
	
	/**
	 * @return All modules of an IModular that have a page.
	 */
	public static List<Module> getPageModules(@Nullable IModuleLogic logic) {
		if (logic == null) {
			return Collections.emptyList();
		}
		List<Module> validModules = Lists.newArrayList();
		for (Module module : logic.getModules()) {
			if (module != null && !module.getPages().isEmpty()) {
				validModules.add(module);
			}
		}
		return validModules;
	}

	/**
	 * @return The matching module container for the stack.
	 */
	public static IModuleContainer getContainerFromItem(ItemStack stack) {
		if (stack == null) {
			return null;
		}
		for (IModuleContainer container : ModuleManager.getContainers()) {
			if (container.matches(stack)) {
				return container;
			}
		}
		return null;
	}
	
}
