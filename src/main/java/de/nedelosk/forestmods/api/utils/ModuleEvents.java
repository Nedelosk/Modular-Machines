package de.nedelosk.forestmods.api.utils;

import cpw.mods.fml.common.eventhandler.Event;
import de.nedelosk.forestmods.api.modular.IModular;
import de.nedelosk.forestmods.api.producers.IModule;

public class ModuleEvents {

	public static class ModuleRegisterEvent extends Event {

		private final IModule module;

		public ModuleRegisterEvent(IModule module) {
			this.module = module;
		}

		public IModule getModule() {
			return module;
		}
	}

	public static class ModuleItemRegisterEvent extends Event {

		private final IModuleHandler item;

		public ModuleItemRegisterEvent(IModuleHandler item) {
			this.item = item;
		}
	}

	public static class ModularRegisterEvent extends Event {

		private final Class<? extends IModular> modular;
		private final String name;

		public ModularRegisterEvent(Class<? extends IModular> modular, String name) {
			this.modular = modular;
			this.name = name;
		}

		public Class<? extends IModular> getModular() {
			return modular;
		}

		public String getName() {
			return name;
		}
	}
}
