package de.nedelosk.forestmods.common.modules;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import de.nedelosk.forestmods.api.material.IMaterial;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.producers.IModule;
import de.nedelosk.forestmods.api.utils.IModuleHandler;
import de.nedelosk.forestmods.api.utils.IModuleRegistry;
import de.nedelosk.forestmods.api.utils.ModuleEvents;
import de.nedelosk.forestmods.api.utils.ModuleStack;
import de.nedelosk.forestmods.api.utils.ModuleUID;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ModuleRegistry implements IModuleRegistry {

	// Module Registry
	private static ModuleMaps moduleMaps = new ModuleMaps();
	private static ArrayList<IModuleHandler> moduleHandlers = Lists.newArrayList();

	@Override
	public <M extends IModule> M registerModule(IMaterial material, String category, M module) {
		if (module == null) {
			return null;
		}
		return moduleMaps.registerModule(material, category, module);
	}

	@Override
	public IModule getModule(IMaterial material, ModuleUID UID) {
		if (material == null || UID == null) {
			return null;
		}
		return moduleMaps.getModule(material, UID);
	}

	// Items for modules
	@Override
	public void registerItemForModule(ItemStack itemStack, ModuleStack moduleStack) {
		registerItemForModule(itemStack, moduleStack, false);
	}

	@Override
	public void registerItemForModule(ItemStack itemStack, ModuleStack moduleStack, boolean ignorNBT) {
		ModuleHandler moduleHandler = new ModuleHandler(itemStack, moduleStack, ignorNBT);
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
		for ( IModuleHandler handler : moduleHandlers ) {
			if (stack.getItem() == handler.getStack().getItem() && stack.getItemDamage() == handler.getStack().getItemDamage()) {
				if (handler.ignorNBT() || ItemStack.areItemStackTagsEqual(stack, handler.getStack())) {
					return handler.getModuleStack();
				}
			}
		}
		return null;
	}

	// Modular Registry
	private HashMap<String, Class<? extends IModular>> modularClasses = Maps.newHashMap();

	@Override
	public void registerModular(String name, Class<? extends IModular> modular) {
		modularClasses.put(name, modular);
		MinecraftForge.EVENT_BUS.post(new ModuleEvents.ModularRegisterEvent(modular, name));
	}

	@Override
	public Class<? extends IModular> getModular(String name) {
		return modularClasses.get(name);
	}

	@Override
	public HashMap<String, Class<? extends IModular>> getModular() {
		return modularClasses;
	}
}
