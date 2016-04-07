package de.nedelosk.forestmods.common.modules.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.nedelosk.forestmods.api.material.IMaterial;
import de.nedelosk.forestmods.api.modules.IModule;
import de.nedelosk.forestmods.api.utils.IModuleHandler;
import de.nedelosk.forestmods.api.utils.IModuleRegistry;
import de.nedelosk.forestmods.api.utils.ModuleEvents;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ModuleRegistry implements IModuleRegistry {

	// Module Registry
	private Map<IMaterial, Map<String, List<IModule>>> modules = Maps.newHashMap();
	private static ArrayList<IModuleHandler> moduleHandlers = Lists.newArrayList();

	@Override
	public <M extends IModule> M registerModule(IMaterial material, String category, M module) {
		if (material == null || category == null || module == null) {
			return null;
		}
		if (!modules.containsKey(material)) {
			modules.put(material, Maps.newHashMap());
		}
		if (!modules.get(material).containsKey(category)) {
			modules.get(material).put(category, Lists.newArrayList());
		}
		if (modules.get(material).get(category).contains(module)) {
			return module;
		}
		if (modules.get(material).get(category).add(module)) {
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleRegisterEvent(module));
			return module;
		}
		return null;
	}

	@Override
	public IModule getModule(IMaterial material, ModuleUID UID) {
		if (material == null || UID == null) {
			return null;
		}
		if (modules.containsKey(material)) {
			if (modules.get(material).containsKey(UID.getCategoryUID())) {
				for(IModule module : modules.get(material).get(UID.getCategoryUID())) {
					if (module.getName() != null && !module.getName().isEmpty() && module.getName().equals(UID.getModuleUID())) {
						return module;
					}
				}
			}
		}
		return null;
	}

	// Items for modules
	@Override
	public void registerItemForModule(ItemStack itemStack, ModuleStack moduleStack) {
		registerItemForModule(itemStack, moduleStack, false);
	}

	@Override
	public void registerItemForModule(ItemStack itemStack, ModuleStack moduleStack, boolean ignorNBT) {
		ModuleContainer moduleHandler = new ModuleContainer(itemStack, moduleStack, ignorNBT);
		if (!moduleHandlers.equals(moduleHandler)) {
			MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModuleItemRegisterEvent(moduleHandler));
			moduleHandlers.add(moduleHandler);
		}
	}

	@Override
	public ModuleStack getModuleFromItem(ItemStack stack) {
		if (stack == null) {
			return null;
		}
		for(IModuleHandler handler : moduleHandlers) {
			if (stack.getItem() == handler.getStack().getItem() && stack.getItemDamage() == handler.getStack().getItemDamage()) {
				if (handler.ignorNBT() || ItemStack.areItemStackTagsEqual(stack, handler.getStack())) {
					return handler.getModuleStack();
				}
			}
		}
		return null;
	}
}
