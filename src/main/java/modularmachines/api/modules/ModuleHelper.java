package modularmachines.api.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import modularmachines.api.modules.containers.IModuleContainer;
import modularmachines.api.modules.logic.IModuleLogic;
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
	
	public static List<Module> getModules(IModuleLogic logic, Class<? extends Module> moduleClass){
		List<Module> modules = new ArrayList<>();
		for(Module module : logic.getModules()){
			if(moduleClass.isAssignableFrom(module.getClass())){
				modules.add(module);
			}
		}
		return modules;
	}
	
	public static Module getModule(IModuleLogic logic, Class<? extends Module> moduleClass){
		for(Module module : logic.getModules()){
			if(moduleClass.isAssignableFrom(module.getClass())){
				return module;
			}
		}
		return null;
	}

	/**
	 * @return The matching module container for the stack.
	 */
	@Nullable
	public static IModuleContainer getContainerFromItem(ItemStack stack) {
		if (stack == null || stack.isEmpty()) {
			return null;
		}
		for (IModuleContainer container : ModuleRegistry.getContainers()) {
			if (container.matches(stack)) {
				return container;
			}
		}
		return null;
	}
	
	@Nullable
	public static Module createModule(IModuleStorage storage, ItemStack stack){
		IModuleContainer container = getContainerFromItem(stack);
		if(container != null && container.getData() != null){
			return container.getData().createModule(storage, container, stack);
		}
		return null;
	}
	
}
