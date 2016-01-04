package nedelosk.modularmachines.api.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import akka.japi.Pair;
import cpw.mods.fml.common.eventhandler.Event;
import nedelosk.modularmachines.api.modular.IModular;
import nedelosk.modularmachines.api.modular.type.Types.Type;
import nedelosk.modularmachines.api.modules.IModule;
import nedelosk.modularmachines.api.producers.IProducer;
import nedelosk.modularmachines.api.producers.factory.IProducerFactory;
import nedelosk.modularmachines.api.producers.farm.IFarm;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ModuleRegistry {

	// Module Registry
	private static HashMap<String, IModule> modules = Maps.newHashMap();
	private static HashMap<String, List<Pair<Type, String>>> typeModifiers = Maps.newHashMap();

	public static void registerModule(IModule module) {
		modules.put(module.getRegistryName(), module);
		MinecraftForge.EVENT_BUS.post(new ModuleRegisterEvent(module));
	}

	public static IModule getModule(String name) {
		return modules.get(name);
	}

	public static HashMap<String, IModule> getModules() {
		return modules;
	}

	public static String getTypeModifier(ModuleStack stack) {
		return getTypeAndModifier(stack).second();
	}

	public static Pair<Type, String> getTypeAndModifier(ModuleStack stack) {
		for (Pair<Type, String> modifiers : typeModifiers.get(stack.getModule().getRegistryName())) {
			if (modifiers.first().equals(stack.getType())) {
				return modifiers;
			}
		}
		return null;
	}

	public static void addTypeModifier(IModule module, Type tier, String modifier) {
		addTypeModifier(module.getRegistryName(), tier, modifier);
	}

	public static void addTypeModifier(String module, Type tier, String modifier) {
		if (typeModifiers.get(module) == null) {
			List list = new ArrayList<>();
			list.add(new Pair(tier, modifier));
			typeModifiers.put(module, list);
		} else {
			for (Pair<Type, String> modifiers : typeModifiers.get(module)) {
				if (modifiers.first().equals(tier)) {
					return;
				}
			}
			typeModifiers.get(module).add(new Pair(tier, modifier));
		}
	}

	// Producer Registry
	private static ArrayList<ModuleStack> producers = Lists.newArrayList();

	public static ArrayList<ModuleStack> getProducers() {
		return producers;
	}

	public static void registerProducer(ModuleStack stack) {
		if (!producers.contains(stack)) {
			producers.add(stack);
			MinecraftForge.EVENT_BUS.post(new ProducerRegisterEvent(stack));
		}
		if (stack.getProducer() != null) {
			if (getProducerClass(stack.getProducer().getName(stack)) == null)
				registerProducerClass(stack.getProducer().getClass(), stack.getProducer().getName(stack));
		}
	}

	public static void registerProducer(ItemStack item, IModule module, IProducer producer, Type type, boolean hasNbt) {
		registerProducer(new ModuleStack(item, module, producer, type, hasNbt));
	}

	public static void registerProducer(ItemStack item, IModule module, IProducer producer, Type type) {
		registerProducer(item, module, producer, type, false);
	}

	public static void registerProducer(ItemStack item, IModule module, Type type, boolean hasNbt) {
		registerProducer(item, module, null, type, hasNbt);
	}

	public static void registerProducer(ItemStack item, IModule module, Type type) {
		registerProducer(item, module, type, false);
	}

	public static ModuleStack getProducer(ItemStack stack) {
		if (stack == null)
			return null;
		for (ModuleStack item : producers) {
			if (stack.getItem() == item.getItem().getItem() && stack.getItemDamage() == item.getItem().getItemDamage())
				if (ItemStack.areItemStackTagsEqual(stack, item.getItem()) || !item.hasNbt())
					return item;
		}
		return null;
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

	// Module Farm Registry
	private static LinkedHashMap<String, IFarm> farmRegistry = new LinkedHashMap<String, IFarm>(64);

	public static void registerFarm(IFarm farm) {
		farmRegistry.put(farm.getName(), farm);
		MinecraftForge.EVENT_BUS.post(new ModuleFarmRegisterEvent(farm));
	}

	public static IFarm getFarm(String farm) {
		return farmRegistry.get(farm);
	}

	public static LinkedHashMap<String, IFarm> getFarmRegistry() {
		return farmRegistry;
	}

	// Producer Factory
	public static IProducerFactory producerFactory;

	// Module Producer
	private static HashMap<String, Class<? extends IProducer>> producerClasses = Maps.newHashMap();

	public static Class<? extends IProducer> getProducerClass(String name) {
		for (Map.Entry<String, Class<? extends IProducer>> entry : producerClasses.entrySet()) {
			if (entry.getKey().equals(name))
				return entry.getValue();
		}
		return null;
	}

	public static void registerProducerClass(Class<? extends IProducer> producer, String name) {
		producerClasses.put(name, producer);
		MinecraftForge.EVENT_BUS.post(new ProducerClassRegisterEvent(producer));
	}

	public static class ModuleRegisterEvent extends Event {
		public final IModule module;

		public ModuleRegisterEvent(IModule module) {
			this.module = module;
		}
	}

	public static class ProducerRegisterEvent extends Event {
		public final ModuleStack module;

		public ProducerRegisterEvent(ModuleStack module) {
			this.module = module;
		}
	}

	public static class ModuleFarmRegisterEvent extends Event {
		public final IFarm farm;

		public ModuleFarmRegisterEvent(IFarm farm) {
			this.farm = farm;
		}
	}

	public static class ProducerClassRegisterEvent extends Event {
		public final Class<? extends IProducer> producer;

		public ProducerClassRegisterEvent(Class<? extends IProducer> producer) {
			this.producer = producer;
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

}
