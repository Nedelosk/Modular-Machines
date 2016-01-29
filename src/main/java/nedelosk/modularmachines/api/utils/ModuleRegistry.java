package nedelosk.modularmachines.api.utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.eventhandler.Event;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.material.Materials.Material;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.modules.IModuleCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class ModuleRegistry {

	// Module Registry
	private static ModuleNameRegistry moduleRegistry = new ModuleNameRegistry();
	private static HashMap<String, IModuleCategory> categorys = Maps.newHashMap();
	private static ArrayList<ModuleItem> moduleItems = Lists.newArrayList();

	public static IModuleCategory registerCategory(IModuleCategory category) {
		return categorys.put(category.getCategoryUID(), category);
	}

	public static IModuleCategory getCategory(String categoryUID) {
		return categorys.get(categoryUID);
	}

	public static IModule registerModule(IModule module, String name) {
		return moduleRegistry.register(module, name);
	}

	public static IModule registerModule(IModule module) {
		return registerModule(module, module.getCategoryUID() + "." + module.getModuleUID());
	}

	public static IModule getModule(ResourceLocation registry) {
		return moduleRegistry.get(registry);
	}

	public static ModuleNameRegistry getModuleRegistry() {
		return moduleRegistry;
	}

	public static void addModuleToItem(ItemStack stack, IModule module, Material material, boolean ignorNBT) {
		addModuleToItem(new ModuleStack(stack, module, material), ignorNBT);
	}

	public static void addModuleToItem(ItemStack stack, IModule module, Material material) {
		addModuleToItem(stack, module, material, false);
	}

	public static void addModuleToItem(ModuleStack moduleStack, boolean ignorNBT) {
		ModuleItem moduleItem = new ModuleItem(moduleStack.getItemStack(), moduleStack, moduleStack.getMaterial(), ignorNBT);
		if (moduleStack.getModule().getRegistry() == null) {
			registerModule(moduleStack.getModule());
		}
		if (!moduleItems.equals(moduleItem)) {
			moduleItems.add(moduleItem);
			MinecraftForge.EVENT_BUS.post(new ModuleItemRegisterEvent(moduleItem));
		}
	}

	public static void addProducerToItem(ModuleStack moduleStack) {
		addModuleToItem(moduleStack, false);
	}

	public static ModuleItem getModuleFromItem(ItemStack stack) {
		if (stack == null) {
			return null;
		}
		for ( ModuleItem item : moduleItems ) {
			if (stack.getItem() == item.stack.getItem() && stack.getItemDamage() == item.stack.getItemDamage()) {
				if (item.ignorNBT || ItemStack.areItemStackTagsEqual(stack, item.stack)) {
					return item;
				}
			}
		}
		return null;
	}

	public static ArrayList<ModuleItem> getModuleItems() {
		return moduleItems;
	}

	// Modular Registry
	private static HashMap<String, Class<? extends IModular>> modularClasses = Maps.newHashMap();

	public static void registerModular(Class<? extends IModular> modular, String name) {
		modularClasses.put(name, modular);
		MinecraftForge.EVENT_BUS.post(new ModularRegisterEvent(modular, name));
	}

	public static HashMap<String, Class<? extends IModular>> getModular() {
		return modularClasses;
	}

	public static class ModuleRegisterEvent extends Event {

		public final IModule module;

		public ModuleRegisterEvent(IModule module) {
			this.module = module;
		}
	}

	public static class ModuleItemRegisterEvent extends Event {

		public final ModuleItem item;

		public ModuleItemRegisterEvent(ModuleItem item) {
			this.item = item;
		}
	}

	public static class ModularRegisterEvent extends Event {

		public final Class<? extends IModular> modular;
		public final String name;

		public ModularRegisterEvent(Class<? extends IModular> modular, String name) {
			this.modular = modular;
			this.name = name;
		}
	}

	public static final class ModuleItem {

		public final ItemStack stack;
		public final ModuleStack moduleStack;
		public final boolean ignorNBT;
		public final Material material;

		public ModuleItem(ItemStack stack, ModuleStack moduleStack, Material material, boolean ignorNBT) {
			this.stack = stack;
			this.moduleStack = moduleStack;
			this.ignorNBT = ignorNBT;
			this.material = material;
		}

		public ModuleItem(ItemStack stack, ModuleStack moduleStack, Material material) {
			this.stack = stack;
			this.moduleStack = moduleStack;
			this.ignorNBT = false;
			this.material = material;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof ModuleItem)) {
				return false;
			}
			ModuleItem i = (ModuleItem) obj;
			if (stack != null && i.stack != null && i.stack.getItem() != null && stack.getItem() != null && stack.getItemDamage() == i.stack.getItemDamage()
					&& (ignorNBT && i.ignorNBT || stack.getTagCompound() == null && i.stack.getTagCompound() == null
							|| stack.getTagCompound() != null && i.stack.getTagCompound() != null && stack.getTagCompound().equals(i.stack.getTagCompound()))) {
				if (material.equals(i.material)) {
					if (moduleStack.equals(i.moduleStack)) {
						return true;
					}
				}
			}
			return false;
		}
	}
}
